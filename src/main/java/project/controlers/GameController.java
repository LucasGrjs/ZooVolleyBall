package project.controlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import project.messages.ActionMessage;
import project.messages.JoinMessage;
import project.messages.ReplyActionMessage;
import project.messages.ReplyJoinMessage;
import project.model.Game;
import project.services.IGamesManagement;

@Controller
public class GameController
{
  @Autowired
  private IGamesManagement gamesManagement;
  
  @Autowired
  private SimpMessagingTemplate simpMessagingTemplate;
  static long limitLeftJ1=0;
  static long limitRightJ1=420;

  static long limitLeftJ2=420;
  static long limitRightJ2=840;

  static long solHeight=800;
  static long limitJumpHeight=600;
  static long jumpVelocity=20;
  static double gravity= 1.2;
  
  @RequestMapping("game")
  public String game(Model model)
  {
    return "game";
  }
  
  @MessageMapping("game/join")
  public void join(SimpMessageHeaderAccessor headerAccessor, JoinMessage message)
  {
    System.out.println("join playerId : " + headerAccessor.getSessionId());
    
    long gameId = message.getGameId();
    
    ReplyJoinMessage reply = new ReplyJoinMessage();

    gamesManagement.createNewGame("a","b"); // en attendant
    Game game = gamesManagement.getGameById(gameId);
    
    if (game != null)
    {
      reply.setError(false);
      reply.setGameId(gameId);
    }
    else
    {
      reply.setError(true);
      reply.setErrorMessage("Can't find game with id " + gameId);
    }
    
    simpMessagingTemplate.convertAndSendToUser(headerAccessor.getSessionId(), "/game/replyjoin", reply);
  }

  @MessageMapping("game/move")
  public void depl(SimpMessageHeaderAccessor headerAccessor, ActionMessage message) {
    System.out.println("deplacement playerId : " + headerAccessor.getSessionId());
    String action = message.getAction();
    System.out.println("action depl : "+action);
    ReplyActionMessage reply = new ReplyActionMessage();
    long gameId= message.getGameId();
    Game game = gamesManagement.getGameById(gameId);
    if(game==null){
      reply.setError(true);
      reply.setErrorMessage("Can't find game with id " + gameId);
      simpMessagingTemplate.convertAndSendToUser(headerAccessor.getSessionId(), "/game/move", reply);
      return;
    }
    reply.setError(false);
    long xJ1 = game.getxJ1();
    long yJ1 = game.getyJ1();
    switch(action){
      case "gauche":
        // comment savoir J1 ou J2 ?
        if(xJ1-10<limitLeftJ1) return;
        game.setxJ1(xJ1-10);
        reply.setAllAttributesFromGame(game);
        break;
      case "droite":
        // comment savoir J1 ou J2 ?
        if(xJ1+10>limitRightJ1) return;
        game.setxJ1(xJ1+10);
        reply.setAllAttributesFromGame(game);
        break;
      case "saut":
        if(yJ1!=solHeight){ // deja en train de sauter/retomber
          System.out.println("saut rejeté car dans les airs");
          return;
        }
        game.setVelocityYJ1(jumpVelocity);
        game.setyJ1(yJ1-jumpVelocity); // on soustrait pour sauter car le repere est à l'envers
        reply.setAllAttributesFromGame(game);
        break;
      case "enSaut":
        if(yJ1<=limitJumpHeight){
          game.setVelocityYJ1(-jumpVelocity);
        }else if(game.getVelocityYJ1()<0){
          game.setVelocityYJ1((long)Math.ceil(game.getVelocityYJ1()*gravity));
        }
        game.setyJ1(yJ1-game.getVelocityYJ1());
        if(game.getyJ1()>=solHeight){ // pas <= car on repere à l'envers
          game.setyJ1(solHeight);
          game.setVelocityYJ1(0);
        }
        reply.setAllAttributesFromGame(game);
        break;
      default:
        System.out.println("SALE TRICHEUR");
    }
    simpMessagingTemplate.convertAndSendToUser(headerAccessor.getSessionId(), "/game/move", reply);
  }
  
  @MessageMapping("game/connected/{gameId}")
  public void connected(SimpMessageHeaderAccessor headerAccessor, @DestinationVariable long gameId)
  {
    System.out.println("connected playerId : " + headerAccessor.getSessionId() + " gameId : " + gameId);
  }
}
