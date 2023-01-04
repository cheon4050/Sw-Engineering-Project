package maker.server.error.handler;

import lombok.extern.slf4j.Slf4j;
import maker.server.error.ErrorResponse;
import maker.server.error.entity.AuthException;
import maker.server.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;



@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {AuthException.class})
    public ResponseEntity authHandler(AuthException e){
        log.info(e.getMessage(), e);
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity globalExceptionHandler(Exception e)   {
        log.info(e.getMessage(), e);
        return new ResponseEntity(new Response(400, "Bad_Request"), HttpStatus.BAD_REQUEST);
    }


}
