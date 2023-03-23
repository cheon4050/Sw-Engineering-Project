package maker.server.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@DynamicUpdate
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String email;

    private String password;

    private String answer;

    @Builder
    public Users(String email, String password, String answer) {
        this.email = email;
        this.password = password;
        this.answer = answer;
    }

    public void updateEmailAndPassword(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
