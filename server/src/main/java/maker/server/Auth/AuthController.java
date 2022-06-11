package maker.server.Auth;

import lombok.RequiredArgsConstructor;
import maker.server.Dto.User.*;
import maker.server.Entity.User;
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

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserLoginDto user){
        return authService.getUserToken(user);
    }

    @PostMapping("/password")
    public ResponseEntity password(@RequestBody UserFindDto user){
        return authService.getPassword(user);
    }

    @PutMapping("/user")
    public ResponseEntity updateUser(@RequestBody UserUpdateDto user){
        return authService.updateUser(user);
    }

    @DeleteMapping("/user")
    public ResponseEntity DeleteUser(@RequestBody UserDeleteDto user){
        return authService.deleteUser(user);
    }
}
