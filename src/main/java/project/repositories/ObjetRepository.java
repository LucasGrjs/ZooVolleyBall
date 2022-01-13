package project.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project.model.Objet;
import project.model.User;

import java.util.List;

@Repository
public interface ObjetRepository extends CrudRepository<Objet, Long> {
    @Query("select o from Objet o where o.id_objet = ?1")
    Objet findById_objet(long Id_objet);
}
