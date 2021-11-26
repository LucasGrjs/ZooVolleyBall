package project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.model.Partie;
import project.model.User;
import project.repositories.PartieRepository;

@Service
public class PartieManagement implements IPartieManagement {

    @Autowired
    PartieRepository partieRep;

    @Override
    public Iterable<Partie> getAllParties() {
        return partieRep.findAll();
    }

    @Override
    public Partie addPartie(User user_1, User user_2) {
        Partie partie = new Partie(user_1, user_2);
        return partieRep.save(partie);
    }

    @Override
    public void removePartie(long id_partie) {
        partieRep.delete(id_partie);
    }
}
