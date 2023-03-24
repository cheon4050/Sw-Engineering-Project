package maker.server.clothes;

import lombok.AllArgsConstructor;
import maker.server.dto.clothes.ClothesPostDto;
import maker.server.dto.clothes.ClothesPutDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/clothes")
@AllArgsConstructor
public class ClothesController {
    private final ClothesService clothesService;

    @PostMapping
    public ResponseEntity postClothes(@RequestHeader String userToken,
                                      @RequestBody ClothesPostDto clothes
                                      ){
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
    ){
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

    @GetMapping("/recommend")
    public ResponseEntity recommend(@RequestHeader String userToken) throws Exception {
        return clothesService.recommend(userToken);
    }
}
