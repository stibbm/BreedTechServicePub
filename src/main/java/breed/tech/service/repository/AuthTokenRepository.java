package breed.tech.service.repository;

import breed.tech.service.model.AuthToken;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Configuration
@Repository
public interface AuthTokenRepository extends CrudRepository<AuthToken, Long> {

    AuthToken findByAuthToken(String authToken);

    List<AuthToken> findAllByEmailAddressIgnoreCase(String emailAddress);

}