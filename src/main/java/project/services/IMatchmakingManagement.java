package project.services;

import project.model.User;

import java.util.Map;

public interface IMatchmakingManagement {

    Map.Entry<String,Long> findCasual(String sessionID,Long playerId);

    boolean quitCasual(String sessionID);
}
