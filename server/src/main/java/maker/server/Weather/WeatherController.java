package maker.server.Weather;

import lombok.RequiredArgsConstructor;
import maker.server.Entity.weather;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
@RequiredArgsConstructor
public class WeatherController {
    private final WeatherService weatherService;

    @GetMapping
    public weather getWeather(){
        return weatherService.getWeather();
    }
}
