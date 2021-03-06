package maker.server.Auth;

import lombok.RequiredArgsConstructor;
import maker.server.Dto.User.UserDto;
import maker.server.Dto.User.UserFindDto;
import maker.server.Dto.User.UserLoginDto;
import maker.server.Dto.User.UserUpdateDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;

    @PostMapping
    public ResponseEntity authorization(@RequestBody UserDto user){
        return authService.addUser(user);
    }

    @GetMapping("/check")
    public ResponseEntity check(@RequestParam String userId){return authService.userIdCheck(userId);}


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserLoginDto user){
        return authService.getUserToken(user);
    }

    @PostMapping("/password")
    public ResponseEntity password(@RequestBody UserFindDto user){
        return authService.getPassword(user);
    }

    @PutMapping("/user")
    public ResponseEntity updateUser(@RequestHeader String userToken, @RequestBody UserUpdateDto user){
        return authService.updateUser(userToken, user);
    }

    @DeleteMapping("/user")
    public ResponseEntity DeleteUser(@RequestHeader String userToken){
        return authService.deleteUser(userToken);
    }
}
