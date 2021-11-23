package project.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project.model.Objet;

@Repository
public interface ObjetRepository extends CrudRepository<Objet, Long> {

}
