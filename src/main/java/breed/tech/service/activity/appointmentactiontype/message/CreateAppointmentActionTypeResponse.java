package breed.tech.service.activity.appointmentactiontype.message;

import breed.tech.service.model.AppointmentActionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateAppointmentActionTypeResponse implements Serializable {
    private AppointmentActionType appointmentActionType;
}