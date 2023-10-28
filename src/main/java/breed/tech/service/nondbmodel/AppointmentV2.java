package breed.tech.service.nondbmodel;
import breed.tech.service.model.Horse;
import breed.tech.service.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentV2 implements Serializable {
    private Long id;
    private Horse horse;
    private User user;
    private User createdByUser;
    private String appointmentStatus;
    private Long createdAtTimestamp;
    private String humanReadableTimestamp;
    private SimpleDate simpleDate;
    private SimpleDate scheduledAtSimpleDate;
    private Long userId;
}