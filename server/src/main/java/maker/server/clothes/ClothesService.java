package maker.server.clothes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maker.server.auth.AuthJpaRepository;
import maker.server.clothes.Repository.v2.ClothesJpaRepository;
import maker.server.response.GetClothesInfoResponse;
import maker.server.response.GetClothesResponse;
import maker.server.response.GetRecommendResponse;
import maker.server.response.Response;
import maker.server.util.JwtUtil;
import maker.server.dto.clothes.ClothesPostDto;
import maker.server.dto.clothes.ClothesPutDto;
import maker.server.entity.*;
import maker.server.weather.WeatherRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClothesService {

//    private final ClothesRepository clothesRepository;
    private final AuthJpaRepository authJpaRepository;
    private final ClothesJpaRepository clothesJpaRepository;
    private final WeatherRepository weatherRepository;
    private final JwtUtil jwtUtil;

    public ResponseEntity postClothes(String userToken, ClothesPostDto clothes)
    {
        Integer userIdx = jwtUtil.parseJwt(userToken);
        Optional<Users> optionalUsers = authJpaRepository.findById(userIdx);
        Users user = optionalUsers.get();
        //clothesRepository.save(userIdx, clothes);
        Clothes saveClothes = clothes.toEntity(user);
        clothesJpaRepository.save(saveClothes);
        Response Response =  new Response(200, "등록 성공");
        return new ResponseEntity(Response,HttpStatus.OK);
    }

    public ResponseEntity getClothes(String userToken, ArrayList<String> season, ArrayList<String> kind, boolean bookmark) {
        Integer userIdx = jwtUtil.parseJwt(userToken);
        Users users = authJpaRepository.findById(userIdx).get();
//        ArrayList<Clothes> clothesArrayList = clothesRepository.findByCategory(userIdx, season, kind, bookmark);
        List<Clothes> clothesArrayList = clothesJpaRepository.findAllByUsersIdAndOptions(users, season, kind, bookmark);
        GetClothesResponse GetClothesResponse = new GetClothesResponse(clothesArrayList, 200, "조회 성공");
        return new ResponseEntity(GetClothesResponse, HttpStatus.OK);
    }

    public ResponseEntity deleteClothes(String userToken, int clothesIdx) {
        Integer userIdx = jwtUtil.parseJwt(userToken);
//        clothesRepository.delete(clothesIdx);
        Clothes clothes = clothesJpaRepository.findById(clothesIdx).get();
        clothesJpaRepository.delete(clothes);
        Response Response = new Response(200, "삭제 성공");
        return new ResponseEntity(Response, HttpStatus.OK);

    }

    @Transactional
    public ResponseEntity updateClothes(String userToken, ClothesPutDto clothesInfo, int clothesIdx){
        Integer userIdx = jwtUtil.parseJwt(userToken);
//        clothesRepository.update(clothesIdx, clothes);
        Users users = authJpaRepository.findById(userIdx).get();
        Clothes clothes = clothesJpaRepository.findById(clothesIdx).get();
        clothes.updateClothes(
                clothesInfo.getClothesImageUrl(),
                clothesInfo.getSeason(),
                clothesInfo.getKind(),
                clothesInfo.getWashingMethod(),
                clothesInfo.getSize(),
                clothesInfo.isBookmark());

        Response Response = new Response(200, "수정 성공");
        return new ResponseEntity(Response, HttpStatus.OK);
    }

    public ResponseEntity getClothesInfo(String userToken, int clothesIdx) {
        Integer userIdx = jwtUtil.parseJwt(userToken);
        //        Clothes clothes = clothesRepository.findByClothesIdx(clothesIdx);
        Clothes clothes = clothesJpaRepository.findById(clothesIdx).get();
        GetClothesInfoResponse GetClothesInfoResponse = new GetClothesInfoResponse(clothes, 200, "상세 조회 성공");
        return new ResponseEntity(GetClothesInfoResponse, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity bookmark(String userToken, int clothesIdx, boolean bookmark) {
        Integer userIdx = jwtUtil.parseJwt(userToken);
//        clothesRepository.bookmarkByClothesIdx(clothesIdx, bookmark);
        Clothes clothes = clothesJpaRepository.findById(clothesIdx).get();
        clothes.updateBookmark(bookmark);
        Response Response = new Response(200, "즐겨찾기 성공");
        return new ResponseEntity(Response, HttpStatus.OK);
    }

    public ResponseEntity recommend(String userToken) throws Exception {
        Integer userIdx = jwtUtil.parseJwt(userToken);
        Weather weather = weatherRepository.getWeather();
        String season = getSeason(weather.getPresent());
//        List<String> urlList =  clothesRepository.recommend(userIdx, season);
        List<String> urlList = clothesJpaRepository.findBySeason(season);
        GetRecommendResponse getRecommendResponse = new GetRecommendResponse(urlList, 200, "의류 추천 성공");
        return new ResponseEntity(getRecommendResponse,HttpStatus.OK);
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
