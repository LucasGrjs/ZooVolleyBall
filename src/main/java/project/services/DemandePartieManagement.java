package project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.model.DemandePartie;
import project.model.User;
import project.repositories.DemandePartieRepository;

@Service
public class DemandePartieManagement implements IDemandePartieManagement {

    @Autowired
    DemandePartieRepository demandePartieRep;

    @Override
    public DemandePartie addDemandePartie(DemandePartie demandePartie) {
        return demandePartieRep.save(demandePartie);
    }

    @Override
    public void removeDemandePartie(User receveur, User demandeur) {
        DemandePartie demandePartie = demandePartieRep.findByDemandeurAndReceveur(receveur, demandeur);
        demandePartieRep.delete(demandePartie.getId_demande());
    }
}
