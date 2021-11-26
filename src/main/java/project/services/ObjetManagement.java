package project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.model.Objet;
import project.repositories.ObjetRepository;

@Service
public class ObjetManagement implements IObjetManagement {
    @Autowired
    ObjetRepository objetRep;

    @Override
    public Iterable<Objet> getAllObjets() {
        return objetRep.findAll();
    }

    @Override
    public Objet addObjet(String nomObjet) {
        Objet objet = new Objet(nomObjet);
        return objetRep.save(objet);
    }

    @Override
    public void removeObjet(long id_objet) {
        objetRep.delete(id_objet);
    }
}
