package maker.server.auth;

import maker.server.dto.user.UserSaveDto;
import maker.server.dto.user.UserFindDto;
import maker.server.dto.user.UserLoginDto;
import maker.server.dto.user.UserUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/*
* V1 AuthRepository
* JDBC 사용 AuthRepository
* */

//@Repository
public class AuthJdbcRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<String> stringMapper =  (rs, count) -> {
        return rs.getString(1);
    };

    private final RowMapper<Integer> intMapper = (rs,count) -> {
        return rs.getInt(1);
    };

    public void save(UserSaveDto user) {

        jdbcTemplate.execute("INSERT INTO user(userId,password,answer) VALUES ('"+
                user.getEmail()+ "','"+
                user.getPassword()+ "','"+
                user.getAnswer()+ "')");
    }

    public int findUserIdx(UserLoginDto user) {
        return jdbcTemplate.queryForObject("select userIdx from user where userId = '"
                +user.getEmail()+"'"
                , intMapper);

    }

    public String findPassword(UserFindDto user){
        return jdbcTemplate.queryForObject("select password from user where userId = '" +
                        user.getEmail() + "' and answer = '" + user.getAnswer() + "'",
                stringMapper);
    }
    public void update(Integer userIdx,UserUpdateDto user){
        jdbcTemplate.execute("update user set userId = '" + user.getEmail() +
                "', password = '" + user.getPassword() +
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
