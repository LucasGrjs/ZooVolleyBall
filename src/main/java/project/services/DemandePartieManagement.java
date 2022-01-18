package project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.model.DemandePartie;
import project.repositories.DemandePartieRepository;

@Service
public class DemandePartieManagement implements IDemandePartieManagement {

    @Autowired
    DemandePartieRepository demandePartieRep;

    @Override
    public DemandePartie addDemandePartie(DemandePartie demandePartie) {
        return demandePartieRep.save(demandePartie);
    }
}
