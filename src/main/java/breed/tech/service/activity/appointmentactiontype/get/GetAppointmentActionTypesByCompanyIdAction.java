package breed.tech.service.activity.appointmentactiontype.get;

import breed.tech.service.activity.appointmentactiontype.get.message.GetAppointmentActionTypesByCompanyIdResponse;
import breed.tech.service.constants.CustomHttpHeaders;
import breed.tech.service.constants.Routes;
import breed.tech.service.manager.UserManager;
import breed.tech.service.model.AppointmentActionType;
import breed.tech.service.model.User;
import breed.tech.service.repository.AppointmentActionTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static breed.tech.service.constants.Constants.ALL_ORIGINS;
import static breed.tech.service.constants.Constants.GSON;

@RestController
@Slf4j
public class GetAppointmentActionTypesByCompanyIdAction {

    private AppointmentActionTypeRepository appointmentActionTypeRepository;
    private UserManager userManager;

    @Autowired
    public GetAppointmentActionTypesByCompanyIdAction(
            AppointmentActionTypeRepository appointmentActionTypeRepository,
            UserManager userManager) {
        this.appointmentActionTypeRepository = appointmentActionTypeRepository;
        this.userManager = userManager;

    }

    @PostMapping(value = Routes.GET_APPOINTMENT_ACTION_TYPES_BY_COMPANY_ID)
    @CrossOrigin(ALL_ORIGINS)
    public @ResponseBody ResponseEntity<GetAppointmentActionTypesByCompanyIdResponse> getAppointmentActionTypesByCompanyId(
            @RequestHeader(CustomHttpHeaders.AUTH_TOKEN) String authToken
    )
    {
        log.info("authToken: " + authToken);
        User user = this.userManager.getUserByAuthToken(authToken);
        List<AppointmentActionType> appointmentActionTypesList = this.appointmentActionTypeRepository
                .findAppointmentActionTypesByCompanyId(user.getCompanyId());
        GetAppointmentActionTypesByCompanyIdResponse getAppointmentActionTypesByCompanyIdResponse =
                GetAppointmentActionTypesByCompanyIdResponse.builder()
                        .appointmentActionTypesList(appointmentActionTypesList)
                        .build();
        log.info("getAppointmentActionTypes: " + GSON.toJson(getAppointmentActionTypesByCompanyIdResponse));
        return ResponseEntity.ok(getAppointmentActionTypesByCompanyIdResponse);
    }

}