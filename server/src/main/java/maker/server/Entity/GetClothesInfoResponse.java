package maker.server.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetClothesInfoResponse extends Response{
    private Clothes clothes;
    public GetClothesInfoResponse(Clothes clothes, int status, String message){
        super(status,message);
        this.clothes = clothes;
    }
}