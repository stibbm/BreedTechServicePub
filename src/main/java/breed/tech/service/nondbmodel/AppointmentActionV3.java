package breed.tech.service.nondbmodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentActionV3 implements Serializable {
    private Long id;
    private String customerFirstAndLastName;
    private String description;
    private String humanReadableTimestamp;
    private String createdByUserFirstAndLastName;
    private String amount;
    private String smsMessage;
}