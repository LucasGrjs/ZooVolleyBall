package project.controlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.ModelAndView;
import project.messages.JoinMessage;
import project.messages.ReplyJoinMessage;
import project.model.Game;
import project.model.User;
import project.repositories.ObjetRepository;
import project.repositories.UsersRepository;
import project.services.IGamesManagement;
import project.services.IMatchmakingManagement;
import project.services.IUserManagement;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    ObjetRepository objetRepository;

    @RequestMapping("findcasual")
    public String rechercheCasual(Model model) throws JsonProcessingException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(userDetails);
        User user = usersRepository.findByEmail(userDetails.getUsername());
        ObjectMapper objectMapper = new ObjectMapper();
        //String idStrUser = ""+user.getIdUser();
//        System.out.println(user.getIdUser());
//        model.put("idUser", user.getIdUser());
        model.addAttribute("idUser",user.getIdUser());
        return "game";
    }

    @MessageMapping("findcasual/join")
    public void join(SimpMessageHeaderAccessor headerAccessor, JoinMessage message) {

        String sessionID = headerAccessor.getSessionId();

        User user = usersRepository.findByIdUser(message.getIdUser());
        if(user!=null){
            Map.Entry<String,Long> adversaireIDs = matchmakingManagement.findCasual(sessionID,user.getIdUser());

            System.out.println("RQT JOIN : ====== "+sessionID+ " | "+adversaireIDs);

            if(adversaireIDs != null){
                Game newG = gamesManagement.createNewGame(sessionID,user.getIdUser(),adversaireIDs.getKey(),adversaireIDs.getValue());
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

                System.out.println("ENVOI REPONSE /findcasual/replyjoin : "+adversaireIDs );
                simpMessagingTemplate.convertAndSendToUser(adversaireIDs.getKey(), "/findcasual/replyjoin", reply);
                System.out.println("ENVOI REPONSE /findcasual/replyjoin : "+sessionID );
                simpMessagingTemplate.convertAndSendToUser(sessionID, "/findcasual/replyjoin", reply);
                System.out.println(reply);
            }
        }else{
            System.out.println("ERROR: user is null");
        }
    }



}
