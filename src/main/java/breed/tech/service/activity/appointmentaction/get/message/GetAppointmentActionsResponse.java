package breed.tech.service.activity.appointmentaction.get.message;

import breed.tech.service.model.AppointmentAction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAppointmentActionsResponse implements Serializable {
    private List<AppointmentAction> appointmentActionsList;
}