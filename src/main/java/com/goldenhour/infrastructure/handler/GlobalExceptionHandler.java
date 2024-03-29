package com.goldenhour.infrastructure.handler;

import com.goldenhour.infrastructure.handler.exceptions.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String TIMESTAMP = ZonedDateTime.now(ZoneId.of("CET")).toString();

    @ExceptionHandler
    public ResponseEntity<Object> handleMethodArgumentException(MethodArgumentNotValidException e) {
        Map<String, Object> errors = new HashMap<>();
        HttpStatus status = HttpStatus.BAD_REQUEST;

        e.getBindingResult()
                .getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        errors.put("status", status);
        errors.put("timestamp", TIMESTAMP);

        return new ResponseEntity<>(errors, status);
    }

    @ExceptionHandler(value = {BaseException.class})
    public ResponseEntity<ApiErrorResponse> handleApiRequestException(BaseException e) {
        HttpStatus status = e.getClass()
                .getAnnotation(ResponseStatus.class)
                .value();

        ApiErrorResponse response = new ApiErrorResponse(e.getMessage(), status, TIMESTAMP);

        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<ApiErrorResponse> handleInvalidTypeException(Exception e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "";

        if (e instanceof MethodArgumentTypeMismatchException) {
            message = "The provided path argument is not a valid type";
        } else if (e instanceof HttpMessageNotReadableException) {
            message = "Entered value is not a valid type";
        }

        ApiErrorResponse response = new ApiErrorResponse(message, status, TIMESTAMP);

        return new ResponseEntity<>(response, status);
    }

}
