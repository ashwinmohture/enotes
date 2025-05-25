package com.enotes.enotes_api.util;

import com.enotes.enotes_api.dto.CategoryDto;
import com.enotes.enotes_api.exception.ValidationException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Observer;

@Component
public class Validation {
    public void categoryValidation(CategoryDto categoryDto){

        Map<String, Object> error = new LinkedHashMap<>();

        if (ObjectUtils.isEmpty(categoryDto)){
            throw new IllegalArgumentException("category object/json should not be null");
        }else {
            // validation name
            if (ObjectUtils.isEmpty((categoryDto.getName()))){
                error.put("name","name field is not empty or null");
            }else {
                if (categoryDto.getName().length() < 3){
                    error.put("name","Length name min 3");
                }
                if (categoryDto.getName().length() > 100){
                    error.put("name","Length name max 3");
                }
            }
        }
            // validation Descriptions
        if (ObjectUtils.isEmpty((categoryDto.getDescriptions()))){
            error.put("Descriptions","Descriptions field is not empty or null");
        }
        // validation isActive
        if (ObjectUtils.isEmpty((categoryDto.getActive()))){
            error.put("isActive","isActive field is not empty or null");
        }else {
            if (categoryDto.getActive() != Boolean.TRUE.booleanValue()
                    && categoryDto.getActive() != Boolean.FALSE.booleanValue()){
                error.put("isActive","invalid value isActive field");
            }
        }

        if (error.isEmpty()){
            throw new ValidationException(error);
        }
    }

}
