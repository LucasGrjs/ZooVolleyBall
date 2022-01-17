package project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.model.ERole;
import project.model.Objet;
import project.model.Roles;
import project.model.User;
import project.repositories.ObjetRepository;
import project.repositories.RoleRepository;
import project.repositories.UsersRepository;

@Service
public class UserManagement implements IUserManagement {

    @Autowired
    IRolesManagement roleService;
    @Autowired
    UsersRepository userRep;
    @Autowired
    ObjetRepository objetRep;

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

    public User findUserById(long id) {
        return userRep.findByIdUser(id);
    }

    @Override
    public User findUserByPseudo(String pseudo) {
        return userRep.findByPseudo(pseudo);
    }

    @Override
    public void removeUser(long id_user) {
        userRep.delete(id_user);
    }

    @Override
    public void addWinOrLoseUser(long id,boolean isWinner){
        User u = findUserById(id);
        if(isWinner){
            u.setNbrWin(u.getNbrWin()+1);
        }else{
            u.setNbrLoss(u.getNbrLoss()+1);
        }
        userRep.save(u);
    }
}
