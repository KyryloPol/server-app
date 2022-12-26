package com.trainme.treainmeapp.validations;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.Map;
//ошибка приходит в binding result из Login или SingUp реквестов
// Содержит и объект ошибки
//Конкретно за фиелд аннотации
//BAD_REQ = 400
//Ошибок нет


/**
 * This class is used to map the validation errors to a map and return a response entity with the map and a bad request
 * status
 */
@Service
public class ResponseErrorValidation {

    /**
     * If there are any errors, create a map of the errors and return a response entity with the map and a bad request
     * status
     *
     * @param result The BindingResult object that contains the validation errors.
     * @return A Response Entity of any Object for error handling
     */
    public ResponseEntity<Object> mapValidationService(BindingResult result) {
        // Checking if there are any errors in the BindingResult object. If there are, it creates a map of the errors and
        // returns a response entity with the map and a bad request status.
        if (result.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();

            // Checking if there are any errors in the BindingResult object. If there are, it creates a map of the errors
            // and returns a response entity with the map and a bad request status.
            if (!CollectionUtils.isEmpty(result.getAllErrors())) {
                for (ObjectError error : result.getAllErrors()) {
                    errorMap.put(error.getCode(), error.getDefaultMessage());
                }
            }

            // This is used to get the field errors.
            for (FieldError error : result.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
        }
        return null;
    }
}

