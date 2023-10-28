package breed.tech.service.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class AppointmentActionType implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String smsMessage;

    @Column(nullable = false)
    private Long amountCents;

    @Column(nullable = false)
    private Long companyId;

    @Column(nullable = false)
    private String stripeProductId;

    @Column(nullable = false)
    private String stripePriceId;

}