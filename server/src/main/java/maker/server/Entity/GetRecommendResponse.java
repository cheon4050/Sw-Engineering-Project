package maker.server.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetRecommendResponse extends Response{
    private ArrayList<String> urlList;
    public GetRecommendResponse(ArrayList<String> urlList, int status, String message){
        super(status, message);
        this.urlList = urlList;
    }
}
