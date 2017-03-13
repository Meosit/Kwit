package by.mksn.kwitapi.repository;

import by.mksn.kwitapi.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);

}
