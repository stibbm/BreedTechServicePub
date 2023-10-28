package breed.tech.service.activity.horse.create.message;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateHorseRequest implements Serializable {
    private String name;
    private String registrationNumber;
    private Boolean isStallion;
    private String dam;
    private String sire;
    private String damSire;
    private String ownerUserId;
}