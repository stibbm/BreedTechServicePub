package breed.tech.service.nondbmodel;

import breed.tech.service.model.Horse;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StallOccupancy implements Serializable {
    private Long stallId;
    private String name;
    private List<Horse> horsesList;
    private Integer capacity;
    private String notes;
    private String description;
    private Long companyId;
}