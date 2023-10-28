package breed.tech.service.repository;

import breed.tech.service.model.Stall;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Configuration
public interface StallRepository extends CrudRepository<Stall, Long> {

    Stall getStallByName(String name);

    List<Stall> getStallsByCompanyId(Long companyId);

    List<Stall> findByNameContainingIgnoreCase(String name);
}