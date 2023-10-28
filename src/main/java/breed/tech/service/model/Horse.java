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
@Table
@Entity
public class Horse implements Serializable {
    @Id @GeneratedValue private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Boolean isStallion;

    @Column(nullable = false, unique = true)
    private String registrationNumber;

    @Column(nullable = false)
    private String dam;

    @Column(nullable = false)
    private String sire;

    @Column(nullable = false)
    private String damSire;

    @Column(nullable = false)
    private Long ownerUserId;

}