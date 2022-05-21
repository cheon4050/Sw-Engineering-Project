package maker.server.Auth;

import maker.server.Dto.User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {
    public void addUser(User user) {
        System.out.printf("userIdx = %d, userId = %s, password = %s, birth = %d", user.getUserIdx(),user.getUserId(), user.getPassword(), user.getBirth());
    }

    public int getUserIdx(Object obj) {
        return 1;
    }

    public String getPassword(Object obj) {
        return "";
    }

    public void updateUser(Object obj) {
    }

    public void deleteUser(Object obj) {
    }
}
