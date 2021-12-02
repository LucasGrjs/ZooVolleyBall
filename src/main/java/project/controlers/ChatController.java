package project.controlers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import project.model.Message;
import project.model.OutputMessage;

@Controller
public class ChatController
{
  @RequestMapping("/chat")
  public String chat(Model model)
  {
    return "chat";
  }
  
  @MessageMapping("/chat")
  @SendTo("/topic/messages")
  public OutputMessage send(Message message) throws Exception {
      String time = new SimpleDateFormat("HH:mm").format(new Date());
      return new OutputMessage(message.getFrom(), message.getText(), time);
  }
}
