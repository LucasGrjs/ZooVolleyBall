package project.controlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.model.User;
import project.repositories.UsersRepository;

@Controller
public class MainController {

    @Autowired
    UsersRepository usersRepository;

    @RequestMapping("main")
    public String mainPage(@RequestParam(value="friend", required=false) String pseudo, Model model)
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = usersRepository.findByEmail(userDetails.getUsername());

        if (pseudo != null && !pseudo.equals(user.getPseudo()) && usersRepository.findByPseudo(pseudo) != null &&
                !user.getAmis().contains(usersRepository.findByPseudo(pseudo))) {
            
        }

        model.addAttribute("User",user);
        if(!(user.getNbrWin() + user.getNbrLoss() == 0))
        {
            model.addAttribute("Winrate",( user.getNbrWin()  / (user.getNbrWin()+ user.getNbrLoss())));
        }else{
            model.addAttribute("Winrate", "You need to play match");
        }



        return "main";
    }
}
