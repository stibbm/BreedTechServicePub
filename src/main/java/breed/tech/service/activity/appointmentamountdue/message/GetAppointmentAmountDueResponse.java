package breed.tech.service.activity.appointmentamountdue.message;

import breed.tech.service.model.AppointmentAction;
import breed.tech.service.model.PaymentV2;
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
public class GetAppointmentAmountDueResponse implements Serializable {
    private Long appointmentId;
    private List<AppointmentAction> appointmentActionsList;
    private List<PaymentV2> paymentV2sList;
    private Long totalAmountDue;
    private Long totalAmountPaid;
    private Long currentAmountDue;
}