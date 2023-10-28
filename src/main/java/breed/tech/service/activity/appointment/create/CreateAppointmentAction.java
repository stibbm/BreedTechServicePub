package breed.tech.service.activity.appointment.create;

import static breed.tech.service.constants.Constants.ALL_ORIGINS;
import static breed.tech.service.constants.Constants.GSON;

import breed.tech.service.activity.appointment.create.message.CreateAppointmentRequest;
import breed.tech.service.activity.appointment.create.message.CreateAppointmentResponse;
import breed.tech.service.constants.CustomHttpHeaders;
import breed.tech.service.constants.Routes;
import breed.tech.service.manager.AppointmentManager;
import breed.tech.service.manager.UserManager;
import breed.tech.service.model.Appointment;
import breed.tech.service.repository.AppointmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class CreateAppointmentAction {

    private AppointmentRepository appointmentRepository;
    private UserManager userManager;
    private AppointmentManager appointmentManager;

    /**
     * Constructor
     * @param appointmentRepository appointment repository
     * @param userManager user manager
     * @param appointmentManager appointment manager
     */
    @Autowired
    public CreateAppointmentAction(
            AppointmentRepository appointmentRepository,
            UserManager userManager,
            AppointmentManager appointmentManager
    ) {
        this.appointmentRepository = Validate.notNull(appointmentRepository);
        this.userManager = Validate.notNull(userManager);
        this.appointmentManager = Validate.notNull(appointmentManager);
    }

    /**
     * Create appointment
     * @param createAppointmentRequest CreateAppointmentRequest
     * @param authToken String
     * @return CreateAppointmentResponse
     */
    @PostMapping(value = Routes.CREATE_APPOINTMENT)
    @CrossOrigin(ALL_ORIGINS)
    public @ResponseBody ResponseEntity<?> createAppointment(
            @RequestBody CreateAppointmentRequest createAppointmentRequest,
            @RequestHeader(CustomHttpHeaders.AUTH_TOKEN) String authToken
    ) {
        log.info("CreateAppointmentAction");
        log.info(GSON.toJson(createAppointmentRequest));
        System.out.println("CreateAppointmentAction");
        System.out.println(GSON.toJson(createAppointmentRequest));
        List<Appointment> appointmentsList = this.appointmentManager
                .getActiveAppointmentsByHorseId(createAppointmentRequest.getHorseId(), authToken);
        if (appointmentsList.size() > 0) {
            return ResponseEntity.badRequest().body("Active appointment already exists");
        }
        Appointment appointment =
                Appointment.builder()
                        .horseId(createAppointmentRequest.getHorseId())
                        .createdByUserId(createAppointmentRequest.getCreatedByUserId())
                        .appointmentStatus(createAppointmentRequest.getAppointmentStatus())
                        .build();
        Appointment createdAppointment = this.appointmentRepository.save(appointment);
        CreateAppointmentResponse createAppointmentResponse =
                CreateAppointmentResponse.builder().appointment(createdAppointment).build();
        return ResponseEntity.ok(createAppointmentResponse);
    }
}