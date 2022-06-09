package maker.server.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetUserPasswordResponse extends Response{
    private String password;
    public GetUserPasswordResponse(String password, int status, String message){
        super(status,message);
        this.password = password;
    }
}
