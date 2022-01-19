package project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import project.model.User;
import project.repositories.UsersRepository;

import java.util.*;

@Service
public class MatchmakingManagement implements IMatchmakingManagement {

    private Map<String,Long> queueCasual = new LinkedHashMap<>();

    private Map<String,Long> queueRanked = new LinkedHashMap<>();

    @Autowired
    IUserManagement userManagement;

    @Autowired
    UsersRepository usersRepository;

    /*
     * Retourne l'adversaire si dispo, null si on est mis en attente
      * */
    @Override
    public Map.Entry<String,Long> findCasual(String sessionID,Long playerId) {

        if(queueCasual.size() > 0){
            Map.Entry<String,Long> last = null ;
            for( Map.Entry<String,Long> entry : queueCasual.entrySet() ) last = entry;

            queueCasual.remove(last.getKey());
            return last;
        }

        queueCasual.put(sessionID,playerId);
        return null;
    }

    @Override
    public boolean quitQueue(String sessionID){
        Long id = queueCasual.get(sessionID);
        if(id != null){
            queueCasual.remove(sessionID);
            return true;
        }
        id = queueRanked.get(sessionID);
        if (id != null) {
            queueRanked.remove(sessionID);
            return true;
        }
        return false;

    }

    /*
     * Retourne l'adversaire si dispo, null si on est mis en attente
     * */
    @Override
    public Map.Entry<String,Long> findRanked(String sessionID,Long playerId) {

        if(queueRanked.size() > 0){
            Map.Entry<String,Long> last = null ;
            for( Map.Entry<String,Long> entry : queueRanked.entrySet() ) last = entry;

            queueRanked.remove(last.getKey());
            return last;
        }

        queueRanked.put(sessionID,playerId);
        return null;
    }


}
