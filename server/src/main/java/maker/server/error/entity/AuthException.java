package maker.server.error.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import maker.server.error.ErrorCode;

@Getter
@AllArgsConstructor
public class AuthException extends RuntimeException{
    private final ErrorCode errorCode;
}
