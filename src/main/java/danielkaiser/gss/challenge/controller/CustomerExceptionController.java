package danielkaiser.gss.challenge.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Log4j2
public class CustomerExceptionController {

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<Object> exception(DataIntegrityViolationException exception) {
        log.error("Input data is not valid.", exception);
        return new ResponseEntity<>("Input data is not valid.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<String> exception(RuntimeException exception) {
        log.error("Exception occurred", exception);
        return new ResponseEntity<>("Exception occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

