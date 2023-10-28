package breed.tech.service.manager;

import breed.tech.service.activity.appointmentaction.create.message.CreateAppointmentActionRequest;
import breed.tech.service.activity.appointmentaction.create.message.CreateAppointmentActionResponse;
import breed.tech.service.activity.appointmentaction.get.message.GetAppointmentActionsByUserIdV2Request;
import breed.tech.service.activity.appointmentaction.get.message.GetAppointmentActionsByUserIdV2Response;
import breed.tech.service.activity.appointmentaction.get.message.GetAppointmentActionsResponse;
import breed.tech.service.adapter.AppointmentActionAdapter;
import breed.tech.service.constants.Constants;
import breed.tech.service.manager.params.SendSMSArgs;
import breed.tech.service.model.Appointment;
import breed.tech.service.model.AppointmentAction;
import breed.tech.service.model.User;
import breed.tech.service.repository.AppointmentActionRepository;
import breed.tech.service.repository.AppointmentActionTypeRepository;
import breed.tech.service.repository.AppointmentRepository;
import lombok.extern.slf4j.Slf4j;
import breed.tech.service.nondbmodel.AppointmentActionV3;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static breed.tech.service.constants.Constants.GSON;

@Service
@Slf4j
public class AppointmentActionManager {

    private AppointmentActionRepository appointmentActionRepository;
    private AppointmentRepository appointmentRepository;
    private UserManager userManager;
    private AppointmentManager appointmentManager;
    private TwilioManager twilioManager;
    private AppointmentActionTypeRepository appointmentActionTypeRepository;
    private AppointmentActionAdapter appointmentActionAdapter;

    @Autowired
    public AppointmentActionManager(
            AppointmentActionRepository appointmentActionRepository,
            AppointmentRepository appointmentRepository,
            UserManager userManager,
            AppointmentManager appointmentManager,
            TwilioManager twilioManager,
            AppointmentActionTypeRepository appointmentActionTypeRepository,
            AppointmentActionAdapter appointmentActionAdapter
    ) {
        this.appointmentActionRepository = Validate.notNull(appointmentActionRepository);
        this.appointmentRepository = Validate.notNull(appointmentRepository);
        this.userManager = Validate.notNull(userManager);
        this.appointmentManager = Validate.notNull(appointmentManager);
        this.twilioManager = Validate.notNull(twilioManager);
        this.appointmentActionTypeRepository = Validate.notNull(appointmentActionTypeRepository);
        this.appointmentActionAdapter = Validate.notNull(appointmentActionAdapter);
    }

    public AppointmentAction createAppointmentActionV2(
            Long appointmentId,
            Long amountCents,
            String description,
            Long timestamp,
            String authToken,
            String smsMessage
    ) {
        log.info("createAppointmentAction");
        User user = userManager.getUserByAuthToken(authToken);
        AppointmentAction unsavedAppointmentAction = AppointmentAction.builder()
                .appointmentId(appointmentId)
                .createdByUserId(user.getId())
                .timestamp(timestamp)
                .amountCents(amountCents)
                .description(description)
                .smsMessage(smsMessage)
                .build();
        AppointmentAction savedAppointmentAction = appointmentActionRepository.save(unsavedAppointmentAction);
        log.info("appointmentAction: " + savedAppointmentAction);
        return savedAppointmentAction;
    }

    public CreateAppointmentActionResponse createAppointmentAction(
            CreateAppointmentActionRequest createAppointmentActionRequest,
            String authToken) {
        log.info("CreateAppointmentAction");
        User createdByUser = userManager.getUserByAuthToken(authToken);
        log.info(GSON.toJson(createdByUser));
        Long timestamp = Instant.now().getEpochSecond();
        AppointmentAction unsavedAppointmentAction = AppointmentAction.builder()
                .appointmentId(createAppointmentActionRequest.getAppointmentId())
                .createdByUserId(createdByUser.getId())
                .smsMessage(createAppointmentActionRequest.getSmsMessage())
                .description(createAppointmentActionRequest.getDescription())
                .timestamp(timestamp)
                .build();
        log.info(GSON.toJson(unsavedAppointmentAction));
        AppointmentAction savedAppointmentAction = this.appointmentActionRepository.save(unsavedAppointmentAction);
        CreateAppointmentActionResponse createAppointmentActionResponse =
                CreateAppointmentActionResponse.builder()
                        .appointmentAction(savedAppointmentAction)
                        .build();
        Appointment appointment = this.appointmentManager.getAppointmentById(savedAppointmentAction.getAppointmentId());
        SendSMSArgs sendSMSArgs = SendSMSArgs.builder()
                .sourcePhoneNumberString(Constants.SOURCE_TWILIO_PHONE_NUMBER)
                .destinationPhoneNumberString(appointment.getNotificationsPhoneNumber())
                .messageString(savedAppointmentAction.getSmsMessage())
                .build();
        this.twilioManager.sendSMS(sendSMSArgs);
        log.info("createAppointmentActionResponse: " + GSON.toJson(createAppointmentActionResponse));
        return createAppointmentActionResponse;
    }

    public GetAppointmentActionsResponse getAppointmentActions(String authToken) {
        log.info("getAppointmentActions");
        Iterable<AppointmentAction> appointmentActionsIterable = this.appointmentActionRepository.findAll();
        List<AppointmentAction> appointmentActionsList = new ArrayList<>();
        for (AppointmentAction appointmentAction : appointmentActionsIterable) {
            appointmentActionsList.add(appointmentAction);
        }
        GetAppointmentActionsResponse getAppointmentActionsResponse = GetAppointmentActionsResponse.builder()
                .appointmentActionsList(appointmentActionsList)
                .build();
        log.info("getAppointmentActionsResponse: " + GSON.toJson(getAppointmentActionsResponse));
        return getAppointmentActionsResponse;
    }

    public List<AppointmentAction> getAppointmentActionsByCompanyId(Long companyId) {
        Iterable<AppointmentAction> appointmentActionsIterable = this.appointmentActionRepository
                .findAll();
        List<AppointmentAction> appointmentActionsList = new ArrayList<>();
        for (AppointmentAction appointmentAction : appointmentActionsIterable) {
            Long appointmentID = appointmentAction.getAppointmentId();
            Appointment appointment = appointmentRepository.findById(appointmentID).get();
            Long userId = appointment.getUserId();
            User user = userManager.getUserById(userId);
            Long userCompanyId = user.getCompanyId();
            if (userCompanyId == companyId) {
                appointmentActionsList.add(appointmentAction);
            }
        }
        return appointmentActionsList;
    }

    public List<AppointmentAction> getAppointmentActionsByUserId(Long userId) {
        log.info("getAppointmentActionsByUserId");
        List<Appointment> appointmentsList = this.appointmentManager.getAppointmentsByUserId(userId);
        log.info("appointmentsList: " + GSON.toJson(appointmentsList));
        List<AppointmentAction> appointmentActionsList = new ArrayList<>();
        for (Appointment appointment : appointmentsList) {
            log.info("appointment: " + GSON.toJson(appointment));
            List<AppointmentAction> appointmentsAppointmentActionsList = this.appointmentActionRepository
                    .findAllByAppointmentId(appointment.getId());
            log.info("appointmentsAppointmentActionsList: " + GSON.toJson(appointmentsAppointmentActionsList));
            appointmentActionsList.addAll(appointmentsAppointmentActionsList);
        }
        log.info("appointmentActionsList: " + GSON.toJson(appointmentActionsList));
        return appointmentActionsList;
    }

    public List<AppointmentAction> getAppointmentActionsByAppointmentId(Long appointmentId) {
        List<AppointmentAction> appointmentActionsList =
                this.appointmentActionRepository.findAllByAppointmentId(appointmentId);
        return appointmentActionsList;
    }

    public List<AppointmentAction> getAppointmentActionsByHorseId(Long horseId) {
        List<Appointment> appointmentsList = appointmentRepository.findByHorseId(horseId);
        log.info("appointmentsList: " + GSON.toJson(appointmentsList));
        List<AppointmentAction> allAppointmentActionsList = new ArrayList<>();
        for (Appointment appointment: appointmentsList) {
            List<AppointmentAction> appointmentActionsList = this.appointmentActionRepository
                    .findAllByAppointmentId(appointment.getId());
            log.info("appointmentActionsList: " + GSON.toJson(appointmentActionsList));
            allAppointmentActionsList.addAll(appointmentActionsList);
        }
        log.info("allAppointmentActionsList: " + GSON.toJson(allAppointmentActionsList));
        return allAppointmentActionsList;
    }

    public GetAppointmentActionsByUserIdV2Response getAppointmentActionsByUserId(GetAppointmentActionsByUserIdV2Request getAppointmentActionsByUserIdV2Request) {
        Iterable<AppointmentAction> appointmentActionsIterable = this.appointmentActionRepository
                .findAll();
        List<AppointmentActionV3> appointmentActionsList = new ArrayList<>();
        for (AppointmentAction appointmentAction : appointmentActionsIterable) {
            Long appointmentId = appointmentAction.getAppointmentId();
            Appointment appointment = this.appointmentRepository.findById(appointmentId).get();
            Long userId = appointment.getUserId();
            log.info("userId: " + userId);
            log.info("requestUserId: " + getAppointmentActionsByUserIdV2Request.getUserId());
            Long requestUserIdLong = Long.parseLong(getAppointmentActionsByUserIdV2Request.getUserId());
            if(userId == requestUserIdLong) {
                AppointmentActionV3 appointmentActionV3 = this.appointmentActionAdapter
                        .convertToAppointmentActionV3(appointmentAction);
                appointmentActionsList.add(appointmentActionV3);
            }
        }
        GetAppointmentActionsByUserIdV2Response getAppointmentActionsByUserIdV2Response =
                GetAppointmentActionsByUserIdV2Response.builder()
                        .appointmentActionsList(appointmentActionsList)
                        .build();
        return getAppointmentActionsByUserIdV2Response;
    }

}