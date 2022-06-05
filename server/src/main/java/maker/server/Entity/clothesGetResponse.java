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
    private ArrayList<Clothes> clothes;
    public clothesGetResponse(ArrayList<Clothes> clothes, int status, String message){
        super(status,message);
        this.clothes = clothes;
    }
}
