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
}
