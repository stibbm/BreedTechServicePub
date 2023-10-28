package breed.tech.service.activity.user.create.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest implements Serializable {
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String password;
    private String accountType;
    private Long companyId;
}