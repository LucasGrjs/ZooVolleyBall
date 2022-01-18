package project.repositories;

import org.springframework.data.repository.CrudRepository;
import project.model.DemandeAmi;
import project.model.DemandePartie;
import project.model.User;

import java.util.List;

public interface DemandePartieRepository extends CrudRepository<DemandePartie, Long> {
    List<DemandePartie> findByReceveur(User receveur);
}
