package breed.tech.service.activity.appointment.get.message;

import breed.tech.service.nondbmodel.AppointmentV2;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAppointmentV2ByIdResponse implements Serializable {
    private AppointmentV2 appointmentV2;
}