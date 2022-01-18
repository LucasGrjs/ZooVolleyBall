package project.services;

import project.model.Game;

public interface IGamesManagement {

  Game getGameById(long id);

  Game createNewGame(String playerSessionId1,Long playerId1, String playerSessionId2,Long playerId2);

  boolean quitGame(String sessionID);
}
