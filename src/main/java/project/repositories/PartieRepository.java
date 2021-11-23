package project.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project.model.Partie;

@Repository
public interface PartieRepository extends CrudRepository<Partie, Long> {

}
