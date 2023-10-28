package breed.tech.service.manager;

import breed.tech.service.activity.user.get.message.GetUserByIdRequest;
import breed.tech.service.model.*;
import breed.tech.service.repository.HorseRepository;
import lombok.extern.slf4j.Slf4j;
import nondbmodel.HorseV2;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static breed.tech.service.constants.Constants.GSON;
import static breed.tech.service.model.enums.AppointmentStatus.CHECKED_IN;

@Service
@Slf4j
public class HorseManager {

    private HorseLocationManager horseLocationManager;
    private HorseRepository horseRepository;
    private UserManager userManager;
    private StallManager stallManager;
    private AppointmentManager appointmentManager;

    @Autowired
    public HorseManager(
            HorseLocationManager horseLocationManager,
            HorseRepository horseRepository,
            UserManager userManager,
            StallManager stallManager,
            AppointmentManager appointmentManager
    ) {
        this.horseLocationManager = Validate.notNull(horseLocationManager);
        this.horseRepository = Validate.notNull(horseRepository);
        this.userManager = Validate.notNull(userManager);
        this.stallManager = Validate.notNull(stallManager);
        this.appointmentManager = Validate.notNull(appointmentManager);
    }

    public List<Horse> getHorsesInStall(Long stallId) {
        Map<Long, List<HorseLocation>> horseLocationsByStallIdMap = this.horseLocationManager
                .getCurrentHorseLocationsByStallId();
        List<HorseLocation> currentHorseLocationsList = horseLocationsByStallIdMap.get(stallId);
        List<Horse> horsesList = new ArrayList<>();
        for (HorseLocation horseLocation : currentHorseLocationsList) {
            Horse horse = this.horseRepository.findById(horseLocation.getHorseId()).get();
            horsesList.add(horse);
        }
        return horsesList;
    }

    public List<HorseV2> getHorsesWithActiveAppointment(String authToken) {
        List<HorseV2> horsesV2List = this.getHorsesV2(authToken);
        Map<Long, Appointment> activeAppointmentByHorseIdMap = this.appointmentManager
                .getActiveAppointmentByHorseIdMap();
        log.info("activeAppointmentByHorseIdMap:");
        log.info(activeAppointmentByHorseIdMap.toString());
        List<HorseV2> horsesWithActiveAppointmentList = new ArrayList<>();
        for (HorseV2 horseV2 : horsesV2List) {
            try {
                Appointment activeAppointment = activeAppointmentByHorseIdMap.get(horseV2.getHorseId());
                if (activeAppointment.getAppointmentStatus().equalsIgnoreCase(CHECKED_IN)) {
                    horsesWithActiveAppointmentList.add(horseV2);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return horsesWithActiveAppointmentList;

    }

    public List<HorseV2> getHorsesWithActiveAppointmentUserAuth(String userAuthToken) {
        User requestUser = this.userManager.getUserByAuthToken(userAuthToken);
        List<HorseV2> horseV2sList = this.getHorsesWithActiveAppointment(userAuthToken);
        List<HorseV2> filteredHorseV2sList = new ArrayList<>();
        Map<Long, Horse> horsesMap = this.getHorsesMap();
        for (HorseV2 horseV2 : horseV2sList) {
            Horse horse = horsesMap.get(horseV2.getHorseId());
            if (requestUser.getId().equals(horse.getOwnerUserId())) {
                filteredHorseV2sList.add(horseV2);
            }
        }
        return filteredHorseV2sList;
    }

    public Horse getHorseById(Long horseId) {
        Horse horse = this.horseRepository.findById(horseId).get();
        return horse;
    }

    public List<Horse> getHorsesByCompanyId(Long companyId) {
        log.info("getHorsesByCompanyId");
        Iterable<Horse> horsesIterable = this.horseRepository.findAll();
        List<Horse> companyHorsesList = new ArrayList<>();
        for (Horse horse : horsesIterable) {
            Long ownerUserId = horse.getOwnerUserId();
            GetUserByIdRequest getUserByIdRequest = GetUserByIdRequest.builder().id(ownerUserId).build();
            log.info("GettingUserById: " + GSON.toJson(getUserByIdRequest));
            User user = this.userManager.getUserById(getUserByIdRequest).getUser();
            if (user.getCompanyId().equals(companyId)) {
                companyHorsesList.add(horse);
            }
        }
        return companyHorsesList;
    }

    public List<Horse> getHorsesByName(String name) {
        List<Horse> horsesList = new ArrayList<>();
        for (Horse horse : this.horseRepository.findHorsesByName(name)) {
            horsesList.add(horse);
        }
        return horsesList;
    }

    public List<HorseV2> getHorsesV2(String authToken) {
        User requestUser = this.userManager.getUserByAuthToken(authToken);
        Long requestCompanyId = requestUser.getCompanyId();
        Iterable<Horse> horsesIterable = this.horseRepository.findAll();
        List<User> usersList = this.userManager.getUsers();
        List<HorseV2> horsesV2List = new ArrayList<>();
        Map<Long, User> usersMap = new HashMap<>();
        List<Stall> stallsList = stallManager.getAllStalls();
        Map<Long, Stall> stallsMap = new HashMap<>();
        for (User user : usersList) {
            usersMap.put(user.getId(), user);
        }
        for (Stall stall : stallsList) {
            stallsMap.put(stall.getId(), stall);
        }
        for (Horse horse : horsesIterable) {
            HorseLocation currentHorseLocation = horseLocationManager.getCurrentHorseLocation(horse.getId());
            String stallName = "N/A";
            Long stallId = -1L;
            try {
                Stall stall = stallsMap.get(currentHorseLocation.getStallId());
                stallName = stall.getName();
                stallId = stall.getId();
            } catch (Exception e) {
                e.printStackTrace();
            }
            String isStallion = "Stallion";
            if (horse.getIsStallion() == false) {
                isStallion = "Mare";
            }
            log.info("ownerUserId: " + horse.getOwnerUserId());
            //log.info(GSON.toJson(horse));
            User owner = usersMap.get(horse.getOwnerUserId());
            HorseV2 horseV2 = HorseV2.builder()
                    .horseId(horse.getId())
                    .horseName(horse.getName())
                    .horseRegistrationNumber(horse.getRegistrationNumber())
                    .damSireName(horse.getDamSire())
                    .sireName(horse.getSire())
                    .damName(horse.getDam())
                    .isStallion(isStallion)
                    .ownerFirstAndLastName(owner.getFirstName() + " " + owner.getLastName())
                    .stallName(stallName)
                    .stallId(stallId)
                    .userId(owner.getId())
                    .user(owner)
                    .build();
            if (requestUser.getCompanyId().equals(owner.getCompanyId())) {
                horsesV2List.add(horseV2);
            }
        }
        return horsesV2List;
    }

    /**
     * Get Horses V2
     * @param userAuthToken user auth token
     * @return List of HorseV2s
     */
    public List<HorseV2> getHorsesV2UserAuth(String userAuthToken) {
        User requestUser = this.userManager.getUserByAuthToken(userAuthToken);
        List<HorseV2> horseV2sList = this.getHorsesV2(userAuthToken);
        List<HorseV2> filteredHorseV2sList = new ArrayList<>();
        Map<Long, Horse> horsesMap = this.getHorsesMap();
        for (HorseV2 horseV2 : horseV2sList) {
            Horse horse = horsesMap.get(horseV2.getHorseId());
            if (requestUser.getId().equals(horse.getOwnerUserId())) {
                filteredHorseV2sList.add(horseV2);
            }
        }
        return filteredHorseV2sList;
    }

    /**
     * Get Horses Map
     * @return Map < Long, Horse >
     */
    public Map<Long, Horse> getHorsesMap() {
        Map<Long, Horse> horsesMap = new HashMap<>();
        for (Horse horse : this.horseRepository.findAll()) {
            horsesMap.put(horse.getId(), horse);
        }
        return horsesMap;
    }

}