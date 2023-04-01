package maker.server.clothes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maker.server.auth.AuthJpaRepository;
import maker.server.clothes.Repository.v2.ClothesJpaRepository;
import maker.server.dto.clothes.ClothesPostDto;
import maker.server.dto.clothes.ClothesPutDto;
import maker.server.entity.*;
import maker.server.weather.WeatherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClothesService {

    private final AuthJpaRepository authJpaRepository;
    private final ClothesJpaRepository clothesJpaRepository;
    private final WeatherRepository weatherRepository;

    public void postClothes(Integer userIdx, ClothesPostDto clothes) {
        Optional<Users> optionalUsers = authJpaRepository.findById(userIdx);
        Users user = optionalUsers.get();
        Clothes saveClothes = clothes.toEntity(user);
        clothesJpaRepository.save(saveClothes);
    }

    public List<Clothes> getClothes(Integer userIdx, ArrayList<String> season, ArrayList<String> kind, boolean bookmark) {
        Users users = authJpaRepository.findById(userIdx).get();
        List<Clothes> clothesArrayList = clothesJpaRepository.findAllByUsersIdAndOptions(users, season, kind, bookmark);
        return clothesArrayList;
    }

    public void deleteClothes(Integer userIdx, int clothesIdx) {
        Clothes clothes = clothesJpaRepository.findById(clothesIdx).get();
        clothesJpaRepository.delete(clothes);
    }

    @Transactional
    public void updateClothes(Integer userIdx, ClothesPutDto clothesInfo, int clothesIdx){
        Users users = authJpaRepository.findById(userIdx).get();
        Clothes clothes = clothesJpaRepository.findById(clothesIdx).get();
        clothes.updateClothes(
                clothesInfo.getClothesImageUrl(),
                clothesInfo.getSeason(),
                clothesInfo.getKind(),
                clothesInfo.getWashingMethod(),
                clothesInfo.getSize(),
                clothesInfo.isBookmark());
    }

    public Clothes getClothesInfo(Integer userIdx, int clothesIdx) {
        Clothes clothes = clothesJpaRepository.findById(clothesIdx).get();
        return clothes;
    }

    @Transactional
    public void bookmark(Integer userIdx, int clothesIdx, boolean bookmark) {
        Clothes clothes = clothesJpaRepository.findById(clothesIdx).get();
        clothes.updateBookmark(bookmark);
    }

    public List<String> recommend(Integer userIdx) throws Exception {
        Weather weather = weatherRepository.getWeather();
        String season = getSeason(weather.getPresent());
        List<String> urlList = clothesJpaRepository.findBySeason(season);
        return urlList;
    }

    private String getSeason(double present){
        String season;
        if (present <8.0)
            season = "winter";
        else if(present < 16.0)
            season = "autumn";
        else if(present < 22)
            season = "spring";
        else
            season = "summer";
        return season;
    }
}
