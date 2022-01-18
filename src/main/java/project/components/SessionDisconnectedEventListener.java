package project.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import project.services.GamesManagement;
import project.services.MatchmakingManagement;

@Component
public class SessionDisconnectedEventListener implements ApplicationListener<SessionDisconnectEvent>
{
  @Autowired
  private SimpMessagingTemplate template;

  @Autowired
  private MatchmakingManagement matchmakingManagement;

  @Autowired
  private GamesManagement gamesManagement;

  private static final Logger logger = LoggerFactory.getLogger(SessionDisconnectedEventListener.class);

  @Override
  public void onApplicationEvent(SessionDisconnectEvent sessionDisconnectEvent) {
    StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(sessionDisconnectEvent.getMessage());

    String playerId = headerAccessor.getSessionId();

    System.out.println("SessionDisconnectEvent playerId : " + playerId);
    //template.convertAndSend("/ttt/gamestate/" + game.id, game);
    if(!matchmakingManagement.quitQueue(playerId)){
      gamesManagement.quitGame(playerId);
    }

  }

}
