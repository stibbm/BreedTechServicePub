package breed.tech.service.activity.user.get.message;

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
public class GetUserByEmailAddressResponse implements Serializable {
    private User user;
}