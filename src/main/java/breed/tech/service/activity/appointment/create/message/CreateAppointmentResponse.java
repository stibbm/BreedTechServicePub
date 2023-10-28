package breed.tech.service.activity.appointment.create.message;

import breed.tech.service.model.Appointment;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateAppointmentResponse implements Serializable {
    private Appointment appointment;
}