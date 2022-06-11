package maker.server.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Weather {
    private double Probability;
    private double humidity;
    private String state;
    private double present;

}
