package maker.server.auth;

import lombok.RequiredArgsConstructor;
import maker.server.dto.user.*;
import maker.server.entity.Users;
import maker.server.error.entity.AuthException;
import maker.server.util.jwt.JwtUtil;
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

    public void addUser(UserSaveDto userSaveDto) {
        Users saveUser = userSaveDto.toEntity();
        authJpaRepository.save(saveUser);
    }

    public String getUserToken(UserLoginDto userLoginDto) {
        Optional<Users> users = authJpaRepository.findByEmailAndPassword(userLoginDto.getEmail(), userLoginDto.getPassword());
        if (users.isEmpty()){
            throw new AuthException(USER_NOT_FOUND);
        }
        int userIdx = users.get().getId();
        String jwt = jwtUtil.createJWT(userIdx, 3*60*60);
        return jwt;
    }

    public String getPassword(String email, String answer) {
        Optional<Users> user = authJpaRepository.findByEmailAndAnswer(email, answer);

        if(user.isEmpty()){
            throw new AuthException(PASSWORD_NOT_FOUND);
        }
        String password = user.get().getPassword();
        return password;
    }

    @Transactional
    public void updateUser(Integer userIdx, UserUpdateDto userUpdateDto) {
        Users user = authJpaRepository.findById(userIdx).get();
        user.updateEmailAndPassword(userUpdateDto.getEmail(), userUpdateDto.getPassword());
    }

    public void deleteUser(Integer userIdx) {
        authJpaRepository.deleteById(userIdx);
    }

    public void userIdCheck(String email) {
        Optional<Users> user = authJpaRepository.findByEmail(email);
        if (!user.isEmpty()){
            throw new AuthException(DUPLICATE_EMAIL);
        }
    }
}
