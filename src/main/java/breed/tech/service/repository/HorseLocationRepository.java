package breed.tech.service.repository;

import breed.tech.service.model.HorseLocation;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Configuration
public interface HorseLocationRepository extends CrudRepository<HorseLocation, Long> {

    HorseLocation findFirstByHorseIdOrderByTimestampDesc(Long horseId);
}