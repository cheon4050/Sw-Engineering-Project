package maker.server.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // 400 BAD_REQUEST
    INVALID_TOKEN(BAD_REQUEST, "토큰이 유효하지 않습니다."),


    //404 NOT_FOUND
    USER_NOT_FOUND(NOT_FOUND, "유저가 존재하지 않습니다."),
    PASSWORD_NOT_FOUND(NOT_FOUND, "패스워드 찾기에 실패했습니다."),


    //409 conflict
    DUPLICATE_EMAIL(CONFLICT, "중복되는 이메일 입니다."),
    ;



    private final HttpStatus httpStatus;
    private final String message;
}
