package breed.tech.service.nondbmodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SimpleDate implements Serializable {
    private Integer year;
    private Integer month;
    private Integer day;
}