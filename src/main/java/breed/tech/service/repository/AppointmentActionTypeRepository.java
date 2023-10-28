package breed.tech.service.repository;

import breed.tech.service.model.AppointmentActionType;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Configuration
public interface AppointmentActionTypeRepository extends CrudRepository<AppointmentActionType, Long> {
    List<AppointmentActionType> findAppointmentActionTypesByCompanyId(Long companyId);
}