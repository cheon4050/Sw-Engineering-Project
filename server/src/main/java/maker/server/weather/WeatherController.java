package maker.server.weather;

import lombok.RequiredArgsConstructor;
import maker.server.entity.Weather;
import maker.server.response.GetWeatherResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
@RequiredArgsConstructor
public class WeatherController {
    private final WeatherService weatherService;

    @GetMapping
    public ResponseEntity getWeather() throws Exception{
        Weather weather = weatherService.getWeather();
        GetWeatherResponse getWeatherResponse = new GetWeatherResponse(weather, 200, "날씨 조회 성공");
        return new ResponseEntity(getWeatherResponse, HttpStatus.OK);
    }
}
