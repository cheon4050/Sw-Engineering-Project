package maker.server.Clothes;

import lombok.RequiredArgsConstructor;
import maker.server.Dto.Clothes.ClothesDto;
import maker.server.Dto.Clothes.ClothesPostDto;
import maker.server.Dto.Clothes.ClothesPutDto;
import maker.server.Entity.Clothes;
import maker.server.Entity.style;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@RestController
@RequestMapping("/clothes")
@RequiredArgsConstructor
public class ClothesController {
    private final ClothesService clothesService;

    @PostMapping
    public ResponseEntity postClothes(@RequestHeader String userToken,
                                      @RequestBody ClothesPostDto clothes
                                      )throws java.io.IOException{
        System.out.println("clothes = " + clothes);
        return clothesService.postClothes(userToken, clothes);
    }

    @GetMapping
    public ResponseEntity getClothes(
            @RequestHeader String userToken,
            @RequestParam(value = "season", required = false)ArrayList<String> season,
            @RequestParam(value = "kind", required = false)ArrayList<String> kind,
            @RequestParam(value = "bookmark", required = false) boolean bookmark){
        return clothesService.getClothes(userToken, season, kind, bookmark);
    }

    @DeleteMapping
    public ResponseEntity deleteClothes(
            @RequestHeader String userToken,
            @RequestParam int clothesIdx){
        return clothesService.deleteClothes(userToken, clothesIdx);
    }

    @PutMapping
    public ResponseEntity updateClothes(@RequestHeader String userToken,
                                        @RequestBody ClothesPutDto clothes,
                            @RequestParam int clothesIdx
    )throws java.io.IOException{
        return clothesService.updateClothes(userToken, clothes, clothesIdx);

    }

    @GetMapping("/info")
    public ResponseEntity getClothesInfo(
            @RequestHeader String userToken,
            @RequestParam int clothesIdx){
        return clothesService.getClothesInfo(userToken, clothesIdx);
    }

    @PostMapping("/bookmark")
    public  ResponseEntity bookmark(
            @RequestHeader String userToken,
            @RequestParam int clothesIdx,
            @RequestParam boolean bookmark){
        return clothesService.bookmark(userToken, clothesIdx, bookmark);
    }

//    @GetMapping("/Recommend")
//    public ArrayList<style> recommend(@RequestHeader String userToken){
//        return clothesService.recommend(userToken);
//    }
}
