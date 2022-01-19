package project.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import project.messages.ActionMessage;
import project.model.Game;

@Service
public class GamesManagement implements IGamesManagement
{
  private static long idCounter = 0;//Long.MIN_VALUE; // en attendant pour tester
  
  private static long getNextId()
  {
    return idCounter++;
  }
  
  Map<Long, Game> games = new HashMap<>();

  @Autowired
  SimpMessagingTemplate simpMessagingTemplate;
  
  @Override
  public Game getGameById(long id)
  {
    if (!games.containsKey(id)) return null;
    
    return games.get(id);
  }

  @Override
  public Game createNewGame(String playerSessionId1,Long playerId1, String playerSessionId2,Long playerId2, boolean isranked)
  {
    long id=getNextId();
    Game g = new Game(id, playerSessionId1,playerId1, playerSessionId2,playerId2);
    g.setRanked(isranked);
    games.put(id,g);
    return g;
  }

  @Override
  public boolean quitGame(String sessionID){
    for (Map.Entry<Long, Game> g:
         games.entrySet()) {
      if(g.getValue().getPlayerSessionIds()[0].equals(sessionID))   {
        ActionMessage msg = new ActionMessage(g.getValue().getId(),"1");
        msg.setScoreFinal("Score : "+g.getValue().getRoundWonJ1()+" - "+g.getValue().getRoundWonJ2());
        simpMessagingTemplate.convertAndSendToUser(g.getValue().getPlayerSessionIds()[1],"/game/win",msg);
        return true;
      }else if(g.getValue().getPlayerSessionIds()[1].equals(sessionID)){
        ActionMessage msg = new ActionMessage(g.getValue().getId(),"0");
        msg.setScoreFinal("Score : "+g.getValue().getRoundWonJ1()+" - "+g.getValue().getRoundWonJ2());
        simpMessagingTemplate.convertAndSendToUser(g.getValue().getPlayerSessionIds()[0],"/game/win",msg);
        return true;
      }
    }
    return false;
  }

}
