package maker.server.Clothes;

import lombok.RequiredArgsConstructor;
import maker.server.Dto.Clothes.ClothesDto;
import maker.server.Entity.Clothes;
import maker.server.Entity.style;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;

@RestController
@RequestMapping("/clothes")
@RequiredArgsConstructor
public class ClothesController {
    private final ClothesService clothesService;

    @PostMapping
    public ResponseEntity postClothes(@RequestHeader String userToken,
                                      @RequestPart(value="clothesImage") MultipartFile clothesImage,
                                      @RequestPart(value = "clothes") String clothes
                            )throws java.io.IOException{
        System.out.println(userToken);
        System.out.println(clothesImage);
        System.out.println(clothes);
        return clothesService.postClothes(userToken, clothesImage, clothes);
    }

    @GetMapping
    public ResponseEntity getClothes(
            @RequestHeader String userToken,
            @RequestParam(value = "season", required = false)ArrayList<String> season,
            @RequestParam(value = "kind", required = false)ArrayList<String> kind,
            @RequestParam(value = "bookmark", required = false) boolean bookmark){
        System.out.println("userToken = " + userToken);
        System.out.println("season = " + season);
        System.out.println("kind = " + kind);
        System.out.println("bookmark = " + bookmark);
        return clothesService.getClothes(userToken, season, kind, bookmark);
    }

//    @DeleteMapping
//    public void deleteClothes(
//            @RequestHeader String userToken,
//            @RequestParam int clothesIdx){
//        clothesService.deleteClothes(userToken, clothesIdx);
//    }
//
//    @PutMapping
//    public void updateClothes(
//            @RequestHeader String userToken,
//            @RequestParam int clothesIdx,
//            @RequestBody ClothesDto clothes){
//        clothesService.updateClothes(userToken, clothesIdx, clothes);
//    }
//
//    @GetMapping("/info")
//    public Clothes getClothesInfo(
//            @RequestHeader String userToken,
//            @RequestParam int clothesIdx){
//        return clothesService.getClothesInfo(userToken, clothesIdx);
//    }
//
//    @PostMapping("/Bookmark")
//    public void bookmark(
//            @RequestHeader String userToken,
//            @RequestParam int clothesIdx){
//        clothesService.bookmark(userToken, clothesIdx);
//    }
//
//    @GetMapping("/Recommend")
//    public ArrayList<style> recommend(@RequestHeader String userToken){
//        return clothesService.recommend(userToken);
//    }
}
