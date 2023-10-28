package breed.tech.service.activity.horse.create.message;

import breed.tech.service.model.Horse;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateHorseResponse implements Serializable {
    private Horse horse;
}