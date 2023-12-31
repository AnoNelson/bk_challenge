package com.challenge.challenge.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Service
public class CustomErrorGenerator {
        public ResponseEntity<?> getErrors(BindingResult bindingResult){
            if(bindingResult.hasErrors()) {
                Map<String, String> errors = new HashMap<>();
                for(FieldError er:bindingResult.getFieldErrors()){
                    errors.put(er.getField(),er.getDefaultMessage());
                }
                return new ResponseEntity<Map<String, String>>(errors, HttpStatus.BAD_REQUEST);
            }else{
                return null;
            }
        }
}
