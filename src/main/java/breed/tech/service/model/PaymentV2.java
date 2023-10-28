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
public class PaymentV2 implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Long appointmentId;

    @Column(nullable = false)
    private Boolean isConfirmed;

    @Column(nullable = false)
    private String paymentLinkId;

    @Column(nullable = false)
    private Long amountCents;

    @Column(nullable = false)
    private Long createdAt;

    @Column(nullable = false)
    private Long userId;
}