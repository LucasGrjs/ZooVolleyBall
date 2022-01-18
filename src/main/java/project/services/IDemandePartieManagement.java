package project.services;

import project.model.DemandePartie;
import project.model.User;

public interface IDemandePartieManagement {
    DemandePartie addDemandePartie(DemandePartie demandePartie);
    void removeDemandePartie(User receveur, User demandeur);
}
