package maker.server.Entity;

public class GetUserLoginResponse extends Response{
    private String userToken;
    public GetUserLoginResponse(String userToken, int status, String message){
        super(status,message);
        this.userToken= userToken;
    }
}
