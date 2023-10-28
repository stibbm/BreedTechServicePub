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
public class AppointmentActionTypeDisplayable implements Serializable {
    private Long id;
    private String name;
    private String description;
    private String amount;
    private String smsMessage;
}