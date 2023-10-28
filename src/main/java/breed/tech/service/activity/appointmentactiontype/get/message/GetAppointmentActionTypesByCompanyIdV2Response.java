package breed.tech.service.activity.appointmentactiontype.get.message;

import breed.tech.service.nondbmodel.AppointmentActionTypeDisplayable;
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
public class GetAppointmentActionTypesByCompanyIdV2Response implements Serializable {
    private List<AppointmentActionTypeDisplayable> appointmentActionTypesList;
}