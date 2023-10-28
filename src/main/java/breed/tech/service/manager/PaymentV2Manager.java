package breed.tech.service.manager;
import breed.tech.service.model.PaymentV2;
import breed.tech.service.repository.PaymentV2Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PaymentV2Manager {

    private PaymentV2Repository paymentV2Repository;

    @Autowired
    public PaymentV2Manager(
            PaymentV2Repository paymentV2Repository
    ) {
        this.paymentV2Repository = paymentV2Repository;
    }

    public List<PaymentV2> getPaymentV2sByAppointmentId(Long appointmentId) {
        List<PaymentV2> paymentV2sList = new ArrayList<>();
        for (PaymentV2 paymentV2 : this.paymentV2Repository.findAll()) {
            paymentV2sList.add(paymentV2);
        }
        return paymentV2sList;
    }

}