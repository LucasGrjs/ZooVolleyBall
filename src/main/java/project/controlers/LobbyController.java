package project.controlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.model.DemandePartie;
import project.model.User;
import project.repositories.UsersRepository;
import project.services.IDemandePartieManagement;

@Controller
public class LobbyController {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    private IDemandePartieManagement demandePartieManagement;

    @RequestMapping("/lobby")
    public String lobby(@RequestParam(value="invite", required=false) String adversaire, Model model)
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = usersRepository.findByEmail(userDetails.getUsername());
        model.addAttribute("User",user);

        if (adversaire == null) {
            return "lobby";
        }

        demandePartieManagement.addDemandePartie(new DemandePartie(user, usersRepository.findByPseudo(adversaire)));
        model.addAttribute("idUser",user.getIdUser());
        return "game";
    }
}
