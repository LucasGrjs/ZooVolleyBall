package project.controlers;

import groovy.util.logging.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.model.Objet;
import project.model.User;
import project.repositories.ObjetRepository;
import project.repositories.UsersRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class ShopController {

    @Autowired
    UsersRepository usersRepository;

    private Map<Long,Boolean> selectedItem(User user)
    {
        Map<Long,Boolean> isSelected = new HashMap<>();

        for(Objet obj : objetRepository.findAll())
        {
            switch (obj.getType_item()){
                case BACKGROUND:
                    if(user.getIdBackgroundSkin() == obj.getId_objet())
                    {
                        isSelected.put(obj.getId_objet(),true);
                    }else
                    {
                        isSelected.put(obj.getId_objet(),false);
                    }
                    break;

                case BALL:
                    if(user.getIdBallSkin() == obj.getId_objet())
                    {
                        isSelected.put(obj.getId_objet(),true);
                    }else
                    {
                        isSelected.put(obj.getId_objet(),false);
                    }
                    break;

                case NET:
                    if(user.getIdNetSkin() == obj.getId_objet())
                    {
                        isSelected.put(obj.getId_objet(),true);
                    }else
                    {
                        isSelected.put(obj.getId_objet(),false);
                    }
                    break;

                case SKIN:
                    if(user.getIdSkin() == obj.getId_objet())
                    {
                        isSelected.put(obj.getId_objet(),true);
                    }else
                    {
                        isSelected.put(obj.getId_objet(),false);
                    }
                    break;
                default:
                    break;
            }
        }
        return isSelected;
    }

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
    public String buyItem(Model model, @RequestParam(value="itemID", required=false) String itemID)
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
        model.addAttribute("Selected",selectedItem(user));

        return "shop";
    }

    @RequestMapping("select")
    public String selectItem(Model model, @RequestParam(value="selectedItemID") String selectedItemID, int type)
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = usersRepository.findByEmail(userDetails.getUsername());

        if(selectedItemID != null)
        {
            switch (type)
            {
                case 0:
                    user.setIdBallSkin((long) Integer.parseInt(selectedItemID));
                    break;

                case 1:
                    user.setIdSkin((long) Integer.parseInt(selectedItemID));
                    break;

                case 2:
                    user.setIdBackgroundSkin((long) Integer.parseInt(selectedItemID));
                    break;

                case 3:
                    user.setIdNetSkin((long) Integer.parseInt(selectedItemID));
                    break;

                default:
                    break;
            }
            usersRepository.save(user);
        }

        model.addAttribute("User", user);
        model.addAttribute("Objets", getNotOwnedItem(user));
        model.addAttribute("Selected", selectedItem(user));

        return "shop";
    }
}
