package by.tima_zaytsev.matketplace_parser.common;

import by.tima_zaytsev.matketplace_parser.common.exceptions.RegValidationException;
import by.tima_zaytsev.matketplace_parser.common.exceptions.UserNotFoundException;
import io.minio.errors.MinioException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<UserNotFoundException> handleStudentNotFoundException(UserNotFoundException exception) {
        return ResponseEntity
                .status(exception.getStatus())
                .body(exception);
    }

    @ExceptionHandler({RegValidationException.class})
    public ResponseEntity<RegValidationException> handleRegValidationException(RegValidationException exception) {
        return ResponseEntity
                .status(exception.getStatus())
                .body(exception);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleInternalException(Exception exception){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }

    @ExceptionHandler({SQLException.class, MinioException.class, MailException.class})
    public ResponseEntity<Object> handle(Exception exception){
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(exception.getMessage());
    }
}
