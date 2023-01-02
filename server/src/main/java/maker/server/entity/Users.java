package maker.server.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userIdx;

    private String userId;

    private String password;

    private String answer;

    @Builder
    public User(String userId, String password, String answer) {
        this.userId = userId;
        this.password = password;
        this.answer = answer;
    }
}
