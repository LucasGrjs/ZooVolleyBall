package project.services;

import project.model.Game;

public interface IGamesManagement {

  Game getGameById(long id);
  
  Game createNewGame(String playerId1, String playerId2);
}
