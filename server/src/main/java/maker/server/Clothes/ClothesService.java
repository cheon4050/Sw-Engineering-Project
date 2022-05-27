package maker.server.Clothes;

import lombok.RequiredArgsConstructor;
import maker.server.Dto.Clothes.ClothesDto;
import maker.server.Entity.Clothes;
import maker.server.Entity.style;
import maker.server.Entity.weather;
import maker.server.Weather.WeatherService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ClothesService {

    private final ClothesRepository clothesRepository;
    private final WeatherService weatherService;

    public void postClothes(String userToken, ClothesDto clothes) {
        clothesRepository.save(userToken, clothes);
    }

    public ArrayList<Clothes> getClothes(String userToken, String season, String kind, boolean bookmark) {
        return clothesRepository.findByCategory(userToken, season, kind, bookmark);
    }

    public void deleteClothes(String userToken, int clothesIdx) {
        clothesRepository.delete(userToken, clothesIdx);
    }

    public void updateClothes(String userToken, int clothesIdx, ClothesDto clothes) {
        clothesRepository.update(userToken, clothesIdx, clothes);
    }

    public Clothes getClothesInfo(String userToken, int clothesIdx) {
        return clothesRepository.findByClothesIdx(userToken, clothesIdx);
    }

    public void bookmark(String userToken, int clothesIdx) {
        clothesRepository.bookmarkByClothesIdx(userToken, clothesIdx);
    }

    public ArrayList<style> recommend(String userToken) {
        weather weather = weatherService.getWeather();
        return clothesRepository.recommend(userToken, weather);
    }
}
