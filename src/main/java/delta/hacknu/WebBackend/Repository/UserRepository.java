package delta.hacknu.WebBackend.Repository;

import delta.hacknu.WebBackend.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Behruz Mansurov
 */

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String name);
}
