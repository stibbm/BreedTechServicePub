package breed.tech.service.adapter;

import breed.tech.service.model.AppointmentActionType;
import breed.tech.service.nondbmodel.AppointmentActionTypeDisplayable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static breed.tech.service.constants.Constants.GSON;

@Slf4j
@Service
public class AppointmentActionTypeAdapter {

    public AppointmentActionTypeDisplayable convertToAppointmentActionTypeDisplayable(
            AppointmentActionType appointmentActionType
    ) {
        log.info("convertToAppointmentActionTypeDisplayable: " + GSON.toJson(appointmentActionType));
        BigDecimal amountBigDecimal = new BigDecimal(appointmentActionType.getAmountCents()).movePointLeft(2);
        String amountString = "$" + amountBigDecimal.toString();
        AppointmentActionTypeDisplayable appointmentActionTypeDisplayable =
                AppointmentActionTypeDisplayable.builder()
                        .id(appointmentActionType.getId())
                        .name(appointmentActionType.getName())
                        .description(appointmentActionType.getDescription())
                        .amount(amountString)
                        .smsMessage(appointmentActionType.getSmsMessage())
                        .build();
        log.info("appointmentActionDisplayable: " + GSON.toJson(appointmentActionTypeDisplayable));
        return appointmentActionTypeDisplayable;
    }

}