package breed.tech.service.activity.appointment.create;

import breed.tech.service.activity.appointment.create.message.CreateAppointmentV2Request;
import breed.tech.service.activity.appointment.create.message.CreateAppointmentV2Response;
import breed.tech.service.constants.CustomHttpHeaders;
import breed.tech.service.constants.Routes;
import breed.tech.service.manager.AppointmentActionManager;
import breed.tech.service.manager.AppointmentManager;
import breed.tech.service.manager.HorseManager;
import breed.tech.service.manager.UserManager;
import breed.tech.service.model.Appointment;
import breed.tech.service.model.Horse;
import breed.tech.service.model.User;
import breed.tech.service.model.enums.AppointmentStatus;
import breed.tech.service.repository.AppointmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;

import static breed.tech.service.constants.Constants.ALL_ORIGINS;
import static breed.tech.service.constants.Constants.GSON;

@RestController
@Slf4j
public class CreateAppointmentV2Action {

    private AppointmentRepository appointmentRepository;
    private UserManager userManager;
    private HorseManager horseManager;
    private AppointmentActionManager appointmentActionManager;
    private AppointmentManager appointmentManager;

    /**
     * Constructor
     * @param appointmentRepository appointment repository
     * @param userManager user manager
     * @param horseManager horse manager
     * @param appointmentActionManager appointment action manager
     * @param appointmentManager appointment manager
     */
    @Autowired
    public CreateAppointmentV2Action(
            AppointmentRepository appointmentRepository,
            UserManager userManager,
            HorseManager horseManager,
            AppointmentActionManager appointmentActionManager,
            AppointmentManager appointmentManager
    ) {
        this.appointmentRepository = Validate.notNull(appointmentRepository);
        this.userManager = Validate.notNull(userManager);
        this.horseManager = Validate.notNull(horseManager);
        this.appointmentActionManager = Validate.notNull(appointmentActionManager);
        this.appointmentManager = Validate.notNull(appointmentManager);
    }

    /**
     * Create Appointment
     * @param createAppointmentV2Request create appointment v2 request
     * @param authToken auth token
     * @return CreateAppointmentV2Response
     */
    @PostMapping(value = Routes.CREATE_APPOINTMENTV2)
    @CrossOrigin(ALL_ORIGINS)
    public @ResponseBody ResponseEntity<?> createAppointment(
            @RequestBody CreateAppointmentV2Request createAppointmentV2Request,
            @RequestHeader(CustomHttpHeaders.AUTH_TOKEN) String authToken
    ) {
        List<Appointment> activeAppointmentsList = this.appointmentManager.getActiveAppointmentsByHorseId(
                createAppointmentV2Request.getHorseId(), authToken
        );
        if (activeAppointmentsList.size() > 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("There is already an active appointment for that horse");
        }
        log.info("CreateAppointmentV2");
        log.info(GSON.toJson(createAppointmentV2Request));
        User user = userManager.getUserById(createAppointmentV2Request.getUserId());
        User createdByUser = userManager.getUserByAuthToken(authToken);
        Long timestamp = Instant.now().getEpochSecond();
        Horse horse = horseManager.getHorseById(createAppointmentV2Request.getHorseId());
        Appointment appointmentToSave = Appointment.builder()
                .createdByUserId(createdByUser.getId())
                .userId(user.getId())
                .horseId(horse.getId())
                .appointmentStatus(AppointmentStatus.SCHEDULED)
                .reason(createAppointmentV2Request.getReason())
                .notificationsPhoneNumber(createAppointmentV2Request.getNotificationsPhoneNumber())
                .createdAtTimestamp(timestamp)
                .scheduledAtTimestamp(timestamp)
                .build();
        Appointment savedAppointment = this.appointmentRepository.save(appointmentToSave);
        CreateAppointmentV2Response createAppointmentV2Response = CreateAppointmentV2Response.builder()
                .appointment(savedAppointment)
                .build();
        log.info("CreateAppointmentV2Response: " + GSON.toJson(createAppointmentV2Response));
        return ResponseEntity.ok(createAppointmentV2Response);
    }
}