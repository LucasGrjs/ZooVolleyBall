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
import project.services.IDemandePartieManagement;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    private IDemandeAmiManagement demandeAmiManagement;

    @Autowired
    private IDemandePartieManagement demandePartieManagement;

    @Autowired
    DemandeAmiRepository demandeAmiRep;

    @Autowired
    DemandePartieRepository demandePartieRep;

    @RequestMapping("demandeur")
    public String demandeur(@RequestParam(value="demandeur", required = true) String demandeur, Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = usersRepository.findByEmail(userDetails.getUsername());

        User dem = usersRepository.findByPseudo(demandeur);
        demandeAmiManagement.removeDemandeAmi(dem, user);
        demandeAmiManagement.removeDemandeAmi(user, dem);
        user.getAmis().add(dem);
        dem.getAmis().add(user);
        usersRepository.save(user);
        usersRepository.save(dem);

        return "redirect:/main";
    }

    @RequestMapping("friend")
    public String friend(@RequestParam(value="friend", required=true) String pseudo, Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = usersRepository.findByEmail(userDetails.getUsername());
        if (pseudo != null && !pseudo.equals(user.getPseudo()) && usersRepository.findByPseudo(pseudo) != null &&
                !user.getAmis().contains(usersRepository.findByPseudo(pseudo))) {
            demandeAmiManagement.addDemandeAmi(new DemandeAmi(user, usersRepository.findByPseudo(pseudo)));
        }
        return "redirect:/main";
    }

    @RequestMapping("demandeurPartie")
    public String demandeurPartie(@RequestParam(value = "demandeurPartie", required = true) String demandeurPartie,
                                  Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = usersRepository.findByEmail(userDetails.getUsername());

        User dem = usersRepository.findByPseudo(demandeurPartie);
        demandePartieManagement.removeDemandePartie(dem, user);
        model.addAttribute("idUser",user.getIdUser());
        return "game";
    }

    @RequestMapping("main")
    public String mainPage(Model model)
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = usersRepository.findByEmail(userDetails.getUsername());

        model.addAttribute("User",user);
        if(!(user.getNbrWin() + user.getNbrLoss() == 0))
        {
            System.out.println(user.getNbrWin());
            System.out.println(user.getNbrLoss());
            System.out.println(((float)user.getNbrWin()  / (user.getNbrWin()+ user.getNbrLoss()))*100);
            model.addAttribute("Winrate",(int)(((float)user.getNbrWin()  / (user.getNbrWin()+ user.getNbrLoss()))*100));
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
