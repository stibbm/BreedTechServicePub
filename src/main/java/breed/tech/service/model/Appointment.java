package breed.tech.service.model;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Appointment implements Serializable {
    @Id @GeneratedValue private Long id;

    @Column(nullable = false)
    private Long horseId;

    @Column(nullable = false)
    private Long userId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String reason;

    @Column(nullable = false)
    private Long createdByUserId;

    @Column(nullable = false)
    private String appointmentStatus;

    @Column(nullable = false)
    private String notificationsPhoneNumber;

    @Column(nullable = false)
    private Long createdAtTimestamp;

    @Column(nullable = false)
    private Long scheduledAtTimestamp;

}