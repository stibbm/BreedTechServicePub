package breed.tech.service.repository;

import breed.tech.service.model.Horse;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Configuration
@Repository
public interface HorseRepository extends CrudRepository<Horse, Long> {

    Iterable<Horse> findByOwnerUserId(Long ownerUserId);

    Horse findByRegistrationNumber(String registrationNumber);

    @Query("SELECT DISTINCT h.id FROM Horse h")
    List<Long> findDistinctHorseIds();

    List<Horse> findByNameContainingIgnoreCase(String name);

    Iterable<Horse> findHorsesByName(String name);
}