package breed.tech.service.activity.user.create.message;

import breed.tech.service.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserResponse implements Serializable {
    // createdSuccessfully
    // userAlreadyExistsWithThatEmailAddress ( email uniqueness is case insensitive: E.g. matt@gmail.com
    // will be treated as the same thing as "MATT@gmail.com)"
    private String message;
    private User user;
}