package maker.server.auth;

import lombok.RequiredArgsConstructor;
import maker.server.dto.user.UserSaveDto;
import maker.server.dto.user.UserFindDto;
import maker.server.dto.user.UserLoginDto;
import maker.server.dto.user.UserUpdateDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;

    @PostMapping
    public ResponseEntity authorization(@RequestBody UserSaveDto user){
        return authService.addUser(user);
    }

    @GetMapping("/check")
    public ResponseEntity check(@RequestParam String email){return authService.userIdCheck(email);}

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserLoginDto user){
        return authService.getUserToken(user);
    }

    @GetMapping("/password")
    public ResponseEntity password(@RequestParam String email, @RequestParam String answer){
        return authService.getPassword(email, answer);
    }

    @PatchMapping("/user")
    public ResponseEntity updateUser(@RequestHeader String userToken, @RequestBody UserUpdateDto user){
        return authService.updateUser(userToken, user);
    }

    @DeleteMapping("/user")
    public ResponseEntity DeleteUser(@RequestHeader String userToken){
        return authService.deleteUser(userToken);
    }
}
