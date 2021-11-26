package project.controlers;

import groovy.util.logging.Slf4j;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.model.User;
import project.services.IUserManagement;

import java.util.Objects;


@Slf4j
@Controller
public class UserController {

    @Autowired
    private IUserManagement userManager;

    @RequestMapping("adduser")
    public String addUser(@RequestParam(value="pseudo", required=false) String pseudo,
                          @RequestParam(value="pwd", required=false) String pwd,
                          @RequestParam(value="email", required=false) String email,
                          Model model)
    {
        System.out.println("pseudo " +pseudo);
        System.out.println("pwd " +pwd);
        System.out.println("email " +email);

        if(pseudo != null && pwd != null && email != null)
        {
            userManager.addUser(pseudo, pwd, email);
        }

        return "inscription";
    }
}
