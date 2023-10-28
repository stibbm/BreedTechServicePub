package breed.tech.service.adapter;

import breed.tech.service.manager.AppointmentManager;
import breed.tech.service.manager.UserManager;
import breed.tech.service.model.Appointment;
import breed.tech.service.model.AppointmentAction;
import breed.tech.service.model.User;
import lombok.extern.slf4j.Slf4j;
import breed.tech.service.nondbmodel.AppointmentActionV3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import static breed.tech.service.constants.Constants.GSON;

@Service
@Slf4j
public class AppointmentActionAdapter {

    private UserManager userManager;
    private AppointmentManager appointmentManager;

    @Autowired
    public AppointmentActionAdapter(
            UserManager userManager,
            AppointmentManager appointmentManager
    ) {
        this.userManager = userManager;
        this.appointmentManager = appointmentManager;
    }

    public AppointmentActionV3 convertToAppointmentActionV3(AppointmentAction appointmentAction) {
        User createdByUser = this.userManager.getUserById(appointmentAction.getCreatedByUserId());
        Appointment appointment = this.appointmentManager.getAppointmentById(appointmentAction.getAppointmentId());
        User customerUser = userManager.getUserById(appointment.getUserId());
        String customerFirstAndLastName = customerUser.getFirstName() + " " + customerUser.getLastName();
        String createdByUserFirstAndLastName = createdByUser.getFirstName() + " " + createdByUser.getLastName();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM d, yyyy 'at' h:mm a");
        String humanReadableTimestamp = simpleDateFormat.format(appointmentAction.getTimestamp() * 1000);
        BigDecimal amountBigDecimal = new BigDecimal(appointmentAction.getAmountCents());
        amountBigDecimal = amountBigDecimal.movePointLeft(2);
        AppointmentActionV3 appointmentActionV3 = AppointmentActionV3.builder()
                .id(appointmentAction.getId())
                .customerFirstAndLastName(customerFirstAndLastName)
                .description(appointmentAction.getDescription())
                .createdByUserFirstAndLastName(createdByUserFirstAndLastName)
                .humanReadableTimestamp(humanReadableTimestamp)
                .amount("$" + amountBigDecimal.toString())
                .smsMessage(appointmentAction.getSmsMessage())
                .build();
        log.info("Converted AppointmentActionV3: " + GSON.toJson(appointmentActionV3));
        return appointmentActionV3;
    }
}