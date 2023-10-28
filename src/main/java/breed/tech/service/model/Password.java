package breed.tech.service.model;

import java.io.Serializable;
import javax.persistence.*;
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
public class Password implements Serializable {

    @Id @GeneratedValue private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String hashedPassword;

    @Column(nullable = false)
    private Long creationTimestamp;
}