package project.controlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import project.messages.JoinMessage;
import project.messages.ReplyJoinMessage;
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

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;


    @RequestMapping("findcasual")
    public String rechercheCasual(){
        return "game";
    }

    @MessageMapping("findcasual/join")
    public void join(SimpMessageHeaderAccessor headerAccessor, JoinMessage message) {

        String sessionID = headerAccessor.getSessionId();

        String adversaireID = matchmakingManagement.findCasual(sessionID);

        System.out.println("RQT JOIN : ====== "+sessionID+ " | "+adversaireID);

        if(adversaireID != null){
            Game newG = gamesManagement.createNewGame(sessionID,adversaireID);
            System.out.println(newG);
            long gameId = newG.getId();

            ReplyJoinMessage reply = new ReplyJoinMessage();

            if (newG != null)
            {
                reply.setError(false);
                reply.setGameId(gameId);
            }
            else
            {
                reply.setError(true);
                reply.setErrorMessage("Can't find game with id " + gameId);
            }

            System.out.println("ENVOI REPONSE /findcasual/replyjoin : "+adversaireID );
            simpMessagingTemplate.convertAndSendToUser(adversaireID, "/findcasual/replyjoin", reply);
            System.out.println("ENVOI REPONSE /findcasual/replyjoin : "+sessionID );
            simpMessagingTemplate.convertAndSendToUser(sessionID, "/findcasual/replyjoin", reply);
            System.out.println(reply);
        }
    }



}
