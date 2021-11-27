package project.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.model.Roles;
import project.model.User;
import project.services.IUserManagement;

import java.util.ArrayList;
import java.util.List;

@Service("customUtilisateurDetailsService")
public class CustomUtilisateurDetailsService implements UserDetailsService {
    @Autowired
    private IUserManagement utilisateurService;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("========================= LOAD =========================");
        if (email.trim().isEmpty()) {
            System.out.println("email empty : "+email);
            throw new UsernameNotFoundException("Email non renseigné");
        }

        User utilisateur = utilisateurService.findUserByEmail(email);
        System.out.println(utilisateur);

        if (utilisateur == null) {
            System.out.println("email ou mdp incorrect, email : "+email);
            throw new UsernameNotFoundException("Email " + email + " ou mot de passe incorrect");
        }

        System.out.println("========================= FIN LOAD =========================");
        return new org.springframework.security.core.userdetails.User(utilisateur.getPseudo(), utilisateur.getPwd(), getGrantedAuthorities(utilisateur));
    }
    private List<GrantedAuthority> getGrantedAuthorities(User utilisateur) {

        System.out.println("getGrantedAuthorities");
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        List<Roles> roles = utilisateur.getRoles();

        for (Roles r : roles) {
            authorities.add(new SimpleGrantedAuthority(r.getRole().name()));
        }

        return authorities;
    }
}
