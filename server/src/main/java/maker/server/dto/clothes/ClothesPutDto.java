package maker.server.dto.clothes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClothesPutDto {

    private String clothesImageUrl;
    private String date;
    private ArrayList<String> season;
    private ArrayList<String> kind;
    private ArrayList<Integer> washingMethod;
    private char size;
    private boolean bookmark;

    public String getSeason(){
        return season.stream().map(String::valueOf).collect(Collectors.joining(","));
    }

    public String getKind(){
        return kind.stream().map(String::valueOf).collect(Collectors.joining(","));
    }
}
