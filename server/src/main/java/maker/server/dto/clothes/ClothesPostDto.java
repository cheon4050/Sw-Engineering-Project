package maker.server.dto.clothes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import maker.server.entity.Clothes;
import maker.server.entity.Users;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClothesPostDto {

    private String clothesImageUrl;
    private String date;
    private ArrayList<String> season;
    private ArrayList<String> kind;
    private ArrayList<Integer> washingMethod;
    private char size;

    public Clothes toEntity(Users users){
        return Clothes.builder()
                .users(users)
                .clothesImageUrl(clothesImageUrl)
                .season(convertArrayToString(season))
                .kind(convertArrayToString(kind))
                .washingMethod(washingMethod)
                .size(size)
                .bookmark(false)
                .build();

    }

    private String convertArrayToString(ArrayList<String> attribute){
        return attribute.stream().map(String::valueOf).collect(Collectors.joining(","));
    }
}
