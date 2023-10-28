package breed.tech.service.manager;

import breed.tech.service.model.Horse;
import breed.tech.service.model.HorseLocation;
import breed.tech.service.model.Stall;
import breed.tech.service.model.User;
import breed.tech.service.repository.HorseLocationRepository;
import breed.tech.service.repository.HorseRepository;
import breed.tech.service.repository.StallRepository;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import nondbmodel.StallOccupancy;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StallManager {

    private StallRepository stallRepository;
    private HorseRepository horseRepository;
    private HorseLocationRepository horseLocationRepository;
    private UserManager userManager;

    public StallManager(
            StallRepository stallRepository,
            HorseRepository horseRepository,
            HorseLocationRepository horseLocationRepository,
            UserManager userManager
    ) {
        this.stallRepository = Validate.notNull(stallRepository);
        this.horseRepository = Validate.notNull(horseRepository);
        this.horseLocationRepository = Validate.notNull(horseLocationRepository);
        this.userManager = Validate.notNull(userManager);
    }

    public Stall getStall(Long stallId) {
        Stall stall = this.stallRepository.findById(stallId).get();
        return stall;
    }

    public List<Stall> getAllEmptyStalls() {
        List<Stall> stallsList = getAllStalls();
        List<Stall> emptyStallsList = new ArrayList<>();
        for (Stall stall : stallsList) {
            List<Long> horseIdsInStallList = getHorsesInStall(stall.getId());
            if (horseIdsInStallList.isEmpty()) {
                emptyStallsList.add(stall);
            }
        }
        return emptyStallsList;
    }

    public List<StallOccupancy> getEmptyStallsOccupancy() {
        List<Stall> stallsList = getAllStalls();
        List<StallOccupancy> emptyStallOccupanciesList = new ArrayList<>();
        for (Stall stall : stallsList) {
            return null;
        }
        return null;
    }

    public Stall createStall(
            String stallName,
            Integer capacity,
            String description,
            String notes,
            Long companyId
    ) {
        Stall unsavedStall = Stall.builder()
                .name(stallName)
                .capacity(capacity)
                .description(description)
                .notes(notes)
                .companyId(companyId)
                .build();
        Stall savedStall = this.stallRepository.save(unsavedStall);
        return savedStall;
    }

    public List<Stall> getAllStalls() {
        List<Stall> stallsList = new ArrayList<>();
        for (Stall stall : this.stallRepository.findAll()) {
            stallsList.add(stall);
        }
        return stallsList;
    }

    public List<Stall> getAllStalls(String authToken) {
        User requestUser = userManager.getUserByAuthToken(authToken);
        List<Stall> stallsList = getAllStalls();
        List<Stall> filteredStallsList = new ArrayList<>();
        for (Stall stall: stallsList) {
            if(stall.getCompanyId().equals(requestUser.getCompanyId())) {
                filteredStallsList.add(stall);
            }
        }
        return filteredStallsList;
    }

    public List<Long> getHorsesInStall(Long stallId) {
        List<Long> horseIdsInStallList = new ArrayList<>();
        List<Long> horseIdsList = getAllHorseIds();
        for (Long horseId : horseIdsList) {
            Long currentStall = getCurrentStall(horseId);
            if (currentStall == stallId) {
                horseIdsInStallList.add(horseId);
            }
        }
        return horseIdsInStallList;
    }

    private List<Long> getAllHorseIds() {
        Iterable<Horse> horsesIterator = this.horseRepository.findAll();
        List<Long> horseIdsList = new ArrayList<>();
        for (Horse horse : horsesIterator) {
            horseIdsList.add(horse.getId());
        }
        return horseIdsList;
    }

    private Long getCurrentStall(Long horseId) {
        try {
            HorseLocation horseLocation =
                    this.horseLocationRepository.findFirstByHorseIdOrderByTimestampDesc(horseId);
            return horseLocation.getStallId();
        } catch (Exception e) {
            return -1L;
        }
    }
}