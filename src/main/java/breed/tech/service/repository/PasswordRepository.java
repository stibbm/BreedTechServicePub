package breed.tech.service.repository;

import breed.tech.service.model.Password;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Configuration
public interface PasswordRepository extends CrudRepository<Password, Long> {
    // only retrieves hashed/salted passwords
    List<Password> getPasswordsByUserId(Long userId);
}