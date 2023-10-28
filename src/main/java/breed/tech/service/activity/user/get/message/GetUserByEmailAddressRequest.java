package breed.tech.service.activity.user.get.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserByEmailAddressRequest implements Serializable {
    private String emailAddress;
}