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

  static long[] limitLeft={0,420};
  static long[] limitRight={420,840};

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
    int j = 0; // car joueur 1 et pour l'instant je sais pas comme savoir si c'est un mouvement de J1 ou J2
    long xJ = -1;
    long yJ = -1;
    if(j==0){
      xJ = game.getxJ1();
      yJ = game.getyJ1();
    }else{
      xJ = game.getxJ2();
      yJ = game.getyJ2();
    }
    switch(action){
      case "gauche":
        if(!gauche(j,game)) return;
        reply.setAllAttributesFromGame(game);
        break;
      case "droite":
        if(!droite(j,game)) return;
        reply.setAllAttributesFromGame(game);
        break;
      case "saut":
        if(!saut(j,game)) return;
        reply.setAllAttributesFromGame(game);
        break;
      case "enSaut":
        enSaut(j,game);
        reply.setAllAttributesFromGame(game);
        break;
      default:
        System.out.println("SALE TRICHEUR");
    }
    simpMessagingTemplate.convertAndSendToUser(headerAccessor.getSessionId(), "/game/move", reply);
  }

  private boolean gauche(int j,Game game){
    long xJ;
    if(j==0){
      xJ=game.getxJ1();
      if(xJ-10<limitLeft[j]) return false;
      game.setxJ1(xJ-10);
      game.setVelocityXJ1(-jumpVelocity);
    }else{
      xJ=game.getxJ2();
      if(xJ-10<limitLeft[j]) return false;
      game.setxJ2(xJ-10);
      game.setVelocityXJ2(-jumpVelocity);
    }
    return true;
  }
  private boolean droite(int j,Game game){
    long xJ;
    if(j==0){
      xJ=game.getxJ1();
      if(xJ+10>limitRight[j]) return false;
      game.setxJ1(xJ+10);
      game.setVelocityXJ1(jumpVelocity);
    }else{
      xJ=game.getxJ2();
      if(xJ+10>limitRight[j]) return false;
      game.setxJ2(xJ+10);
      game.setVelocityXJ2(jumpVelocity);
    }
    return true;
  }
  private boolean saut(int j,Game game){
    long yJ;
    long xJ;
    if(j==0){
      yJ=game.getyJ1();
      xJ=game.getxJ1();
      if(yJ!=solHeight){ // deja en train de sauter/retomber
        System.out.println("saut rejeté car dans les airs");
        return false;
      }
      game.setVelocityYJ1(jumpVelocity);
      game.setyJ1(yJ-jumpVelocity); // on soustrait pour sauter car le repere est à l'envers
      if(game.getVelocityXJ1()!=0){
        game.setxJ1(xJ+game.getVelocityXJ1());
        game.setVelocityXJ1(0);
      }
    }else{
      yJ=game.getyJ2();
      xJ=game.getxJ2();
      if(yJ!=solHeight){ // deja en train de sauter/retomber
        System.out.println("saut rejeté car dans les airs");
        return false;
      }
      game.setVelocityYJ2(jumpVelocity);
      game.setyJ2(yJ-jumpVelocity); // on soustrait pour sauter car le repere est à l'envers
      if(game.getVelocityXJ2()!=0){
        game.setxJ2(xJ+game.getVelocityXJ2());
        game.setVelocityXJ2(0);
      }
    }
    return true;
  }
  private void enSaut(int j,Game game){
    long yJ;
    long xJ;
    if(j==0){
      yJ=game.getyJ1();
      xJ=game.getxJ1();
      if(yJ<=limitJumpHeight){
        game.setVelocityYJ1(-jumpVelocity);
      }else if(game.getVelocityYJ1()<0){
        game.setVelocityYJ1((long)Math.ceil(game.getVelocityYJ1()*gravity));
      }
      game.setyJ1(yJ-game.getVelocityYJ1());
      if(game.getyJ1()>=solHeight){ // pas <= car repere à l'envers
        game.setyJ1(solHeight);
        game.setVelocityYJ1(0);
      }
      if(game.getVelocityXJ1()!=0){
        long x = xJ+game.getVelocityXJ1();
        if(x<limitLeft[j]){
          x=limitLeft[j];
        }else if(x>limitRight[j]){
          x=limitRight[j];
        }
        game.setxJ1(x);
        game.setVelocityXJ1(0);
      }
    }else{
      yJ=game.getyJ2();
      xJ=game.getxJ2();
      if(yJ<=limitJumpHeight){
        game.setVelocityYJ2(-jumpVelocity);
      }else if(game.getVelocityYJ2()<0){
        game.setVelocityYJ2((long)Math.ceil(game.getVelocityYJ2()*gravity));
      }
      game.setyJ2(yJ-game.getVelocityYJ2());
      if(game.getyJ2()>=solHeight){ // pas <= car repere à l'envers
        game.setyJ2(solHeight);
        game.setVelocityYJ2(0);
      }
      if(game.getVelocityXJ2()!=0){
        long x = xJ+game.getVelocityXJ2();
        if(x<limitLeft[j]){
          x=limitLeft[j];
        }else if(x>limitRight[j]){
          x=limitRight[j];
        }
        game.setxJ2(x);
        game.setVelocityXJ2(0);
      }
    }
  }
  
  @MessageMapping("game/connected/{gameId}")
  public void connected(SimpMessageHeaderAccessor headerAccessor, @DestinationVariable long gameId)
  {
    System.out.println("connected playerId : " + headerAccessor.getSessionId() + " gameId : " + gameId);
  }
}
