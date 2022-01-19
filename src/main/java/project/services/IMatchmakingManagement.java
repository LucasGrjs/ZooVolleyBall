package project.services;

import project.model.User;

import java.util.Map;

public interface IMatchmakingManagement {

    Map.Entry<String,Long> findCasual(String sessionID,Long playerId);

    Map.Entry<String,Long> findRanked(String sessionID,Long playerId);

    boolean quitQueue(String sessionID);
}
