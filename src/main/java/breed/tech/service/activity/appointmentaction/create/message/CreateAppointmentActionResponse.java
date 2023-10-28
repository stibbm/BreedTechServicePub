package breed.tech.service.activity.appointmentaction.create.message;

import breed.tech.service.model.AppointmentAction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateAppointmentActionResponse implements Serializable {
    private AppointmentAction appointmentAction;
}