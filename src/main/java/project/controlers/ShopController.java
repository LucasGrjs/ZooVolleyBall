package project.controlers;

import groovy.util.logging.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    private List<Objet> getNotOwnedItem(User user)
    {
        List<Objet> ownedObject = user.getObjets();
        List<Objet> notOwned = new ArrayList<>();

        List<Objet> allObjets = new ArrayList<>();
        objetRepository.findAll().forEach(allObjets::add);

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
                notOwned.add(objet);
            }
        }
        return notOwned;
    }


    @RequestMapping("shop")
    public String shop(Model model, @RequestParam(value="itemID", required=false) String itemID)
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = usersRepository.findByEmail(userDetails.getUsername());

        if(itemID != null)
        {
            Objet item = objetRepository.findById_objet(Integer.parseInt(itemID));
            if(user.getCredit() >= item.getPrice())
            {
                user.setCredit(user.getCredit() - item.getPrice());
                user.addItem(item);
                usersRepository.save(user);
            }else
            {
                model.addAttribute("Error", "You don't have enough coins");
            }
        }

        model.addAttribute("User", user);
        model.addAttribute("Objets", getNotOwnedItem(user));

        return "shop";
    }
}
