package maker.server.Auth;

import lombok.RequiredArgsConstructor;
import maker.server.Dto.User.*;
import maker.server.Response.GetUserPasswordResponse;
import maker.server.Response.Response;
import maker.server.Response.GetUserLoginResponse;
import maker.server.Util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository authRepository;
    private final JwtUtil jwtUtil;

    public ResponseEntity addUser(UserDto user) {
        authRepository.save(user);
        return new ResponseEntity(new Response(200, "회원가입 성공"), HttpStatus.OK);

    }

    public ResponseEntity getUserToken(UserLoginDto user) {
        int userIdx = authRepository.findUserIdx(user);
        String jwt =  jwtUtil.createJWT(userIdx, 3*60*60);
        return new ResponseEntity(new GetUserLoginResponse(jwt, 200,"토큰 생성 성공"), HttpStatus.OK);
    }

    public ResponseEntity getPassword(UserFindDto user) {
        String password = authRepository.findPassword(user);
        return new ResponseEntity(new GetUserPasswordResponse(password, 200, "비밀번호 찾기 성공"),HttpStatus.OK);
    }

    public ResponseEntity updateUser(String userToken, UserUpdateDto user) {
        Integer userIdx = jwtUtil.parseJwt(userToken);
        authRepository.update(userIdx, user);
        return new ResponseEntity(new Response(200, "회원 정보 수정 성공"), HttpStatus.OK);
    }

    public ResponseEntity deleteUser(String userToken) {
        Integer userIdx = jwtUtil.parseJwt(userToken);
        authRepository.delete(userIdx);
        return new ResponseEntity(new Response(200, "회원 탈퇴 성공"), HttpStatus.OK);
    }

    public ResponseEntity userIdCheck(String userId) {
        authRepository.userIdCheck(userId);
        return new ResponseEntity(new Response(200, "이미 사용중인 아이디입니다."), HttpStatus.OK);
    }
}
