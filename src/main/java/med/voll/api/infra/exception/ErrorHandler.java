package med.voll.api.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity handle404Error() {
    return ResponseEntity.notFound().build();
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity handle400Error(MethodArgumentNotValidException ex) {
    var errors = ex.getFieldErrors();


    return ResponseEntity.badRequest().body(errors.stream().map(ValidationErrorData::new).toList());
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity handle400Error(HttpMessageNotReadableException ex) {
    return ResponseEntity.badRequest().body(ex.getMessage());
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity handleBadCredentialsError() {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity handleAuthenticationError() {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failure");
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity handleAccessDeniedError() {
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity handle500Error(Exception ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + ex.getLocalizedMessage());
  }

  private record ValidationErrorData(String field, String message) {
    public ValidationErrorData(FieldError error) {
      this(error.getField(), error.getDefaultMessage());
    }
  }
}
