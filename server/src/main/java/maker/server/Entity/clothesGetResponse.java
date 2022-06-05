package maker.server.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class clothesGetResponse extends clothesResponse{
    private ArrayList<Clothes> clothesArrayList;
    public clothesGetResponse(ArrayList<Clothes> clothesArrayList, int status, String message){
        super(status,message);
        this.clothesArrayList = clothesArrayList;
    }
}
