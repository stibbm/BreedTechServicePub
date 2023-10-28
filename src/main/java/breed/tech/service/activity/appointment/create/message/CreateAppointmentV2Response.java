package breed.tech.service.activity.appointment.create.message;

import breed.tech.service.model.Appointment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateAppointmentV2Response implements Serializable {
    private Appointment appointment;
}