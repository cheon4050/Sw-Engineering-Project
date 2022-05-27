package maker.server.Auth;

import maker.server.Dto.User.UserDto;
import maker.server.Dto.User.UserFindDto;
import maker.server.Dto.User.UserLoginDto;
import maker.server.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AuthRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(UserDto user) {
        jdbcTemplate.execute("INSERT INTO user(userId,password,birth) VALUES ('"+
                user.getUserId()+ "','"+
                user.getPassword()+ "','"+
                user.getBirth()+ "')");
    }

    public String findUserToken(UserLoginDto user){
        return "";
    }

    public String findPassword(UserFindDto user){
        return "";
    }
    public void update(UserDto user){

    }
    public void delete(String userToken) {

    }
}
