package breed.tech.service.nondbmodel;

import breed.tech.service.model.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HorseV2 implements Serializable {
    private Long horseId;
    private String horseName;
    private String horseRegistrationNumber;
    private String isStallion;
    private String damName;
    private String sireName;
    private String damSireName;
    private String ownerFirstAndLastName;
    private String stallName;
    private Long stallId;
    private Long userId;
    private User user;
}