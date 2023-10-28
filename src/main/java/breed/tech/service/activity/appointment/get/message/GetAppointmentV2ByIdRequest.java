package breed.tech.service.activity.appointment.get.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAppointmentV2ByIdRequest implements Serializable {
    private Long id;
}