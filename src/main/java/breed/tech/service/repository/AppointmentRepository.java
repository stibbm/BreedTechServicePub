package breed.tech.service.repository;

import breed.tech.service.model.Appointment;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Configuration
@Repository
public interface AppointmentRepository extends CrudRepository<Appointment, Long> {
    List<Appointment> getAppointmentsByHorseId(Long horseId);

    List<Appointment> getAppointmentsByCreatedByUserId(Long createdByUserId);

    List<Appointment> findByHorseId(Long horseId);

    Iterable<Appointment> findAppointmentsByHorseId(Long horseId);

    List<Appointment> findAllByUserId(Long userId);

    Iterable<Appointment> findAppointmentsByUserId(Long userId);

}