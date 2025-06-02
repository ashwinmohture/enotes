package com.enotes.enotes_api.util;

import com.enotes.enotes_api.Handler.GenericResponse;
import org.apache.commons.io.FilenameUtils;
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

    public static String getContentType(String originalFileName) {
        String extension = FilenameUtils.getExtension(originalFileName);
        switch (extension){
            case "pdf":
                return "application/pdf";
            case "xlsx":
                return "application/vnd.openxmlformats-officedocument.spreadsheettml.sheet";
            case "txt":
                return "text/plan";
            case "jpeg":
                return "image/jpeg";
            case "png":
                return "image/png";
            default:
                return "appication/octet-strem";
        }
    }
}
