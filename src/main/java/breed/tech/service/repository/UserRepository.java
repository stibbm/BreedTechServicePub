package breed.tech.service.repository;

import breed.tech.service.model.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Configuration
public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmailAddress(String emailAddress);
    User findByEmailAddressIgnoreCase(String emailAddress);
    Iterable<User> findByCompanyId(Long companyId);
}