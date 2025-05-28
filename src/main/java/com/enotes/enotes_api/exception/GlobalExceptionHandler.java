package com.enotes.enotes_api.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> HandleException(Exception e){
//        log.error("GlobalExceptionHandler :: HandleException :: ",e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> HandleNullPointerException(Exception e){
//        log.error("GlobalExceptionHandler :: HandleNullPointerException :: ",e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> HandleResourceNotFoundException(Exception e){
//        log.error("GlobalExceptionHandler :: HandleResourceNotFoundException :: ",e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> HandleValidationException(ValidationException e){
        return new ResponseEntity<>(e.getError(), HttpStatus.BAD_REQUEST);
    }

    // exist category validation handler
    @ExceptionHandler(ExistDataException.class)
    public ResponseEntity<?> HandleExistDataException(ExistDataException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    // exist category validation handler
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> HandleHttpMessageNotReadableException(HttpMessageNotReadableException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }





}
