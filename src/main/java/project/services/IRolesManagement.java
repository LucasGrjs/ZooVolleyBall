package project.services;

import project.model.ERole;
import project.model.Roles;
import project.model.User;

import java.util.List;

public interface IRolesManagement {
    Iterable<Roles> findAllRoles();
    List<User> findUsersByRole(ERole role);
    Roles creerRole(User u, ERole er);
    void associerRole(User u, Roles r);
}
