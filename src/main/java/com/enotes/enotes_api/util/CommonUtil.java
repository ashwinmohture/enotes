package com.enotes.enotes_api.util;

import com.enotes.enotes_api.Handler.GenericResponse;
import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CommonUtil {

    public static ResponseEntity<?> createBuildResponse(Object data, HttpStatus status){
        GenericResponse response = GenericResponse.builder()
                .status("success")
                .message("success")
                .data(data)
                .responseStatus(status)
                .build();

        return response.create();
    }

    public static ResponseEntity<?> createBuildResponseMessage(String message, HttpStatus status){
        GenericResponse response = GenericResponse.builder()
                .status("success")
                .message(message)
                .responseStatus(status)
                .build();

        return response.create();
    }

    public static ResponseEntity<?> createErrorResponse(Object data, HttpStatus status){
        GenericResponse response = GenericResponse.builder()
                .status("Failed")
                .message("Failed")
                .data(data)
                .responseStatus(status)
                .build();

        return response.create();
    }

    public static ResponseEntity<?> createErrorResponseMessage(String message, HttpStatus status){
        GenericResponse response = GenericResponse.builder()
                .status("Failed")
                .message(message)
                .responseStatus(status)
                .build();

        return response.create();
    }
}
