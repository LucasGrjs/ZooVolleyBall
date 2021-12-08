package project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import project.model.User;

import java.util.ArrayList;

@Service
public class MatchmakingManagement implements IMatchmakingManagement {

    private ArrayList<String> queueCasual = new ArrayList<String>();

    @Autowired
    IUserManagement userManagement;

    /*
     * Retourne l'adversaire si dispo, null si on est mis en attente
      * */
    @Override
    public String findCasual(String sessionID) {
        String adversaireID = null;

        if(queueCasual.size() > 0){
            adversaireID = queueCasual.get(queueCasual.size()-1);
            queueCasual.remove(queueCasual.size()-1);
            return adversaireID;
        }

        queueCasual.add(sessionID);
        return null;
    }
}
