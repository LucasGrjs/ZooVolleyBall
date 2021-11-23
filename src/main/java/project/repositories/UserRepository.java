package project.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}
