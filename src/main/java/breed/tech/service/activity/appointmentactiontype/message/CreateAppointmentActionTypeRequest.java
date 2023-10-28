package breed.tech.service.activity.appointmentactiontype.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateAppointmentActionTypeRequest implements Serializable {
    private String name;
    private String description;
    private Long amountCents;
    private String smsMessage;
}