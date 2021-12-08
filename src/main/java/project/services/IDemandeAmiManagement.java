package project.services;

import project.model.DemandeAmi;
import project.model.User;

public interface IDemandeAmiManagement {
    Iterable<DemandeAmi> getAllDemande();
    DemandeAmi addDemandeAmi(DemandeAmi demandeAmi);
    void removeDemandeAmi(long id_demande);
    DemandeAmi findByReceveur(User receveur);
}
