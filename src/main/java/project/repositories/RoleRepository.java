package project.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project.model.ERole;
import project.model.Roles;
import project.model.User;

import java.util.List;

@Repository
public interface RoleRepository extends CrudRepository<Roles,Long> {

    List<Roles> findAllByUser(User u);

    List<Roles> findAllByRole(ERole role);


}
