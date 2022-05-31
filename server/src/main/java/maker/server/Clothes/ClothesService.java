package maker.server.Clothes;

import lombok.RequiredArgsConstructor;
import maker.server.Dto.Clothes.ClothesDto;
import maker.server.Entity.Clothes;
import maker.server.Entity.clothesPostResponse;
import maker.server.Entity.style;
import maker.server.Entity.weather;
import maker.server.Weather.WeatherService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class ClothesService {

    private final ClothesRepository clothesRepository;
    private final WeatherService weatherService;

    public ResponseEntity  postClothes(String userToken, MultipartFile clothesImage, String clothes) throws IOException
    {
        String filename = clothesImage.getOriginalFilename();

        String fileSavePath = "/Users/kodongcheon/Desktop/sw-engineering-project/Sw-Engineering-Project/server/";
        File f = new File(fileSavePath + filename);
        clothesImage.transferTo(f);
        clothesPostResponse clothesResponse =  new clothesPostResponse(200, "저장이 되었습니다.");
        return new ResponseEntity(clothesResponse,HttpStatus.OK);
//        clothesRepository.save(userToken, clothes);
    }

//    public ArrayList<Clothes> getClothes(String userToken, String season, String kind, boolean bookmark) {
//        return clothesRepository.findByCategory(userToken, season, kind, bookmark);
//    }
//
//    public void deleteClothes(String userToken, int clothesIdx) {
//        clothesRepository.delete(userToken, clothesIdx);
//    }
//
//    public void updateClothes(String userToken, int clothesIdx, ClothesDto clothes) {
//        clothesRepository.update(userToken, clothesIdx, clothes);
//    }
//
//    public Clothes getClothesInfo(String userToken, int clothesIdx) {
//        return clothesRepository.findByClothesIdx(userToken, clothesIdx);
//    }
//
//    public void bookmark(String userToken, int clothesIdx) {
//        clothesRepository.bookmarkByClothesIdx(userToken, clothesIdx);
//    }
//
//    public ArrayList<style> recommend(String userToken) {
//        weather weather = weatherService.getWeather();
//        return clothesRepository.recommend(userToken, weather);
//    }
}
