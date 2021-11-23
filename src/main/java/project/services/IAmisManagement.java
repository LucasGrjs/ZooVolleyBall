package project.services;

import project.model.Amis;
import project.model.User;

public interface IAmisManagement {
    Iterable<Amis> getAllAmis();
    Amis addAmis(User user_1, User user_2);
    void removeAmis(long id_lien);
}
