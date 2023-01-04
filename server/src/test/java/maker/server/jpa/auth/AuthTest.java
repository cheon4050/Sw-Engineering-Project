package maker.server.jpa.auth;

import maker.server.auth.AuthJpaRepository;
import maker.server.entity.Users;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@Transactional
public class AuthTest {

    @Autowired
    private AuthJpaRepository authJpaRepository;

    @Test
    void createUsersTest(){
        Users user = new Users("cheon4050@gmail.com", "1234", "동두천");

        authJpaRepository.save(user);

        Users user2 = authJpaRepository.findByEmail("cheon4050@gmail.com").get();
        Assertions.assertThat(user.getEmail()).isEqualTo(user2.getEmail());
        Assertions.assertThat(user.getPassword()).isEqualTo(user2.getPassword());
        Assertions.assertThat(user.getId()).isEqualTo(user2.getId());
        Assertions.assertThat(user.getAnswer()).isEqualTo(user2.getAnswer());
    }

    @Test
    void emailDuplicateCheckTest(){
        Users user = new Users("cheon4050@gmail.com", "1234", "동두천");
        String findEmail = "cheon405011@gmail.com";
        authJpaRepository.save(user);

        Optional<Users> findUser =  authJpaRepository.findByEmail(findEmail);

        Assertions.assertThat(findUser).isEmpty();
    }

    @Test
    void loginSuccessTest(){
        Users user = new Users("cheon4050@gmail.com", "1234", "동두천");
        String loginEmail = "cheon4050@gmail.com";
        String loginPassword = "1234";
        authJpaRepository.save(user);

        Optional<Users> loginUser = authJpaRepository.findByEmailAndPassword(loginEmail, loginPassword);

        Assertions.assertThat(loginUser).isNotEmpty();
    }

    @Test
    void loginFailedTest(){
        Users user = new Users("cheon4050@gmail.com", "1234", "동두천");
        String loginEmail = "cheon4050@gmail.com";
        String loginPassword = "12345";
        authJpaRepository.save(user);

        Optional<Users> loginUser = authJpaRepository.findByEmailAndPassword(loginEmail, loginPassword);

        Assertions.assertThat(loginUser).isEmpty();
    }

    @Test
    void findPasswordSuccessTest(){
        Users user = new Users("cheon4050@gmail.com", "1234", "동두천");
        String findEmail = "cheon4050@gmail.com";
        String findAnswer = "동두천";
        authJpaRepository.save(user);

        Optional<Users> findUser = authJpaRepository.findByEmailAndAnswer(findEmail, findAnswer);

        Assertions.assertThat(findUser).isNotEmpty();
    }

    @Test
    void findPasswordFailedTest(){
        Users user = new Users("cheon4050@gmail.com", "1234", "동두천");
        String findEmail = "cheon4050@gmail.com";
        String findAnswer = "서울";
        authJpaRepository.save(user);

        Optional<Users> findUser = authJpaRepository.findByEmailAndAnswer(findEmail, findAnswer);

        Assertions.assertThat(findUser).isEmpty();
    }

    @Test
    void updateUserTest(){
        Users user = new Users("cheon4050@gmail.com", "1234", "동두천");
        String updateEmail = "ehdcjs159@gmail.com";
        String updatePassword = "12345";
        authJpaRepository.save(user);
        int userId = user.getId();

        authJpaRepository.updateEmailAndPassword(userId, updateEmail, updatePassword);
        Optional<Users> findUser = authJpaRepository.findById(userId);

        Assertions.assertThat(findUser.get().getEmail()).isEqualTo(updateEmail);
    }

    @Test
    void deleteUserTest(){
        Users user = new Users("cheon4050@gmail.com", "1234", "동두천");
        authJpaRepository.save(user);
        int userId = user.getId();

        authJpaRepository.deleteById(userId);
        Optional<Users> findUser =  authJpaRepository.findById(userId);

        Assertions.assertThat(findUser).isEmpty();
    }
}
