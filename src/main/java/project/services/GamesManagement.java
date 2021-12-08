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
  public Game createNewGame(String playerId1, String playerId2)
  {
    long id=getNextId();
    return games.put(id,new Game(id, playerId1, playerId2));
  }
}
