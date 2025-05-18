package by.tima_zaytsev.MarketplaceParser.common;

import by.tima_zaytsev.MarketplaceParser.common.exceptions.*;
import io.minio.errors.MinioException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({UserNotFoundException.class, RegValidationException.class, SendMsgException.class,
    JwtExpirationException.class})
    public ResponseEntity<ExceptionResponse> handleStudentNotFoundException(RegistrationException exception) {
        return ResponseEntity
                .status(exception.getStatus())
                .body(new ExceptionResponse(exception.getMessage(),exception.getMetadata()));
    }
    @ExceptionHandler({SQLException.class, MinioException.class, MailException.class})
    public ResponseEntity<Object> handle(Exception exception){
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(exception.getMessage());
    }
}
