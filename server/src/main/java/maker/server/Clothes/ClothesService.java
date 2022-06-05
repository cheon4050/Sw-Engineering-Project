package maker.server.Clothes;

import lombok.RequiredArgsConstructor;
import maker.server.Dto.Clothes.ClothesDto;
import maker.server.Dto.Clothes.ClothesPostDto;
import maker.server.Dto.Clothes.ClothesPutDto;
import maker.server.Entity.Clothes;
import maker.server.Entity.clothesGetResponse;
import maker.server.Entity.clothesInfoGetResponse;
import maker.server.Entity.clothesResponse;
import maker.server.Weather.WeatherService;
import org.json.HTTP;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ClothesService {

    private final ClothesRepository clothesRepository;
    private final WeatherService weatherService;

    public ResponseEntity  postClothes(String userToken, MultipartFile clothesImage, String clothes) throws IOException
    {
        LocalTime now = LocalTime.now();
        String fileSavePath = "/Users/kodongcheon/Desktop/sw-engineering-project/Sw-Engineering-Project/server/"+now+".png";
        File f = new File(fileSavePath);
        clothesImage.transferTo(f);
        JSONObject jObject = new JSONObject(clothes);
        String date = jObject.getString("date");
        String size = jObject.getString("size");
        ArrayList<String> season = new ArrayList<String>();
        jObject.getJSONArray("season").forEach(x-> season.add((String)x));
        ArrayList<String> kind = new ArrayList<String>();
        jObject.getJSONArray("kind").forEach(x-> kind.add((String)x));
        ArrayList<Integer> washingMethod = new ArrayList<Integer>();
        jObject.getJSONArray("washingMethod").forEach(x-> washingMethod.add((Integer) x));
        ClothesPostDto clothesPostDto = new ClothesPostDto(fileSavePath,date, season, kind, washingMethod, size,false);
        try{
            clothesRepository.save(userToken, clothesPostDto);
            clothesResponse clothesResponse =  new clothesResponse(200, "등록 성공");
            return new ResponseEntity(clothesResponse,HttpStatus.OK);
        }
        catch (Exception e){
            clothesResponse clothesResponse =  new clothesResponse(404, "등록 실패");
            return new ResponseEntity(clothesResponse,HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity getClothes(String userToken, ArrayList<String> season, ArrayList<String> kind, boolean bookmark) {
        try {
            ArrayList<Clothes> clothesArrayList = clothesRepository.findByCategory(userToken, season, kind, bookmark);
            clothesGetResponse clothesGetResponse = new clothesGetResponse(clothesArrayList, 200, "조회 성공");
            return new ResponseEntity(clothesGetResponse, HttpStatus.OK);
        }
        catch (Exception e){
            clothesResponse clothesResponse =  new clothesResponse(404, "조회 실패");
            return new ResponseEntity(clothesResponse,HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity deleteClothes(String userToken, int clothesIdx) {
        try {
            clothesRepository.delete(userToken, clothesIdx);
            clothesResponse clothesResponse = new clothesResponse(200, "삭제 성공");
            return new ResponseEntity(clothesResponse, HttpStatus.OK);
        }
        catch (Exception e){
            clothesResponse clothesResponse = new clothesResponse(404, "삭제 실패");
            return new ResponseEntity(clothesResponse, HttpStatus.OK);
        }
    }

    public ResponseEntity updateClothes(String userToken,MultipartFile clothesImage, int clothesIdx, String clothes) throws IOException{
        String clothesImageUrl = clothesRepository.findClothesImageUrlByClothesIdx(clothesIdx);
        File f = new File(clothesImageUrl);
        clothesImage.transferTo(f);
        JSONObject jObject = new JSONObject(clothes);
        String date = jObject.getString("date");
        String size = jObject.getString("size");
        ArrayList<String> season = new ArrayList<String>();
        jObject.getJSONArray("season").forEach(x-> season.add((String)x));
        ArrayList<String> kind = new ArrayList<String>();
        jObject.getJSONArray("kind").forEach(x-> kind.add((String)x));
        ArrayList<Integer> washingMethod = new ArrayList<Integer>();
        jObject.getJSONArray("washingMethod").forEach(x-> washingMethod.add((Integer) x));
        ClothesPutDto clothesPutDto = new ClothesPutDto(clothesImageUrl,date, season, kind, washingMethod, size,false);

        try {
            clothesRepository.update(userToken, clothesIdx, clothesPutDto);
            clothesResponse clothesResponse = new clothesResponse(200, "수정 성공");
            return new ResponseEntity(clothesResponse, HttpStatus.OK);
        }
        catch (Exception e){
            clothesResponse clothesResponse =  new clothesResponse(404, "수정 실패");
            return new ResponseEntity(clothesResponse,HttpStatus.BAD_REQUEST);
        }
    }
//
    public ResponseEntity getClothesInfo(String userToken, int clothesIdx) {
        try {
            Clothes clothes = clothesRepository.findByClothesIdx(userToken, clothesIdx);
            clothesInfoGetResponse clothesInfoGetResponse = new clothesInfoGetResponse(clothes, 200, "상세 조회 성공");
            return new ResponseEntity(clothesInfoGetResponse, HttpStatus.OK);
        }
        catch (Exception e){
            clothesResponse clothesResponse = new clothesResponse(404, "상세 조회 실패");
            return new ResponseEntity(clothesResponse, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity bookmark(String userToken, int clothesIdx, boolean bookmark) {
        try {
            clothesRepository.bookmarkByClothesIdx(userToken, clothesIdx, bookmark);
            clothesResponse clothesResponse = new clothesResponse(200, "즐겨찾기 성공");
            return new ResponseEntity(clothesResponse, HttpStatus.OK);
        }
        catch (Exception e){
            clothesResponse clothesResponse = new clothesResponse(404,"즐겨찾기 실패");
            return new ResponseEntity(clothesResponse, HttpStatus.BAD_REQUEST);
        }
    }

//    public ArrayList<style> recommend(String userToken) {
//        weather weather = weatherService.getWeather();
//        return clothesRepository.recommend(userToken, weather);
//    }
}
