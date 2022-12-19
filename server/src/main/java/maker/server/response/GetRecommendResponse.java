package maker.server.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetRecommendResponse extends Response{
    private ArrayList<String> clothesImageUrl;
    public GetRecommendResponse(ArrayList<String> clothesImageUrl, int status, String message){
        super(status, message);
        this.clothesImageUrl = clothesImageUrl;
    }
}
