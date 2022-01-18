package project.controlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.model.DemandeAmi;
import project.model.DemandePartie;
import project.model.User;
import project.repositories.DemandeAmiRepository;
import project.repositories.DemandePartieRepository;
import project.repositories.UsersRepository;
import project.services.IDemandeAmiManagement;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    private IDemandeAmiManagement demandeAmiManagement;

    @Autowired
    DemandeAmiRepository demandeAmiRep;

    @Autowired
    DemandePartieRepository demandePartieRep;

    @RequestMapping("main")
    public String mainPage(@RequestParam(value="friend", required=false) String pseudo,
                           @RequestParam(value="demandeur", required = false) String demandeur, Model model)
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = usersRepository.findByEmail(userDetails.getUsername());

        if (pseudo != null && !pseudo.equals(user.getPseudo()) && usersRepository.findByPseudo(pseudo) != null &&
                !user.getAmis().contains(usersRepository.findByPseudo(pseudo))) {
            demandeAmiManagement.addDemandeAmi(new DemandeAmi(user, usersRepository.findByPseudo(pseudo)));
        }

        if (demandeur != null) {
            User dem = usersRepository.findByPseudo(demandeur);
            demandeAmiManagement.removeDemandeAmi(dem, user);
            demandeAmiManagement.removeDemandeAmi(user, dem);
            user.getAmis().add(dem);
            dem.getAmis().add(user);
            usersRepository.save(user);
            usersRepository.save(dem);
        }

        model.addAttribute("User",user);
        if(!(user.getNbrWin() + user.getNbrLoss() == 0))
        {
            model.addAttribute("Winrate",( user.getNbrWin()  / (user.getNbrWin()+ user.getNbrLoss())));
        }else{
            model.addAttribute("Winrate", "You need to play match");
        }

        List<DemandeAmi> demandes = demandeAmiRep.findByReceveur(user);
        model.addAttribute("Demandes", demandes);

        List<DemandePartie> demandesPartie = demandePartieRep.findByReceveur(user);
        model.addAttribute("DemandesPartie", demandesPartie);

        return "main";
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/main";
    }
}
