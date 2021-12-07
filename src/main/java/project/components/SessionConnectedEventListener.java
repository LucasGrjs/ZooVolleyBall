package project.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

@Component
public class SessionConnectedEventListener implements ApplicationListener<SessionConnectedEvent>
{
  @Autowired
  private SimpMessagingTemplate template;

  private static final Logger logger = LoggerFactory.getLogger(SessionDisconnectedEventListener.class);

  @Override
  public void onApplicationEvent(SessionConnectedEvent sessionConnectedEvent) {
    StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(sessionConnectedEvent.getMessage());

    String playerId = headerAccessor.getSessionId();

    System.out.println("SessionConnectedEvent playerId : " + playerId);
    //template.convertAndSend("/ttt/gamestate/" + game.id, game);
  }
}
