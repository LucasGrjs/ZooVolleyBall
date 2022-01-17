package project.services;

import project.model.Objet;
import project.model.User;

public interface IUserManagement {
    Iterable<User> getAllUsers();
    User addUser(String pseudo, String pwd, String email);
    User addUser(User user);
    void removeUser(long id_user);
    User findUserByEmail(String email);
    User findUserByPseudo(String pseudo);
    void addWinOrLoseUser(long id,boolean isWinner);
}
