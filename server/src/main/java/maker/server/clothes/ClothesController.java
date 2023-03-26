package maker.server.clothes;

import lombok.AllArgsConstructor;
import maker.server.config.argumentresolver.JwtValidation;
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
    public ResponseEntity postClothes(@JwtValidation Integer userIdx,
                                      @RequestBody ClothesPostDto clothes
                                      ){
        return clothesService.postClothes(userIdx, clothes);
    }

    @GetMapping
    public ResponseEntity getClothes(
            @JwtValidation Integer userIdx,
            @RequestParam(value = "season", required = false)ArrayList<String> season,
            @RequestParam(value = "kind", required = false)ArrayList<String> kind,
            @RequestParam(value = "bookmark", required = false) boolean bookmark){
        return clothesService.getClothes(userIdx, season, kind, bookmark);
    }

    @DeleteMapping
    public ResponseEntity deleteClothes(
            @JwtValidation Integer userIdx,
            @RequestParam int clothesIdx){
        return clothesService.deleteClothes(userIdx, clothesIdx);
    }

    @PutMapping
    public ResponseEntity updateClothes(@JwtValidation Integer userIdx,
                                        @RequestBody ClothesPutDto clothes,
                            @RequestParam int clothesIdx
    ){
        return clothesService.updateClothes(userIdx, clothes, clothesIdx);

    }

    @GetMapping("/info")
    public ResponseEntity getClothesInfo(
            @JwtValidation Integer userIdx,
            @RequestParam int clothesIdx){
        return clothesService.getClothesInfo(userIdx, clothesIdx);
    }

    @PostMapping("/bookmark")
    public  ResponseEntity bookmark(
            @JwtValidation Integer userIdx,
            @RequestParam int clothesIdx,
            @RequestParam boolean bookmark){
        return clothesService.bookmark(userIdx, clothesIdx, bookmark);
    }

    @GetMapping("/recommend")
    public ResponseEntity recommend(@JwtValidation Integer userIdx) throws Exception {
        return clothesService.recommend(userIdx);
    }
}
