package co.yasok.npdoc.repo;


import co.yasok.npdoc.entity.User;
import co.yasok.npdoc.entity.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAndPassword(String email, String password);
    List<User> findByUserType(UserType userType);
}
