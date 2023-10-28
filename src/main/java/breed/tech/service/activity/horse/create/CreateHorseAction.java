package breed.tech.service.activity.horse.create;

import static breed.tech.service.constants.Constants.ALL_ORIGINS;
import static breed.tech.service.constants.Constants.GSON;

import breed.tech.service.activity.horse.create.message.CreateHorseRequest;
import breed.tech.service.activity.horse.create.message.CreateHorseResponse;
import breed.tech.service.constants.CustomHttpHeaders;
import breed.tech.service.constants.Routes;
import breed.tech.service.model.Horse;
import breed.tech.service.repository.HorseRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class CreateHorseAction {

    private HorseRepository horseRepository;

    /**
     * Constructor
     * @param horseRepository horse repository
     */
    @Autowired
    public CreateHorseAction(HorseRepository horseRepository) {
        this.horseRepository = Validate.notNull(horseRepository);
    }

    /**
     * Create Horse
     * @param createHorseRequest CreateHorseRequest
     * @param authToken auth token
     * @return CreateHorseResponse
     */
    @PostMapping(value = Routes.CREATE_HORSE)
    @CrossOrigin(ALL_ORIGINS)
    public @ResponseBody ResponseEntity<CreateHorseResponse> act(
            @RequestBody CreateHorseRequest createHorseRequest,
            @RequestHeader(CustomHttpHeaders.AUTH_TOKEN) String authToken
    ) {
        log.info("CreateHorseAction");
        log.info("CreateHorseRequest: \n" + GSON.toJson(createHorseRequest));
        log.info("AuthToken: " + authToken);
        Long ownerUserIdLong = Long.parseLong(createHorseRequest.getOwnerUserId());
        Horse horse =
                this.horseRepository.save(
                        Horse.builder()
                                .name(createHorseRequest.getName())
                                .registrationNumber(createHorseRequest.getRegistrationNumber())
                                .isStallion(createHorseRequest.getIsStallion())
                                .dam(createHorseRequest.getDam())
                                .sire(createHorseRequest.getSire())
                                .damSire(createHorseRequest.getDamSire())
                                .ownerUserId(ownerUserIdLong)
                                .build());
        log.info("Horse: " + GSON.toJson(horse));
        CreateHorseResponse createHorseResponse =
                CreateHorseResponse.builder().horse(horse).build();
        return ResponseEntity.ok(createHorseResponse);
    }
}