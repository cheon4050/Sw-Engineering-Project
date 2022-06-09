package maker.server.Auth;

import lombok.RequiredArgsConstructor;
import maker.server.Dto.User.UserDto;
import maker.server.Dto.User.UserFindDto;
import maker.server.Dto.User.UserLoginDto;
import maker.server.Entity.GetUserPasswordResponse;
import maker.server.Entity.Response;
import maker.server.Entity.GetUserLoginResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository authRepository;
    private final JwtGenerator jwtGenerator;

    public ResponseEntity addUser(UserDto user) {
        try {
            authRepository.save(user);
            return new ResponseEntity(new Response(200, "회원가입 성공"), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity(new Response(404, "회원가입 실패"), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity getUserToken(UserLoginDto user) {
        int userIdx = authRepository.findUserIdx(user);
        String jwt =  jwtGenerator.createJWT(userIdx, 3*60*60);

        return new ResponseEntity(new GetUserLoginResponse(jwt, 200,"토큰 생성 성공"), HttpStatus.OK);
    }

    public ResponseEntity getPassword(UserFindDto user) {
        String password = authRepository.findPassword(user);
        return new ResponseEntity(new GetUserPasswordResponse(password, 200, "비밀번호 찾기 성공"),HttpStatus.OK);
    }

    public void updateUser(UserDto user) {
        authRepository.update(user);
    }

    public void deleteUser(String userToken) {
        authRepository.delete(userToken);
    }
}
