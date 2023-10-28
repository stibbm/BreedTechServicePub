package breed.tech.service.activity.appointment.create.message;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateAppointmentRequest implements Serializable {
    private Long horseId;
    private Long createdByUserId;
    private String appointmentStatus;
}