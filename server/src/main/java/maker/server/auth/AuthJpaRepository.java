package maker.server.auth;

import maker.server.entity.Clothes;
import maker.server.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


/*
 * V2 AuthRepository
 * Jpa 사용 AuthRepository
 * */
@Repository
public interface AuthJpaRepository extends JpaRepository<Users, Integer> {

    Optional<Users> findByEmail(String email);

    Optional<Users> findByEmailAndPassword(String email, String password);

    Optional<Users> findByEmailAndAnswer(String email, String answer);

    @Query("update Users set email = :email, password = :password where id = :id")
    @Modifying(clearAutomatically = true)
    void updateEmailAndPassword(@Param("id") int id, @Param("email") String email, @Param("password") String password);
}
