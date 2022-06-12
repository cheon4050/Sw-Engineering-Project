package maker.server.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import maker.server.Entity.Weather;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetWeatherResponse extends Response{
    private Weather weather;
    public GetWeatherResponse(Weather weather, int status, String message){
        super(status,message);
        this.weather= weather;
    }
}
