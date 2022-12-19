package maker.server.Error.Handler;

import lombok.extern.slf4j.Slf4j;
import maker.server.Response.Response;
import maker.server.Error.Exception.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;



@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {UserException.class})
    public ResponseEntity userHandler(UserException e){
        log.info(e.getMessage(), e);
        return new ResponseEntity(new Response(401, "UnAuthorization"), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity globalExceptionHandler(Exception e)   {
        log.info(e.getMessage(), e);
        return new ResponseEntity(new Response(400, "Bad_Request"), HttpStatus.BAD_REQUEST);
    }


}
