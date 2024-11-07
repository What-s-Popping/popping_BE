package com.popping.global.advice;

import com.popping.global.exception.SignInRequiredException;
import com.popping.global.responseform.ResponseStatus;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class ValidExceptionHandler {
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ResponseStatus> noSuchElementException(NoSuchElementException e) {
        return ResponseEntity.badRequest()
                .body(
                        ResponseStatus.builder()
                                .httpCode(HttpStatus.BAD_REQUEST.value())
                                .httpStatus(HttpStatus.BAD_REQUEST)
                                .responseMessage(e.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ResponseStatus> entityNotFoundException(Exception e) {
        return ResponseEntity.badRequest()
                .body(
                        ResponseStatus.builder()
                                .httpCode(HttpStatus.BAD_REQUEST.value())
                                .httpStatus(HttpStatus.BAD_REQUEST)
                                .responseMessage(e.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<ResponseStatus> unsupportedOperationException(Exception e) {
        return ResponseEntity.badRequest()
                .body(
                        ResponseStatus.builder()
                                .httpCode(HttpStatus.BAD_REQUEST.value())
                                .httpStatus(HttpStatus.BAD_REQUEST)
                                .responseMessage(e.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseStatus> ConstraintViolationException1(Exception e) {
        return ResponseEntity.badRequest()
                .body(
                        ResponseStatus.builder()
                                .httpCode(HttpStatus.BAD_REQUEST.value())
                                .httpStatus(HttpStatus.BAD_REQUEST)
                                .responseMessage(e.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseStatus> MethodArgumentNotValidException(Exception e) {
        return ResponseEntity.badRequest()
                .body(
                        ResponseStatus.builder()
                                .httpCode(HttpStatus.BAD_REQUEST.value())
                                .httpStatus(HttpStatus.BAD_REQUEST)
                                .responseMessage(e.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseStatus> IllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest()
                .body(
                        ResponseStatus.builder()
                                .httpCode(HttpStatus.BAD_REQUEST.value())
                                .httpStatus(HttpStatus.BAD_REQUEST)
                                .responseMessage(e.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(SignInRequiredException.class)
    public ResponseEntity<ResponseStatus> SignInRequiredException(SignInRequiredException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(
                        ResponseStatus.builder()
                                .httpCode(HttpStatus.FORBIDDEN.value())
                                .httpStatus(HttpStatus.FORBIDDEN)
                                .responseMessage(e.getMessage())
                                .build()
                );
    }
}
