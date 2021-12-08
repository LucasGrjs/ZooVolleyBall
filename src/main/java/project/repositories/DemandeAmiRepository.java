package project.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project.model.DemandeAmi;
import project.model.User;

@Repository
public interface DemandeAmiRepository extends CrudRepository<DemandeAmi, Long> {
    DemandeAmi findByReceveur(User receveur);
}
