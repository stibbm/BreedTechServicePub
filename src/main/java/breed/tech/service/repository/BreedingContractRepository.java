package breed.tech.service.repository;

import breed.tech.service.model.BreedingContract;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Configuration
public interface BreedingContractRepository extends CrudRepository<BreedingContract, Long> {
    List<BreedingContract> findBreedingContractsByHorseId(Long horseId);
}