package maker.server.Auth;

import maker.server.Dto.User.UserDto;
import maker.server.Dto.User.UserFindDto;
import maker.server.Dto.User.UserLoginDto;
import maker.server.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.SqlValue;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLData;
import java.sql.SQLInput;
import java.sql.SQLType;

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
        jdbcTemplate.execute("select userToken from user where userId = '" +
                user.getUserId()+"' and password = '"+ user.getPassword() + "'");
        return "";
    }

    public String findPassword(UserFindDto user){
        jdbcTemplate.execute("select password from user where userId = '" +
                user.getUserId() + "' and birth = '" + user.getBirth() + "'");
        return "";
    }
    public void update(UserDto user){
        jdbcTemplate.execute("update user set userId = '" + user.getUserId() + "' and password = '" + user.getPassword() + "' and birth = '" + user.getBirth() + "'");

    }
    public void delete(String userToken) {
        jdbcTemplate.execute("delete from user where userToken = '" +
                userToken + "'");

    }
}
