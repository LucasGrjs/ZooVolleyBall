package project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.model.DemandeAmi;
import project.model.User;
import project.repositories.DemandeAmiRepository;

import java.util.List;

@Service
public class DemandeAmiManagement implements IDemandeAmiManagement {

    @Autowired
    DemandeAmiRepository demandeAmiRep;

    @Override
    public Iterable<DemandeAmi> getAllDemande() {
        return demandeAmiRep.findAll();
    }

    @Override
    public DemandeAmi addDemandeAmi(DemandeAmi demandeAmi) {
        return demandeAmiRep.save(demandeAmi);
    }

    @Override
    public void removeDemandeAmi(long id_demande) {
        demandeAmiRep.delete(id_demande);
    }

    @Override
    public List<DemandeAmi> findByReceveur(User receveur) {
        return demandeAmiRep.findByReceveur(receveur);
    }
}
