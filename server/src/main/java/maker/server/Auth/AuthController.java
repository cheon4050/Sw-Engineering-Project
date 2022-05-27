package maker.server.Auth;

import lombok.RequiredArgsConstructor;
import maker.server.Dto.User.UserDto;
import maker.server.Dto.User.UserFindDto;
import maker.server.Dto.User.UserLoginDto;
import maker.server.Entity.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;

    @PostMapping
    public void authorization(@RequestBody UserDto user){
        authService.addUser(user);
    }

    @GetMapping("/login")
    public String login(@RequestBody UserLoginDto user){
        return authService.getUserToken(user);
    }

    @GetMapping("/password")
    public String password(@RequestBody UserFindDto user){
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
