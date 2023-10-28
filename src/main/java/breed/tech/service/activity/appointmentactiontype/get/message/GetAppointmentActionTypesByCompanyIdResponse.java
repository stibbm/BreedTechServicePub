package breed.tech.service.activity.appointmentactiontype.get.message;
import breed.tech.service.model.AppointmentActionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAppointmentActionTypesByCompanyIdResponse implements Serializable {
    private List<AppointmentActionType> appointmentActionTypesList;
}