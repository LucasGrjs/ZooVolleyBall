package project.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project.model.DemandeAmi;
import project.model.User;

import java.util.List;

@Repository
public interface DemandeAmiRepository extends CrudRepository<DemandeAmi, Long> {
    List<DemandeAmi> findByReceveur(User receveur);
}
