package breed.tech.service.repository;


import breed.tech.service.model.AppointmentAction;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Configuration
public interface AppointmentActionRepository extends CrudRepository<AppointmentAction, Long> {
    List<AppointmentAction> findAllByAppointmentId(Long appointmentId);
    List<AppointmentAction> findAllByTimestampAfterAndTimestampBefore(Long startTimestamp, Long endTimestamp);
    //List<AppointmentAction> findAppointmentActions
}