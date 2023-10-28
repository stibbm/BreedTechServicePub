package breed.tech.service.activity.appointmentaction.get.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAppointmentActionsByUserIdV2Request implements Serializable {
    private String userId;
}