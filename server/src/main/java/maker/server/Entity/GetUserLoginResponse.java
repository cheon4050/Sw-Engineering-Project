package maker.server.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetUserLoginResponse extends Response{
    private String userToken;
    public GetUserLoginResponse(String userToken, int status, String message){
        super(status,message);
        this.userToken= userToken;
    }
}
