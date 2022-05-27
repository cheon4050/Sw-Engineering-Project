package maker.server.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class style {
    private String outer;
    private String top;
    private String bottom;
    private String shoes;
}
