package breed.tech.service.activity.appointment.get.message;

import breed.tech.service.model.Appointment;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllAppointmentsResponse implements Serializable {
    private List<Appointment> appointmentsList;
}