package me.mattpitts.jobber.application.rest.x;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandlers {
    @ExceptionHandler(produces = "application/json")
    public ResponseEntity<ErrorDetail> handleJson(IllegalArgumentException exc) {
        return ResponseEntity.badRequest().body(ErrorDetail.builder().fromException(exc).build());
    }

    @ExceptionHandler(produces = "application/json")
    public ResponseEntity<ErrorDetail> handleJson(Exception exc) {
        return ResponseEntity.internalServerError().body(ErrorDetail.builder().fromException(exc).build());
    }
}
