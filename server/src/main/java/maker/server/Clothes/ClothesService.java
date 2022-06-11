package maker.server.Clothes;

import lombok.RequiredArgsConstructor;
import maker.server.Dto.Clothes.ClothesPostDto;
import maker.server.Dto.Clothes.ClothesPutDto;
import maker.server.Entity.Clothes;
import maker.server.Entity.GetClothesResponse;
import maker.server.Entity.GetClothesInfoResponse;
import maker.server.Entity.Response;
import maker.server.Weather.WeatherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ClothesService {

    private final ClothesRepository clothesRepository;
    private final WeatherService weatherService;

    public ResponseEntity  postClothes(String userToken, ClothesPostDto clothes) throws IOException
    {
        clothes.setBookmark(false);
        try{
            clothesRepository.save(userToken, clothes);
            Response Response =  new Response(200, "등록 성공");
            return new ResponseEntity(Response,HttpStatus.OK);
        }
        catch (Exception e){
            Response Response =  new Response(404, "등록 실패");
            return new ResponseEntity(Response,HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity getClothes(String userToken, ArrayList<String> season, ArrayList<String> kind, boolean bookmark) {
        try {
            ArrayList<Clothes> clothesArrayList = clothesRepository.findByCategory(userToken, season, kind, bookmark);
            GetClothesResponse GetClothesResponse = new GetClothesResponse(clothesArrayList, 200, "조회 성공");
            return new ResponseEntity(GetClothesResponse, HttpStatus.OK);
        }
        catch (Exception e){
            Response Response =  new Response(404, "조회 실패");
            return new ResponseEntity(Response,HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity deleteClothes(String userToken, int clothesIdx) {
        try {
            clothesRepository.delete(userToken, clothesIdx);
            Response Response = new Response(200, "삭제 성공");
            return new ResponseEntity(Response, HttpStatus.OK);
        }
        catch (Exception e){
            Response Response = new Response(404, "삭제 실패");
            return new ResponseEntity(Response, HttpStatus.OK);
        }
    }

    public ResponseEntity updateClothes(String userToken, ClothesPutDto clothes,int clothesIdx) throws IOException{
        try {
            clothesRepository.update(userToken, clothesIdx, clothes);
            Response Response = new Response(200, "수정 성공");
            return new ResponseEntity(Response, HttpStatus.OK);
        }
        catch (Exception e){
            Response Response =  new Response(404, "수정 실패");
            return new ResponseEntity(Response,HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity getClothesInfo(String userToken, int clothesIdx) {
        try {
            Clothes clothes = clothesRepository.findByClothesIdx(userToken, clothesIdx);
            GetClothesInfoResponse GetClothesInfoResponse = new GetClothesInfoResponse(clothes, 200, "상세 조회 성공");
            return new ResponseEntity(GetClothesInfoResponse, HttpStatus.OK);
        }
        catch (Exception e){
            Response Response = new Response(404, "상세 조회 실패");
            return new ResponseEntity(Response, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity bookmark(String userToken, int clothesIdx, boolean bookmark) {
        try {
            clothesRepository.bookmarkByClothesIdx(userToken, clothesIdx, bookmark);
            Response Response = new Response(200, "즐겨찾기 성공");
            return new ResponseEntity(Response, HttpStatus.OK);
        }
        catch (Exception e){
            Response Response = new Response(404,"즐겨찾기 실패");
            return new ResponseEntity(Response, HttpStatus.BAD_REQUEST);
        }
    }

//    public ArrayList<style> recommend(String userToken) {
//        weather weather = weatherService.getWeather();
//        return clothesRepository.recommend(userToken, weather);
//    }
}
