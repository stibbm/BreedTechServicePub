package breed.tech.service.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class BreedingContract implements Serializable {
    @Id @GeneratedValue private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String s3FilePath;

    @Column(nullable = false)
    private String s3BucketName;

    @Column(nullable = false)
    private Long horseId;
}