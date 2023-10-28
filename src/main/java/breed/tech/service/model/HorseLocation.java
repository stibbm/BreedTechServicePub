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
public class HorseLocation implements Serializable {

    @GeneratedValue @Id private Long id;

    @Column(nullable = false)
    private Long horseId;

    @Column(nullable = false)
    private Long timestamp; // unix time

    @Column(nullable = false)
    private Long stallId; // set to -1 if not in a stall
}