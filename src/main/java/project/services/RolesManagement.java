package project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.model.ERole;
import project.model.Roles;
import project.model.User;
import project.repositories.RoleRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class RolesManagement implements IRolesManagement{
    @Autowired
    RoleRepository rr;

    @Override
    public Iterable<Roles> findAllRoles() {
        return rr.findAll();
    }

    @Override
    public List<User> findUsersByRole(ERole role) {
        List<Roles> roles = rr.findAllByRole(role);
        List<User> utilisateurs = new ArrayList<>();
        roles.forEach(r -> {
            if (!utilisateurs.contains(r.getUser()))
                utilisateurs.add(r.getUser());
        });
        return utilisateurs;
    }

    public Roles creerRole(User u, ERole er) {
        Roles r = new Roles();
        r.setRole(er);
        r.setUser(u);
        return rr.save(r);
    }

    public void associerRole(User u, Roles r) {
        ArrayList<Roles> r_list = (ArrayList<Roles>) u.getRoles();
        if(r_list == null) r_list = new ArrayList<>();
        r_list.add(r);
        u.setRoles(r_list);
    }
}
