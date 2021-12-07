package project.services;

import project.model.Objet;
import project.model.User;

public interface IObjetManagement {
    Iterable<Objet> getAllObjets();
    Objet addObjet(String nom_objet);
    Objet addObjet(String nom_objet, int price, Objet.TypeItem type_item);
    void removeObjet(long id_objet);
}
