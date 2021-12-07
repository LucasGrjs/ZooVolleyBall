package project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.model.ERole;
import project.model.Roles;
import project.model.User;
import project.repositories.RoleRepository;
import project.repositories.UsersRepository;

@Service
public class UserManagement implements IUserManagement {

    @Autowired
    IRolesManagement roleService;
    @Autowired
    UsersRepository userRep;

    @Override
    public Iterable<User> getAllUsers() {
        return userRep.findAll();
    }

    @Override
    public User addUser(String pseudo, String pwd, String email) {
        User user=new User();
        user.setPseudo(pseudo);
        user.setPwd(pwd);
        user.setEmail(email);
        userRep.save(user);
        Roles r = roleService.creerRole(user, ERole.JOUEUR);

        roleService.associerRole(user,r);
        return userRep.save(user);
    }

    @Override
    public User addUser(User user) {
        return userRep.save(user);
    }

    public User findUserByEmail(String email) {

        return userRep.findByEmail(email);

    }

    @Override
    public User findUserByPseudo(String pseudo) {
        return userRep.findByPseudo(pseudo);
    }

    @Override
    public void removeUser(long id_user) {
        userRep.delete(id_user);
    }


}
