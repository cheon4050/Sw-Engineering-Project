package maker.server.dto.user;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import maker.server.entity.Users;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSaveDto {
    private String email;
    private String password;
    private String answer;

    public Users toEntity(){
        return Users.builder()
                .email(email)
                .password(password)
                .answer(answer)
                .build();
    }
}
