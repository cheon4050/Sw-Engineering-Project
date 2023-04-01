package maker.server.clothes;

import lombok.AllArgsConstructor;
import maker.server.config.argumentresolver.JwtValidation;
import maker.server.dto.clothes.ClothesPostDto;
import maker.server.dto.clothes.ClothesPutDto;
import maker.server.entity.Clothes;
import maker.server.response.GetClothesInfoResponse;
import maker.server.response.GetClothesResponse;
import maker.server.response.GetRecommendResponse;
import maker.server.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/clothes")
@AllArgsConstructor
public class ClothesController {
    private final ClothesService clothesService;

    @PostMapping
    public ResponseEntity postClothes(@JwtValidation Integer userIdx,
                                      @RequestBody ClothesPostDto clothes
                                      ){
        clothesService.postClothes(userIdx, clothes);
        Response Response =  new Response(200, "등록 성공");
        return new ResponseEntity(Response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getClothes(
            @JwtValidation Integer userIdx,
            @RequestParam(value = "season", required = false)ArrayList<String> season,
            @RequestParam(value = "kind", required = false)ArrayList<String> kind,
            @RequestParam(value = "bookmark", required = false) boolean bookmark){
        List<Clothes> clothesArrayList = clothesService.getClothes(userIdx, season, kind, bookmark);
        GetClothesResponse GetClothesResponse = new GetClothesResponse(clothesArrayList, 200, "조회 성공");
        return new ResponseEntity(GetClothesResponse, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity deleteClothes(
            @JwtValidation Integer userIdx,
            @RequestParam int clothesIdx){
        clothesService.deleteClothes(userIdx, clothesIdx);
        Response Response = new Response(200, "삭제 성공");
        return new ResponseEntity(Response, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity updateClothes(@JwtValidation Integer userIdx,
                                        @RequestBody ClothesPutDto clothes,
                            @RequestParam int clothesIdx
    ){
        clothesService.updateClothes(userIdx, clothes, clothesIdx);
        Response Response = new Response(200, "수정 성공");
        return new ResponseEntity(Response, HttpStatus.OK);

    }

    @GetMapping("/info")
    public ResponseEntity getClothesInfo(
            @JwtValidation Integer userIdx,
            @RequestParam int clothesIdx){
        Clothes clothes = clothesService.getClothesInfo(userIdx, clothesIdx);
        GetClothesInfoResponse GetClothesInfoResponse = new GetClothesInfoResponse(clothes, 200, "상세 조회 성공");
        return new ResponseEntity(GetClothesInfoResponse, HttpStatus.OK);
    }

    @PatchMapping("/bookmark")
    public  ResponseEntity bookmark(
            @JwtValidation Integer userIdx,
            @RequestParam int clothesIdx,
            @RequestParam boolean bookmark){
        clothesService.bookmark(userIdx, clothesIdx, bookmark);
        Response Response = new Response(200, "즐겨찾기 성공");
        return new ResponseEntity(Response, HttpStatus.OK);
    }

    @GetMapping("/recommend")
    public ResponseEntity recommend(@JwtValidation Integer userIdx) throws Exception {
        List<String> urlList = clothesService.recommend(userIdx);
        GetRecommendResponse getRecommendResponse = new GetRecommendResponse(urlList, 200, "의류 추천 성공");
        return new ResponseEntity(getRecommendResponse,HttpStatus.OK);
    }
}
