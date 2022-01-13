package project.controlers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
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

  static long limitLeftJ2=500;
  static long limitRightJ2=920;
  
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
    if(game.isJ1(headerAccessor.getSessionId())){
      long xJ1 = game.getxJ1();
      if(action.equals("gauche")){
        if(xJ1-10<limitLeftJ1) return;
        game.setxJ1(xJ1-10);
        reply.setxJ1(xJ1-10);
      }else if(action.equals("droite")){
        if(xJ1+10>limitRightJ1) return;
        game.setxJ1(xJ1+10);
        reply.setxJ1(xJ1+10);
      }else{
        System.out.println("SALE TRICHEUR");
      }
    }else{
      long xJ2 = game.getxJ2();
      if(action.equals("gauche")){
        if(xJ2-10<limitLeftJ2) return;
        game.setxJ2(xJ2-10);
        reply.setxJ2(xJ2-10);
      }else if(action.equals("droite")){
        if(xJ2+10>limitRightJ2) return;
        game.setxJ2(xJ2+10);
        reply.setxJ2(xJ2+10);
      }else{
        System.out.println("SALE TRICHEUR");
      }
    }

    simpMessagingTemplate.convertAndSendToUser(headerAccessor.getSessionId(), "/game/move", reply);
  }
  
  @MessageMapping("game/connected/{gameId}")
  public void connected(SimpMessageHeaderAccessor headerAccessor, @DestinationVariable long gameId)
  {
    System.out.println("connected playerId : " + headerAccessor.getSessionId() + " gameId : " + gameId);
  }
}
