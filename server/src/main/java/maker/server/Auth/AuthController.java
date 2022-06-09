package maker.server.Auth;

import lombok.RequiredArgsConstructor;
import maker.server.Dto.User.UserDto;
import maker.server.Dto.User.UserFindDto;
import maker.server.Dto.User.UserLoginDto;
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

    @GetMapping("/login")
    public ResponseEntity login(@RequestBody UserLoginDto user){
        return authService.getUserToken(user);
    }

    @GetMapping("/password")
    public ResponseEntity password(@RequestBody UserFindDto user){
        return authService.getPassword(user);
    }

    @PutMapping("/user")
    public void updateUser(@RequestBody UserDto user){
        authService.updateUser(user);
    }

    @DeleteMapping("/user")
    public void DeleteUser(@RequestBody String userToken){
        authService.deleteUser(userToken);
    }
}
