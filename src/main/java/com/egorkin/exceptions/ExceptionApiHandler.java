package com.egorkin.exceptions;

import com.egorkin.model.datamodel.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;

@Slf4j
@RestControllerAdvice
public class ExceptionApiHandler {

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorMessage> handleIOException(IOException exception) {
        log.error(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ErrorMessage> handleHttpClientErrorException(HttpClientErrorException exception) {
        log.error(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(HttpException.class)
    public ResponseEntity<ErrorMessage> handleHttpException(HttpException exception) {
        log.error(exception.getMessage());
        return ResponseEntity.status(exception.getHttpStatus()).body(new ErrorMessage(exception.getMessage()));
    }
    @ExceptionHandler(IncorrectValueException.class)
    public ResponseEntity<ErrorMessage> handleIncorrectValueException(IncorrectValueException exception) {
        log.error(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage(exception.getMessage()));
    }
}
