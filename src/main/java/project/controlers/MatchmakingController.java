package project.controlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import project.model.Game;
import project.model.User;
import project.services.IGamesManagement;
import project.services.IMatchmakingManagement;
import project.services.IUserManagement;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MatchmakingController {

    @Autowired
    IMatchmakingManagement matchmakingManagement;

    @Autowired
    IUserManagement userManagement;

    @Autowired
    IGamesManagement gamesManagement;


    @RequestMapping("findCasual")
    public String rechercheCasual(){

        UserDetails userDetails =  (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userManagement.findUserByEmail(userDetails.getUsername());
        User adversaire = matchmakingManagement.findCasual(user);

        if(adversaire != null){
            //Game newG = gamesManagement.createNewGame(user.getPseudo(),user.getPseudo());
            return "redirect:/";
        }

        return "recherche";
    }
}
