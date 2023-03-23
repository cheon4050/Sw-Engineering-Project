package maker.server.dto.clothes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

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
}
