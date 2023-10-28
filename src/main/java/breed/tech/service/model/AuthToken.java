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
public class AuthToken implements Serializable {
    @Id
    @GeneratedValue
    private Long authTokenId;

    @Column(nullable = false)
    private String emailAddress;

    @Column(nullable = false, unique = true)
    private String authToken;

    @Column(nullable = false)
    private String accountType;

    @Column(nullable = false)
    private Long validUntilUnixTime;

}