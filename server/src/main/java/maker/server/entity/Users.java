package maker.server.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    private String email;

    private String password;

    private String answer;

    @OneToMany
    @JoinColumn(name="userIdx")
    private List<Clothes> clothes;

    @Builder
    public Users(String email, String password, String answer) {
        this.email = email;
        this.password = password;
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "Users{" +
                "Id=" + Id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", answer='" + answer + '\'' +
                ", clothes=" + clothes +
                '}';
    }
}
