package breed.tech.service.manager.params;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendSMSArgs implements Serializable {
    private String sourcePhoneNumberString;
    private String destinationPhoneNumberString;
    private String messageString;
}