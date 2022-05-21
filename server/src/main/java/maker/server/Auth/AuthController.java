package maker.server.Auth;

import lombok.RequiredArgsConstructor;
import maker.server.Dto.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;

    @PostMapping
    public void authorization(@RequestBody User user){
        authService.addUser(user);
    }

    @GetMapping("/login")
    public int login(@RequestBody Object obj){
        return authService.getUserIdx(obj);
    }

    @GetMapping("/password")
    public String password(@RequestBody Object obj){
        return authService.getPassword(obj);
    }

    @PutMapping("/user")
    public void updateUser(@RequestBody Object obj){
        authService.updateUser(obj);
    }

    @DeleteMapping("/user")
    public void DeleteUser(@RequestBody Object obj){
        authService.deleteUser(obj);
    }
}
