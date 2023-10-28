package breed.tech.service.model;
import javax.persistence.*;
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
public class Company {

    @Id @GeneratedValue private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String contactFirstName;

    @Column(nullable = false)
    private String contactLastName;

    @Column(nullable = false)
    private String contactPhoneNumber;

    @Column(nullable = false)
    private String contactEmailAddress;
}