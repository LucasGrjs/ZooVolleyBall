package project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import project.model.User;

import java.util.ArrayList;

@Service
public class MatchmakingManagement implements IMatchmakingManagement {

    private ArrayList<User> queueCasual = new ArrayList<>();

    @Autowired
    IUserManagement userManagement;

    /*
     * Retourne l'adversaire si dispo, null si on est mis en attente
      * */
    @Override
    public User findCasual(User u) {
        UserDetails userDetails =  (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userManagement.findUserByEmail(userDetails.getUsername());
        User adversaire = null;

        System.out.println(user);
        if(queueCasual.size() > 0){
            adversaire = queueCasual.get(queueCasual.size()-1);
            queueCasual.remove(queueCasual.size()-1);
            return adversaire;
        }

        queueCasual.add(user);
        return null;
    }
}
