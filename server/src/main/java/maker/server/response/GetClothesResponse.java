package maker.server.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import maker.server.entity.Clothes;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetClothesResponse extends Response{
    private ArrayList<Clothes> clothes;
    public GetClothesResponse(ArrayList<Clothes> clothes, int status, String message){
        super(status,message);
        this.clothes = clothes;
    }
}
