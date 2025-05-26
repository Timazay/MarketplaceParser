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
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponse> handleBadRequestException(ApplicationException exception) {
        return ResponseEntity
                .status(400)
                .body(new ExceptionResponse(
                        exception.getMessage(),
                        exception.getMetadata()
                ));
    }
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ExceptionResponse> handleUnauthorizedException(ApplicationException exception) {
        return ResponseEntity
                .status(401)
                .body(new ExceptionResponse(
                        exception.getMessage(),
                        exception.getMetadata()
                ));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundException(ApplicationException exception) {
        return ResponseEntity
                .status(404)
                .body(new ExceptionResponse(
                        exception.getMessage(),
                        exception.getMetadata()
                ));
    }

    @ExceptionHandler({SQLException.class, MinioException.class, MailException.class})
    public ResponseEntity<Object> handleServiceException(Exception exception) {
        return ResponseEntity
                .status(500)
                .body(exception.getMessage());
    }
}
