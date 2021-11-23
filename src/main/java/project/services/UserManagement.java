package project.services;

import org.springframework.beans.factory.annotation.Autowired;
import project.model.User;
import project.repositories.UserRepository;

public class UserManagement implements IUserManagement {

    @Autowired
    UserRepository userRep;

    @Override
    public Iterable<User> getAllUsers() {
        return userRep.findAll();
    }

    @Override
    public User addUser(String pseudo, String pwd, String email) {
        User user = new User(pseudo, pwd, email);
        return userRep.save(user);
    }

    @Override
    public void removeUser(long id_user) {
        userRep.delete(id_user);
    }
}
