package maker.server.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class GetUserLoginResponse extends Response{
    private String userToken;
    public GetUserLoginResponse(String userToken, int status, String message){
        super(status,message);
        this.userToken= userToken;
    }
}
