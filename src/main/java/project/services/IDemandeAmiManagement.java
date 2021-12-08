package project.services;

import project.model.DemandeAmi;
import project.model.User;

import java.util.List;

public interface IDemandeAmiManagement {
    Iterable<DemandeAmi> getAllDemande();
    DemandeAmi addDemandeAmi(DemandeAmi demandeAmi);
    void removeDemandeAmi(long id_demande);
    List<DemandeAmi> findByReceveur(User receveur);
}
