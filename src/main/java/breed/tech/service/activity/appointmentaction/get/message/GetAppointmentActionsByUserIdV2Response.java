package breed.tech.service.activity.appointmentaction.get.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nondbmodel.AppointmentActionV3;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAppointmentActionsByUserIdV2Response implements Serializable {
    private List<AppointmentActionV3> appointmentActionsList;
}