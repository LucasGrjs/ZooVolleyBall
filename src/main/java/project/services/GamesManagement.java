package project.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import project.model.Game;

@Service
public class GamesManagement implements IGamesManagement
{
  private static long idCounter = Long.MIN_VALUE;
  
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
    return new Game(getNextId(), playerId1, playerId2);
  }
}
