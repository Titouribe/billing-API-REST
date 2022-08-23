package com.billing.app.controllers;

import com.billing.app.exceptions.RequestException;
import com.billing.app.model.dtos.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, ErrorDTO>> validateExceptionHandler(MethodArgumentNotValidException exception){

        Map<String, ErrorDTO> errorDTOMap = new HashMap<>();

        exception.getBindingResult().getAllErrors().forEach(error -> {
            ErrorDTO errorDTO = ErrorDTO
                    .builder()
                    .code(error.getCode())
                    .message(error.getDefaultMessage())
                    .build();
            errorDTOMap.put("error", errorDTO);
        });

        return new ResponseEntity<>(errorDTOMap, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = RequestException.class)
    public ResponseEntity<ErrorDTO> requestExceptionHandler(RequestException exception){

            ErrorDTO errorDTO = ErrorDTO
                    .builder()
                    .code(exception.getCode())
                    .message(exception.getMessage())
                    .build();

        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<Map<String, ErrorDTO>> constraintExceptionHandler(ConstraintViolationException exception){

        Map<String, ErrorDTO> errorDTOMap = new HashMap<>();

        exception.getConstraintViolations().forEach(error -> {
            ErrorDTO errorDTO = ErrorDTO
                    .builder()
                    .code(error.getPropertyPath().toString())
                    .message(error.getMessageTemplate())
                    .build();
            errorDTOMap.put("error", errorDTO);
        });

        return new ResponseEntity<>(errorDTOMap, HttpStatus.BAD_REQUEST);
    }
}
