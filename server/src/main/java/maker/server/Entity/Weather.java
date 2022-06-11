package maker.server.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class weather {
    private int precipitationProbability;
    private int humidity;
    private String state;
    private Float presentTemperature;
    private Float highestTemperature;
    private Float lowestTemperature;
}
