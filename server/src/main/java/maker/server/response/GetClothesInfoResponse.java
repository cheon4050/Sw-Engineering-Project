package maker.server.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import maker.server.entity.Clothes;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetClothesInfoResponse extends Response{
    private Clothes clothes;
    public GetClothesInfoResponse(Clothes clothes, int status, String message){
        super(status,message);
        this.clothes = clothes;
    }
}
