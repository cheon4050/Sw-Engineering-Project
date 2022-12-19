package maker.server.Weather;

import lombok.RequiredArgsConstructor;
import maker.server.Response.GetWeatherResponse;
import maker.server.Entity.Weather;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherService {
    private final WeatherRepository weatherRepository;


    public ResponseEntity getWeather() throws Exception {
        Weather weather = weatherRepository.getWeather();
        GetWeatherResponse getWeatherResponse = new GetWeatherResponse(weather, 200, "날씨 조회 성공");
        return new ResponseEntity(getWeatherResponse, HttpStatus.OK);
    }

}
