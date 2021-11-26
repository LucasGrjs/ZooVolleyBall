package project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.model.User;
import project.repositories.UsersRepository;

@Service
public class UserManagement implements IUserManagement {

    @Autowired
    UsersRepository userRep;

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
    public User addUser(User user) {
        return userRep.save(user);
    }

    @Override
    public void removeUser(long id_user) {
        userRep.delete(id_user);
    }
}
