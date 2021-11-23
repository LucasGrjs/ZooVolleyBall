package project.services;

import project.model.Objet;
import project.model.User;

public interface IObjetManagement {
    Iterable<Objet> getAllObjets();
    Objet addObjet(String nomObjet);
    void removeObjet(long id_objet);
}
