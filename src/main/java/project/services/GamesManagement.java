package project.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

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
  
  @Override
  public Game getGameById(long id)
  {
    if (!games.containsKey(id)) return null;
    
    return games.get(id);
  }

  @Override
  public Game createNewGame(String playerSessionId1,Long playerId1, String playerSessionId2,Long playerId2)
  {
    long id=getNextId();
    Game g = new Game(id, playerSessionId1,playerId1, playerSessionId2,playerId2);
    games.put(id,g);
    return g;
  }
}
