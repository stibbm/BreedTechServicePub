package breed.tech.service.activity.appointmentaction.create.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateAppointmentActionRequest implements Serializable {
    private Long appointmentId;
    private String description;
    private String smsMessage;
    private Long amountCents;
}