package com.khajana.setting.exception;

import com.khajana.setting.dto.productRequisition.ApiResponse;
import com.khajana.setting.dto.productRequisition.CustomKeyValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<CustomKeyValue> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.add(new CustomKeyValue(fieldName, errorMessage));
        });
        ApiResponse apiResponse = new ApiResponse(400, "Validation Error", errors, null);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }
}
