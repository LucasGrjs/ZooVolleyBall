package project.services;

import project.model.Partie;
import project.model.User;

public interface IPartieManagement {
    Iterable<Partie> getAllParties();
    Partie addPartie(User user_1, User user_2);
    void removePartie(long id_partie);
}
