package breed.tech.service.activity.appointmentamountdue;

import breed.tech.service.activity.appointmentamountdue.message.GetAppointmentAmountDueRequest;
import breed.tech.service.activity.appointmentamountdue.message.GetAppointmentAmountDueResponse;
import breed.tech.service.constants.CustomHttpHeaders;
import breed.tech.service.constants.Routes;
import breed.tech.service.manager.AppointmentActionManager;
import breed.tech.service.manager.PaymentV2Manager;
import breed.tech.service.manager.UserManager;
import breed.tech.service.model.AppointmentAction;
import breed.tech.service.model.PaymentV2;
import breed.tech.service.model.User;
import breed.tech.service.repository.PaymentV2Repository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static breed.tech.service.constants.Constants.ALL_ORIGINS;
import static breed.tech.service.constants.Constants.GSON;

@RestController
@Slf4j
public class GetAppointmentAmountDueAction {

    private AppointmentActionManager appointmentActionManager;
    private PaymentV2Repository paymentV2Repository;
    private UserManager userManager;
    private PaymentV2Manager paymentV2Manager;

    /**
     * Constructor
     * @param appointmentActionManager appointment action manager
     * @param paymentV2Repository payment v2 repository
     * @param userManager user manager
     * @param paymentV2Manager payment v2 manager
     */
    @Autowired
    public GetAppointmentAmountDueAction(
            AppointmentActionManager appointmentActionManager,
            PaymentV2Repository paymentV2Repository,
            UserManager userManager,
            PaymentV2Manager paymentV2Manager
    )
    {
        this.appointmentActionManager = appointmentActionManager;
        this.paymentV2Repository = paymentV2Repository;
        this.userManager = userManager;
        this.paymentV2Manager = paymentV2Manager;
    }

    /**
     * Get appointment amount due
     * @param getAppointmentAmountDueRequest get appointment amount due request
     * @param authToken auth token
     * @return GetAppointmentAmountDueResponse
     */
    @PostMapping(value = Routes.GET_APPOINTMENT_AMOUNT_DUE)
    @CrossOrigin(ALL_ORIGINS)
    public @ResponseBody ResponseEntity<GetAppointmentAmountDueResponse> getAppointmentAmountDue(
            @RequestBody GetAppointmentAmountDueRequest getAppointmentAmountDueRequest,
            @RequestHeader(CustomHttpHeaders.AUTH_TOKEN) String authToken)
    {
        log.info("getAppointmentAmountDueRequest");
        User user = this.userManager.getUserByAuthToken(authToken);
        log.info(GSON.toJson(user));
        Long appointmentId = getAppointmentAmountDueRequest.getAppointmentId();
        List<AppointmentAction> appointmentActionsList = this.appointmentActionManager
                .getAppointmentActionsByAppointmentId(getAppointmentAmountDueRequest.getAppointmentId());
        List<PaymentV2> paymentV2sList = this.paymentV2Manager.getPaymentV2sByAppointmentId(appointmentId);
        long totalAmountDue = 0L;
        for (AppointmentAction appointmentAction : appointmentActionsList) {
            totalAmountDue += appointmentAction.getAmountCents();
        }
        long totalAmountPaid = 0L;
        for (PaymentV2 paymentV2: paymentV2sList) {
            totalAmountPaid += paymentV2.getAmountCents();
        }
        long currentAmountDue = totalAmountDue - totalAmountPaid;
        GetAppointmentAmountDueResponse getAppointmentAmountDueResponse = GetAppointmentAmountDueResponse
                .builder()
                .appointmentId(appointmentId)
                .appointmentActionsList(appointmentActionsList)
                .paymentV2sList(paymentV2sList)
                .totalAmountDue(totalAmountDue)
                .totalAmountPaid(totalAmountPaid)
                .currentAmountDue(currentAmountDue)
                .build();
        log.info(GSON.toJson(getAppointmentAmountDueResponse));
        return ResponseEntity.ok(getAppointmentAmountDueResponse);
    }

}