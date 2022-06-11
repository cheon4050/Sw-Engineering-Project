package maker.server.Auth;

import maker.server.Dto.User.UserDto;
import maker.server.Dto.User.UserFindDto;
import maker.server.Dto.User.UserLoginDto;
import maker.server.Dto.User.UserUpdateDto;
import maker.server.Entity.Clothes;
import maker.server.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.SqlValue;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class AuthRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<String> stringMapper =  (rs, count) -> {
        return rs.getString(1);
    };

    private final RowMapper<Integer> intMapper = (rs,count) -> {
        return rs.getInt(1);
    };

    public void save(UserDto user) {

        jdbcTemplate.execute("INSERT INTO user(userId,password,birth) VALUES ('"+
                user.getUserId()+ "','"+
                user.getPassword()+ "','"+
                user.getAnswer()+ "')");
    }

    public int findUserIdx(UserLoginDto user){
        return jdbcTemplate.queryForObject("select userIdx from user where userId = '"
                +user.getUserId()+"'"
                , intMapper);

    }

    public String findPassword(UserFindDto user){
        return jdbcTemplate.queryForObject("select password from user where userId = '" +
                        user.getUserId() + "' and answer = '" + user.getAnswer() + "'",
                stringMapper);
    }
    public void update(Integer userIdx,UserUpdateDto user){
        jdbcTemplate.execute("update user set userId = '" + user.getUserId() +
                "', password = '" + user.getPassword() +
                "', birth = '" + user.getBirth() +
                "' where userIdx = " + userIdx);
    }
    public void delete(Integer userIdx) {
        jdbcTemplate.execute("delete from user where userIdx = " +
                userIdx);

    }

    public String userIdCheck(String userId) {
        return jdbcTemplate.queryForObject("select userId from user where userId = '" + userId + "'", stringMapper);
    }
}
