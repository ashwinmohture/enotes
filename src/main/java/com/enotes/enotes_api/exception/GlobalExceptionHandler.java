package com.enotes.enotes_api.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    // Validation message exceptions
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> HandleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        List<ObjectError> allError  = e.getBindingResult().getAllErrors();
        Map<String, Object> error = new LinkedHashMap<>();
         allError.stream().forEach(er->{
             String message = er.getDefaultMessage();
             String field = ((FieldError) (er)).getField();
             error.put(field,message);
         });


        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }



}
