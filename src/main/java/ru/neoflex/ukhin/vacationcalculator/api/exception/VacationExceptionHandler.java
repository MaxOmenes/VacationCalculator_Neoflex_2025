package ru.neoflex.ukhin.vacationcalculator.api.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class VacationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> exception(Exception ex, WebRequest request) throws Exception {
        log.error("Error during execution of application", ex);
        return handleException(ex, request);
    }

    @ExceptionHandler({ConstraintViolationException.class, ValidationException.class})
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
        log.error("Validation error", ex);

        Map<String, Object> body = new HashMap<>();
        body.put("error", "Validation Error");

        String errorMessage = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .reduce((a, b) -> a + ", " + b)
                .orElse("Validation failed");

        body.put("message", errorMessage);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
