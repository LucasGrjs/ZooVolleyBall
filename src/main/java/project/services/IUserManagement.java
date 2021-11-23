package project.services;

import project.model.User;

public interface IUserManagement {
    Iterable<User> getAllUsers();
    User addUser(String pseudo, String pwd, String email);
    void removeUser(long id_user);
}
