package breed.tech.service.activity.appointment.create.message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateAppointmentV2Request implements Serializable {
    private Long horseId;
    private Long userId;
    private String reason;
    private String notificationsPhoneNumber;
}