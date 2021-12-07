package project.controlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.model.User;
import project.services.IUserManagement;

@Controller
public class FriendRequestController {

    @Autowired
    IUserManagement userManagement;

    @RequestMapping("searchfriend")
    public String searchUser(@RequestParam(value="pseudo", required=false) String pseudo, Model model) {
        User friend = userManagement.findUserByPseudo(pseudo);
        if (friend != null) {
            return "redirect:/game";
        }
        return "main";
    }
}
