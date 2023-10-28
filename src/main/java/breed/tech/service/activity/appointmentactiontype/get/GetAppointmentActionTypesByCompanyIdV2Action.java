package breed.tech.service.activity.appointmentactiontype.get;

import breed.tech.service.activity.appointmentactiontype.get.message.GetAppointmentActionTypesByCompanyIdV2Response;
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

@RestController
@Slf4j
public class GetAppointmentActionTypesByCompanyIdV2Action {

    private AppointmentActionTypeRepository appointmentActionTypeRepository;
    private UserManager userManager;
    private AppointmentActionTypeAdapter appointmentActionTypeAdapter;

    @Autowired
    public GetAppointmentActionTypesByCompanyIdV2Action(
            AppointmentActionTypeRepository appointmentActionTypeRepository,
            UserManager userManager,
            AppointmentActionTypeAdapter appointmentActionTypeAdapter
    ) {
        this.appointmentActionTypeRepository = appointmentActionTypeRepository;
        this.userManager = userManager;
        this.appointmentActionTypeAdapter = appointmentActionTypeAdapter;
    }

    @PostMapping(value = Routes.GET_APPOINTMENT_ACTION_TYPES_BY_COMPANY_ID_V2)
    @CrossOrigin(ALL_ORIGINS)
    public @ResponseBody ResponseEntity<GetAppointmentActionTypesByCompanyIdV2Response> getAppointmentActionTypesByCompanyIDV2(
            @RequestHeader(CustomHttpHeaders.AUTH_TOKEN) String authToken
    )
    {
        log.info("getAppointmentActionTypesByCompanyIdV2");
        User user = this.userManager.getUserByAuthToken(authToken);
        List<AppointmentActionType> appointmentActionTypesList = this.appointmentActionTypeRepository.findAppointmentActionTypesByCompanyId(user.getCompanyId());
        List<AppointmentActionTypeDisplayable> appointmentActionTypeDisplayablesList = new ArrayList<>();
        for (AppointmentActionType appointmentActionType : appointmentActionTypesList) {
            AppointmentActionTypeDisplayable appointmentActionTypeDisplayable = appointmentActionTypeAdapter.convertToAppointmentActionTypeDisplayable(appointmentActionType);
            appointmentActionTypeDisplayablesList.add(appointmentActionTypeDisplayable);
        }
        GetAppointmentActionTypesByCompanyIdV2Response getAppointmentActionTypesByCompanyIdV2Response =
                GetAppointmentActionTypesByCompanyIdV2Response.builder()
                        .appointmentActionTypesList(appointmentActionTypeDisplayablesList)
                        .build();
        return ResponseEntity.ok(getAppointmentActionTypesByCompanyIdV2Response);
    }

}