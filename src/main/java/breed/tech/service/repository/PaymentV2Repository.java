package breed.tech.service.repository;

import breed.tech.service.model.PaymentV2;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Configuration
public interface PaymentV2Repository extends CrudRepository<PaymentV2, Long> {

}