package breed.tech.service.repository;

import breed.tech.service.model.Company;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Configuration
public interface CompanyRepository extends CrudRepository<Company, Long> {}