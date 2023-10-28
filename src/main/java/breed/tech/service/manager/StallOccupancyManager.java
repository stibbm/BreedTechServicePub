package breed.tech.service.manager;

import breed.tech.service.model.Horse;
import breed.tech.service.model.HorseLocation;
import breed.tech.service.model.Stall;
import breed.tech.service.model.User;
import breed.tech.service.repository.HorseLocationRepository;
import breed.tech.service.repository.HorseRepository;
import breed.tech.service.repository.StallRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nondbmodel.StallOccupancy;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StallOccupancyManager {

    private StallRepository stallRepository;
    private HorseRepository horseRepository;
    private HorseLocationRepository horseLocationRepository;
    private UserManager userManager;

    @Autowired
    public StallOccupancyManager(
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

    public List<StallOccupancy> getAllStallOccupancies(String authToken) {
        List<StallOccupancy> stallOccupanciesList = this.getAllStallOccupancies();
        User requestUser = this.userManager.getUserByAuthToken(authToken);
        List<StallOccupancy> filteredStallOccupancyList = new ArrayList<>();
        for (StallOccupancy stallOccupancy : stallOccupanciesList) {
            Long companyId = stallOccupancy.getCompanyId();
            if (companyId.equals(requestUser.getCompanyId())) {
                filteredStallOccupancyList.add(stallOccupancy);
            }
        }
        return filteredStallOccupancyList;
    }

    public List<StallOccupancy> getAllStallOccupancies() {
        Iterable<Stall> stallsIterable = this.stallRepository.findAll();
        List<Stall> stallsList = new ArrayList<>();
        for (Stall stall : stallsIterable) {
            stallsList.add(stall);
        }
        Iterable<Horse> horsesIterable = this.horseRepository.findAll();
        List<Horse> horsesList = new ArrayList<>();
        for (Horse horse : horsesIterable) {
            horsesList.add(horse);
        }
        Map<Long, List<Horse>> horsesByStallId = new HashMap<>();
        for (Stall stall : stallsList) {
            horsesByStallId.put(stall.getId(), new ArrayList<>());
        }
        List<StallOccupancy> stallOccupanciesList = new ArrayList<>();
        for (Horse horse : horsesList) {
            Long currentStallId = getCurrentHorseLocationByHorseId(horse.getId());
            if (currentStallId == -1) {
                continue;
            }
            horsesByStallId.get(currentStallId).add(horse);
        }
        for (Long stallId : horsesByStallId.keySet()) {
            Stall stall = this.stallRepository.findById(stallId).get();
            List<Horse> stallHorsesList = horsesByStallId.get(stallId);
            StallOccupancy stallOccupancy =
                    StallOccupancy.builder()
                            .stallId(stall.getId())
                            .name(stall.getName())
                            .notes(stall.getNotes())
                            .capacity(stall.getCapacity())
                            .description(stall.getDescription())
                            .horsesList(stallHorsesList)
                            .companyId(stall.getCompanyId())
                            .build();
            stallOccupanciesList.add(stallOccupancy);
        }
        return stallOccupanciesList;
    }

    private Long getCurrentHorseLocationByHorseId(Long horseId) {
        try {
            HorseLocation horseLocation =
                    this.horseLocationRepository.findFirstByHorseIdOrderByTimestampDesc(horseId);
            return horseLocation.getStallId();
        } catch (Exception e) {

        }
        return -1L;
    }
}