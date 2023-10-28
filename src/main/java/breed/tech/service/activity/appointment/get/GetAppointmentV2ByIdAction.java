package breed.tech.service.activity.appointment.get;

import breed.tech.service.activity.appointment.get.message.GetAppointmentV2ByIdRequest;
import breed.tech.service.activity.appointment.get.message.GetAppointmentV2ByIdResponse;
import breed.tech.service.constants.Routes;
import breed.tech.service.manager.AppointmentManager;
import breed.tech.service.manager.HorseManager;
import breed.tech.service.manager.UserManager;
import breed.tech.service.model.Appointment;
import breed.tech.service.model.Horse;
import breed.tech.service.model.User;
import breed.tech.service.nondbmodel.AppointmentV2;
import breed.tech.service.nondbmodel.SimpleDate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

import static breed.tech.service.constants.Constants.ALL_ORIGINS;
import static breed.tech.service.constants.Constants.GSON;

@RestController
@Slf4j
public class GetAppointmentV2ByIdAction {

    private AppointmentManager appointmentManager;
    private HorseManager horseManager;
    private UserManager userManager;

    /**
     * Constructor
     * @param appointmentManager appointment manager
     * @param horseManager horse manager
     * @param userManager user manager
     */
    @Autowired
    public GetAppointmentV2ByIdAction(
            AppointmentManager appointmentManager,
            HorseManager horseManager,
            UserManager userManager
    ) {
        this.appointmentManager = appointmentManager;
        this.horseManager = horseManager;
        this.userManager = userManager;
    }

    /**
     * Get Appointment v2 by id
     * @param getAppointmentV2ByIdRequest get appointment v2 by id request
     * @return GetAppointmentV2ByIdResponse
     */
    @PostMapping(Routes.GET_APPOINTMENT_V2_BY_ID)
    @CrossOrigin(ALL_ORIGINS)
    public @ResponseBody ResponseEntity<GetAppointmentV2ByIdResponse> getAppointmentV2ById(
            @RequestBody GetAppointmentV2ByIdRequest getAppointmentV2ByIdRequest
    )
    {
        log.info("getAppointmentV2ById");
        log.info(GSON.toJson(getAppointmentV2ByIdRequest));
        Appointment appointment = appointmentManager.getAppointmentById(getAppointmentV2ByIdRequest.getId());
        Horse horse = horseManager.getHorseById(appointment.getHorseId());
        User user = userManager.getUserById(appointment.getUserId());
        User createdByUser = userManager.getUserById(appointment.getCreatedByUserId());
        String appointmentStatus = appointment.getAppointmentStatus();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM d, yyyy 'at' h:mm a");
        String humanReadableTimestamp = simpleDateFormat.format(appointment.getCreatedAtTimestamp() * 1000);
        Date date = new Date(appointment.getCreatedAtTimestamp() * 1000);
        int dayOfMonth = date.getDate();
        int month = date.getMonth() + 1;
        int year = date.getYear();
        SimpleDate simpleDate = SimpleDate.builder()
                .year(year + 1900)
                .month(month)
                .day(dayOfMonth)
                .build();
        Date scheduledAtDate = new Date(appointment.getScheduledAtTimestamp() * 1000);
        SimpleDate scheduledAtSimpleDate = SimpleDate.builder()
                .year(scheduledAtDate.getDate() + 1900)
                .month(scheduledAtDate.getMonth() + 1)
                .day(scheduledAtDate.getDate())
                .build();
        AppointmentV2 appointmentV2 = AppointmentV2.builder()
                .id(appointment.getId())
                .horse(horse)
                .user(user)
                .createdByUser(createdByUser)
                .appointmentStatus(appointmentStatus)
                .createdAtTimestamp(appointment.getCreatedAtTimestamp())
                .humanReadableTimestamp(humanReadableTimestamp)
                .userId(appointment.getUserId())
                .simpleDate(simpleDate)
                .scheduledAtSimpleDate(scheduledAtSimpleDate)
                .build();
        GetAppointmentV2ByIdResponse getAppointmentV2ByIdResponse =
                GetAppointmentV2ByIdResponse.builder()
                        .appointmentV2(appointmentV2)
                        .build();
        return ResponseEntity.ok(getAppointmentV2ByIdResponse);
    }

}
