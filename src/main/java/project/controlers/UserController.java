package project.controlers;

import groovy.util.logging.Slf4j;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private static final String ERR_EMAIL = "L'email est déjà utilisé choisissez en un autre.";

    private String error;
    private boolean fromRedirect = false;

    @RequestMapping("register")
    public String addUser(@RequestParam(value="pseudo", required=false) String pseudo,
                          @RequestParam(value="pwd", required=false) String pwd,
                          @RequestParam(value="email", required=false) String email,
                          Model model)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if (!(auth instanceof AnonymousAuthenticationToken))
        {
          return "redirect:/main";
        }
      
        System.out.println("pseudo " +pseudo);
        System.out.println("pwd " +pwd);
        System.out.println("email " +email);

        if(pseudo != null && pwd != null && email != null)
        {
            userManager.addUser(pseudo, passwordEncoder.encode(pwd), email);
            return "redirect:/login";
        }
        
        return "inscription";
    }
}
