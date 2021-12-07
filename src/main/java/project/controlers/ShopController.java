package project.controlers;

import groovy.util.logging.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.model.Objet;
import project.model.User;
import project.repositories.ObjetRepository;
import project.repositories.UsersRepository;
import project.services.IUserManagement;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Controller
public class ShopController {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    ObjetRepository objetRepository;

    @RequestMapping("shop")
    public String shop(Model model)
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = usersRepository.findByEmail(userDetails.getUsername());
        model.addAttribute("User", user);
        List<Objet> ownedObject = user.getObjets();

        List<Objet> allObjets = new ArrayList<>();
        objetRepository.findAll().forEach(allObjets::add);

        System.out.println();

        for(Objet objet : allObjets) {

            System.out.println(objet.getNomObjet() +" : "+objet.getPrice());
        }

        List<Objet> notOwned = new ArrayList<>();

        for(Objet objet : allObjets)
        {
            boolean contains = false;
            for(Objet objetOwned : ownedObject)
            {
                if(objetOwned.getId_objet() == objet.getId_objet())
                {
                    contains = true;
                    break;
                }
            }
            if(!contains)
            {
                System.out.println("Not owned "+objet.getNomObjet()+" : "+objet.getPrice());
                notOwned.add(objet);
            }
        }

        model.addAttribute("Objets", notOwned);

        return "shop";
    }
}
