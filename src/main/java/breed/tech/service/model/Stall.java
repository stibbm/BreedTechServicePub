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
public class Stall implements Serializable {

    @Id @GeneratedValue private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(columnDefinition = "TEXT", nullable = true)
    private String horseIdsList;

    @Column(nullable = false)
    private Integer capacity;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String notes;

    @Column(nullable = false)
    private Long companyId;
}