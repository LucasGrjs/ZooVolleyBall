package project.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project.model.Objet;
import project.model.User;

import java.util.List;

@Repository
public interface ObjetRepository extends CrudRepository<Objet, Long> {

}
