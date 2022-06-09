package maker.server.Entity;

public class GetUserPasswordResponse extends Response{
    private String password;
    public GetUserPasswordResponse(String password, int status, String message){
        super(status,message);
        this.password = password;
    }
}
