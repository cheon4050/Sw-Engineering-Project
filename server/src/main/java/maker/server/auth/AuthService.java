package maker.server.auth;

import lombok.RequiredArgsConstructor;
import maker.server.dto.user.*;
import maker.server.entity.Users;
import maker.server.error.entity.AuthException;
import maker.server.response.GetUserPasswordResponse;
import maker.server.response.Response;
import maker.server.response.GetUserLoginResponse;
import maker.server.util.jwt.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static maker.server.error.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final AuthJpaRepository authJpaRepository;
    private final JwtUtil jwtUtil;

    public ResponseEntity addUser(UserSaveDto userSaveDto) {
        Users saveUser = userSaveDto.toEntity();
        authJpaRepository.save(saveUser);
        return new ResponseEntity(new Response(200, "회원가입 성공"), HttpStatus.OK);
    }

    public ResponseEntity getUserToken(UserLoginDto userLoginDto) {
        Optional<Users> users = authJpaRepository.findByEmailAndPassword(userLoginDto.getEmail(), userLoginDto.getPassword());
        if (users.isEmpty()){
            throw new AuthException(USER_NOT_FOUND);
        }
        int userIdx = users.get().getId();
        String jwt = jwtUtil.createJWT(userIdx, 3*60*60);
        return new ResponseEntity(new GetUserLoginResponse(jwt, 200,"토큰 생성 성공"), HttpStatus.OK);
    }

    public ResponseEntity getPassword(String email, String answer) {
        Optional<Users> user = authJpaRepository.findByEmailAndAnswer(email, answer);

        if(user.isEmpty()){
            throw new AuthException(PASSWORD_NOT_FOUND);
        }
        String password = user.get().getPassword();
        return new ResponseEntity(new GetUserPasswordResponse(password, 200, "비밀번호 찾기 성공"),HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity updateUser(Integer userIdx, UserUpdateDto userUpdateDto) {
//        authJpaRepository.updateEmailAndPassword(userIdx, userUpdateDto.getEmail(), userUpdateDto.getPassword());
        Users user = authJpaRepository.findById(userIdx).get();
        user.updateEmailAndPassword(userUpdateDto.getEmail(), userUpdateDto.getPassword());
        return new ResponseEntity(new Response(200, "회원 정보 수정 성공"), HttpStatus.OK);
    }

    public ResponseEntity deleteUser(String userToken) {
        Integer userIdx = jwtUtil.parseJwt(userToken);
        authJpaRepository.deleteById(userIdx);
        return new ResponseEntity(new Response(200, "회원 탈퇴 성공"), HttpStatus.OK);
    }

    public ResponseEntity userIdCheck(String email) {
        Optional<Users> user = authJpaRepository.findByEmail(email);
        if (!user.isEmpty()){
            throw new AuthException(DUPLICATE_EMAIL);
        }
        return new ResponseEntity(new Response(200, "사용가능한 아이디입니다."), HttpStatus.OK);
    }
}
