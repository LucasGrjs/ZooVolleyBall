package project.controlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import project.messages.JoinMessage;
import project.messages.ReplyJoinMessage;
import project.services.IGamesManagement;

@Controller
public class GameController
{
  @Autowired
  private IGamesManagement gamesManagement;
  
  @RequestMapping("game")
  public String game(Model model)
  {
    return "game";
  }
  
  @MessageMapping("join")
  @SendTo("/zvb/replyjoin")
  public ReplyJoinMessage join(SimpMessageHeaderAccessor headerAccessor, JoinMessage message)
  {
    System.out.println("join playerId : " + headerAccessor.getSessionId());
    
    return new ReplyJoinMessage(10);
  }
  
  @MessageMapping("connected/{id}")
  public void connected(SimpMessageHeaderAccessor headerAccessor, @DestinationVariable long id)
  {
    System.out.println("connected playerId : " + headerAccessor.getSessionId() + " id : " + id);
  }
}
