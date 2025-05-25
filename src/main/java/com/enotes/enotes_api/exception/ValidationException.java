package com.enotes.enotes_api.exception;

import java.util.Map;
import java.util.Objects;

public class ValidationException extends RuntimeException{
    private Map<String, Object> error;

    public ValidationException(Map<String,Object> error){
        super("Validation failed");
        this.error = error;
    }
    public Map<String,Object> getError(){
        return error;
    }
}
