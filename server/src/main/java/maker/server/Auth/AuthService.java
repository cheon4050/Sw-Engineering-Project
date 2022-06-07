package maker.server.Auth;

import lombok.RequiredArgsConstructor;
import maker.server.Dto.User.UserDto;
import maker.server.Dto.User.UserFindDto;
import maker.server.Dto.User.UserLoginDto;
import maker.server.Entity.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository authRepository;

    public void addUser(UserDto user) {

        authRepository.save(user);
    }

    public String getUserToken(UserLoginDto user) {
        return authRepository.findUserToken(user);
    }

    public String getPassword(UserFindDto user) {
        return authRepository.findPassword(user);
    }

    public void updateUser(UserDto user) {
        authRepository.update(user);
    }

    public void deleteUser(String userToken) {
        authRepository.delete(userToken);
    }
}
