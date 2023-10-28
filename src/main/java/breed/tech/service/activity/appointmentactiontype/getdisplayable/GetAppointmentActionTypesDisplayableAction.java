package breed.tech.service.activity.appointmentactiontype.getdisplayable;

import breed.tech.service.activity.appointmentactiontype.getdisplayable.message.GetAppointmentActionTypesDisplayableResponse;
import breed.tech.service.adapter.AppointmentActionTypeAdapter;
import breed.tech.service.constants.CustomHttpHeaders;
import breed.tech.service.constants.Routes;
import breed.tech.service.manager.UserManager;
import breed.tech.service.model.AppointmentActionType;
import breed.tech.service.model.User;
import breed.tech.service.nondbmodel.AppointmentActionTypeDisplayable;
import breed.tech.service.repository.AppointmentActionTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static breed.tech.service.constants.Constants.ALL_ORIGINS;
import static breed.tech.service.constants.Constants.GSON;

@RestController
@Slf4j
public class GetAppointmentActionTypesDisplayableAction {

    private AppointmentActionTypeRepository appointmentActionTypeRepository;
    private UserManager userManager;
    private AppointmentActionTypeAdapter appointmentActionTypeAdapter;

    /**
     * Constructor
     * @param appointmentActionTypeRepository appointment action type repository
     * @param userManager user manager
     * @param appointmentActionTypeAdapter appointment action type adapter
     */
    @Autowired
    public GetAppointmentActionTypesDisplayableAction(
            AppointmentActionTypeRepository appointmentActionTypeRepository,
            UserManager userManager,
            AppointmentActionTypeAdapter appointmentActionTypeAdapter
    ) {
        this.appointmentActionTypeRepository = appointmentActionTypeRepository;
        this.userManager = userManager;
        this.appointmentActionTypeAdapter = appointmentActionTypeAdapter;
    }

    /**
     * Get appointment action types displayable
     * @param authToken auth token
     * @return GetAppointmentActionTypesDisplayableResponse
     */
    @PostMapping(value = Routes.GET_APPOINTMENT_ACTION_TYPES_DISPLAYABLE)
    @CrossOrigin(ALL_ORIGINS)
    public @ResponseBody ResponseEntity<GetAppointmentActionTypesDisplayableResponse> getAppointmentActionTypesDisplayable(
            @RequestHeader(CustomHttpHeaders.AUTH_TOKEN) String authToken
    ) {
        log.info("getAppointmentActionTypesDisplayable");
        User user = this.userManager.getUserByAuthToken(authToken);
        List<AppointmentActionType> appointmentActionTypesList =
                this.appointmentActionTypeRepository.findAppointmentActionTypesByCompanyId(user.getCompanyId());
        List<AppointmentActionTypeDisplayable> appointmentActionTypeDisplayablesList = new ArrayList<>();
        for (AppointmentActionType appointmentActionType : appointmentActionTypesList) {
            AppointmentActionTypeDisplayable appointmentActionTypeDisplayable = appointmentActionTypeAdapter
                    .convertToAppointmentActionTypeDisplayable(appointmentActionType);
            appointmentActionTypeDisplayablesList.add(appointmentActionTypeDisplayable);
        }
        GetAppointmentActionTypesDisplayableResponse getAppointmentActionTypesDisplayableResponse =
                GetAppointmentActionTypesDisplayableResponse.builder()
                        .appointmentActionTypeDisplayablesList(appointmentActionTypeDisplayablesList)
                        .build();
        log.info("getAppointmentActionTypesDisplayableResponse: " + GSON.toJson(appointmentActionTypeDisplayablesList));
        return ResponseEntity.ok(getAppointmentActionTypesDisplayableResponse);
    }

}