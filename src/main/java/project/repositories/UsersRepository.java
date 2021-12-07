package project.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project.model.User;

@Repository
public interface UsersRepository extends CrudRepository<User, Long>
{
    User findByEmail(String email);
    User findByPseudo(String pseudo);
}
