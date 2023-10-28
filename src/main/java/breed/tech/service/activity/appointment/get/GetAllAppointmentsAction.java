package breed.tech.service.activity.appointment.get;

import static breed.tech.service.constants.Constants.ALL_ORIGINS;
import static breed.tech.service.constants.Constants.GSON;

import breed.tech.service.activity.appointment.get.message.GetAllAppointmentsResponse;
import breed.tech.service.constants.CustomHttpHeaders;
import breed.tech.service.constants.Routes;
import breed.tech.service.model.Appointment;
import breed.tech.service.repository.AppointmentRepository;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class GetAllAppointmentsAction {

    private AppointmentRepository appointmentRepository;

    /**
     * Constructor
     * @param appointmentRepository appointment repository
     */
    @Autowired
    public GetAllAppointmentsAction(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = Validate.notNull(appointmentRepository);
    }

    /**
     * Get all appointments
     * @param authToken auth token
     * @return GetAllAppointmentsResponse
     */
    @PostMapping(value = Routes.GET_ALL_APPOINTMENTS)
    @CrossOrigin(ALL_ORIGINS)
    public @ResponseBody ResponseEntity<GetAllAppointmentsResponse> act(
            @RequestHeader(CustomHttpHeaders.AUTH_TOKEN) String authToken
    ) {
        log.info("GetAllAppointments action");
        List<Appointment> appointmentsList = new ArrayList<>();
        Iterable<Appointment> appointmentsIterable = this.appointmentRepository.findAll();
        for (Appointment appointment : appointmentsIterable) {
            appointmentsList.add(appointment);
        }
        GetAllAppointmentsResponse getAllAppointmentsResponse =
                GetAllAppointmentsResponse.builder().appointmentsList(appointmentsList).build();
        log.info("GetAllAppointments");
        log.info(GSON.toJson(getAllAppointmentsResponse));
        return ResponseEntity.ok(getAllAppointmentsResponse);
    }
}