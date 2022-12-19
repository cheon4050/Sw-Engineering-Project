package maker.server.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserPasswordResponse extends Response{
    private String password;
    public GetUserPasswordResponse(String password, int status, String message){
        super(status,message);
        this.password = password;
    }
}
