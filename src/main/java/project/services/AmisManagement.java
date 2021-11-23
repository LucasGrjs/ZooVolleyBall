package project.services;

import org.springframework.beans.factory.annotation.Autowired;
import project.model.Amis;
import project.model.User;
import project.repositories.AmisRepository;

public class AmisManagement implements IAmisManagement {

    @Autowired
    AmisRepository amisRep;

    @Override
    public Iterable<Amis> getAllAmis() {
        return amisRep.findAll();
    }

    @Override
    public Amis addAmis(User user_1, User user_2) {
        Amis amis = new Amis(user_1, user_2);
        return amisRep.save(amis);
    }

    @Override
    public void removeAmis(long id_lien) {
        amisRep.delete(id_lien);
    }
}
