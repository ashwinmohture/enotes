package com.enotes.enotes_api.exception;

import org.modelmapper.internal.bytebuddy.implementation.bind.annotation.Super;

public class ExistDataException extends RuntimeException{
    public ExistDataException(String message){
        super(message);
    }
}
