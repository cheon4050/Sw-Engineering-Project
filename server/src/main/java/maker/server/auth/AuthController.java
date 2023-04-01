package maker.server.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maker.server.config.argumentresolver.JwtValidation;
import maker.server.dto.user.UserSaveDto;
import maker.server.dto.user.UserLoginDto;
import maker.server.dto.user.UserUpdateDto;
import maker.server.response.GetUserLoginResponse;
import maker.server.response.GetUserPasswordResponse;
import maker.server.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;

    @PostMapping
    public ResponseEntity authorization(@RequestBody UserSaveDto user){
        authService.addUser(user);
        return new ResponseEntity(new Response(200, "회원가입 성공"), HttpStatus.OK);
    }

    @GetMapping("/check")
    public ResponseEntity check(@RequestParam String email){
        authService.userIdCheck(email);
        return new ResponseEntity(new Response(200, "사용가능한 아이디입니다."), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserLoginDto user){

        String jwt = authService.getUserToken(user);
        return new ResponseEntity(new GetUserLoginResponse(jwt, 200,"토큰 생성 성공"), HttpStatus.OK);
    }

    @GetMapping("/password")
    public ResponseEntity password(@RequestParam String email, @RequestParam String answer){
        String password = authService.getPassword(email, answer);
        return new ResponseEntity(new GetUserPasswordResponse(password, 200, "비밀번호 찾기 성공"),HttpStatus.OK);
    }

    @PatchMapping("/user")
    public ResponseEntity updateUser(@JwtValidation Integer userIdx, @RequestBody UserUpdateDto user){
        authService.updateUser(userIdx, user);
        return new ResponseEntity(new Response(200, "회원 정보 수정 성공"), HttpStatus.OK);
    }

    @DeleteMapping("/user")
    public ResponseEntity DeleteUser(@JwtValidation Integer userIdx){
        authService.deleteUser(userIdx);
        return new ResponseEntity(new Response(200, "회원 탈퇴 성공"), HttpStatus.OK);
    }

}
