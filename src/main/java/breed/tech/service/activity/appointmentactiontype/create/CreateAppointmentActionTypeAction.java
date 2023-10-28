package breed.tech.service.activity.appointmentactiontype.create;

import breed.tech.service.activity.appointmentactiontype.create.message.CreateAppointmentActionTypeRequest;
import breed.tech.service.activity.appointmentactiontype.create.message.CreateAppointmentActionTypeResponse;
import breed.tech.service.constants.CustomHttpHeaders;
import breed.tech.service.constants.Routes;
import breed.tech.service.manager.UserManager;
import breed.tech.service.manager.stripe.StripePriceManager;
import breed.tech.service.manager.stripe.StripeProductManager;
import breed.tech.service.model.AppointmentActionType;
import breed.tech.service.model.User;
import breed.tech.service.repository.AppointmentActionTypeRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.Price;
import com.stripe.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static breed.tech.service.constants.Constants.ALL_ORIGINS;
import static breed.tech.service.constants.Constants.GSON;

@RestController
@Slf4j
public class CreateAppointmentActionTypeAction {

    private AppointmentActionTypeRepository appointmentActionTypeRepository;
    private UserManager userManager;
    private StripeProductManager stripeProductManager;
    private StripePriceManager stripePriceManager;

    /**
     * Constructor
     * @param appointmentActionTypeRepository appointment action type repository
     * @param userManager user manager
     * @param stripeProductManager stripe product manager
     * @param stripePriceManager stripe price manager
     */
    @Autowired
    public CreateAppointmentActionTypeAction(
            AppointmentActionTypeRepository appointmentActionTypeRepository,
            UserManager userManager,
            StripeProductManager stripeProductManager,
            StripePriceManager stripePriceManager
    ) {
        this.appointmentActionTypeRepository = appointmentActionTypeRepository;
        this.userManager = userManager;
        this.stripeProductManager = stripeProductManager;
        this.stripePriceManager = stripePriceManager;
    }

    /**
     * Create appointment action type
     * @param createAppointmentActionTypeRequest create appointment action type request
     * @param authToken auth token
     * @return CreateAppointmentActionTypeResponse
     * @throws StripeException stripe exception
     */
    @PostMapping(value = Routes.CREATE_APPOINTMENT_ACTION_TYPE)
    @CrossOrigin(ALL_ORIGINS)
    public @ResponseBody ResponseEntity<CreateAppointmentActionTypeResponse> createAppointmentAction(
            @RequestBody CreateAppointmentActionTypeRequest createAppointmentActionTypeRequest,
            @RequestHeader(CustomHttpHeaders.AUTH_TOKEN) String authToken
    ) throws StripeException {
        log.info("createAppointmentActionType: " + GSON.toJson(createAppointmentActionTypeRequest));

        User user = userManager.getUserByAuthToken(authToken);
        Product product = stripeProductManager.createProduct(
                createAppointmentActionTypeRequest.getName(),
                createAppointmentActionTypeRequest.getDescription()
        );
        String productId = product.getId();
        int amountCents = createAppointmentActionTypeRequest.getAmountCents().intValue();
        //int amountCents = Integer.parseInt("" + createAppointmentActionTypeRequest.getAmountCents());
        Price price = stripePriceManager.createPrice(
                amountCents,
                "usd",
                productId
        );
        String priceId = price.getId();

        AppointmentActionType unsavedAppointmentActionType = AppointmentActionType.builder()
                .name(createAppointmentActionTypeRequest.getName())
                .amountCents(createAppointmentActionTypeRequest.getAmountCents())
                .description(createAppointmentActionTypeRequest.getDescription())
                .smsMessage(createAppointmentActionTypeRequest.getSmsMessage())
                .companyId(user.getCompanyId())
                .stripeProductId(productId)
                .stripePriceId(priceId)
                .build();
        AppointmentActionType savedAppointmentActionType = appointmentActionTypeRepository
                .save(unsavedAppointmentActionType);
        CreateAppointmentActionTypeResponse createAppointmentActionTypeResponse = CreateAppointmentActionTypeResponse
                .builder()
                .appointmentActionType(savedAppointmentActionType)
                .build();
        log.info("createAppointmentActionTypeResponse: " + GSON.toJson(createAppointmentActionTypeResponse));
        return ResponseEntity.ok(createAppointmentActionTypeResponse);
    }

}