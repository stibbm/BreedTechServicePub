package breed.tech.service.manager;

import breed.tech.service.model.HorseLocation;
import breed.tech.service.model.Stall;
import breed.tech.service.repository.HorseLocationRepository;
import breed.tech.service.repository.HorseRepository;
import breed.tech.service.repository.StallRepository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HorseLocationManager {

    private HorseLocationRepository horseLocationRepository;

    private HorseRepository horseRepository;
    private StallRepository stallRepository;

    @Autowired
    public HorseLocationManager(
            HorseLocationRepository horseLocationRepository,
            HorseRepository horseRepository,
            StallRepository stallRepository) {
        this.horseLocationRepository = Validate.notNull(horseLocationRepository);
        this.horseRepository = Validate.notNull(horseRepository);
        this.stallRepository = Validate.notNull(stallRepository);
    }

    public List<HorseLocation> getAllCurrentHorseLocations() {
        List<Long> distinctHorseIdsList = horseRepository.findDistinctHorseIds();
        List<HorseLocation> horseLocationsList = new ArrayList<>();
        for (Long horseId : distinctHorseIdsList) {
            HorseLocation horseLocation =
                    this.horseLocationRepository.findFirstByHorseIdOrderByTimestampDesc(horseId);
            if (horseLocation != null) {
                horseLocationsList.add(horseLocation);
            }
        }
        return horseLocationsList;
    }

    public List<HorseLocation> getAllHorseLocations() {
        List<HorseLocation> horseLocationsList = new ArrayList<>();
        for (HorseLocation horseLocation : this.horseLocationRepository.findAll()) {
            horseLocationsList.add(horseLocation);
        }
        return horseLocationsList;
    }

    public Map<Long, List<HorseLocation>> getCurrentHorseLocationsByStallId() {
        Iterable<Stall> stallsIterable = this.stallRepository.findAll();
        System.out.println("STALLS ITERABLE: " + stallsIterable);
        List<HorseLocation> currentHorseLocationsList = this.getAllCurrentHorseLocations();
        System.out.println("CURRENT HORSE LOCATIONS LIST = " + currentHorseLocationsList);
        Map<Long, List<HorseLocation>> currentHorseLocationsByStall =
                currentHorseLocationsList.stream()
                        .collect(Collectors.groupingBy(HorseLocation::getStallId));
        return currentHorseLocationsByStall;
    }

    public HorseLocation getCurrentHorseLocation(Long horseId) {
        HorseLocation horseLocation = this.horseLocationRepository.findFirstByHorseIdOrderByTimestampDesc(horseId);
        return horseLocation;
    }

    public HorseLocation createHorseLocation(Long horseId, Long stallId) {
        HorseLocation horseLocation = HorseLocation.builder()
                .horseId(horseId)
                .stallId(stallId)
                .timestamp(Instant.now().getEpochSecond())
                .build();
        HorseLocation savedHorseLocation = this.horseLocationRepository.save(horseLocation);
        return savedHorseLocation;
    }
}