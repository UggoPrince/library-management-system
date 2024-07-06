package com.libraryservice.exception;

import com.libraryservice.dto.response.ApiResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponse<Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        error -> error.getField(),
                        error -> error.getDefaultMessage()
                ));
        ApiResponse response = new ApiResponse();
        response.setMessage("Some fields are not valid.");
        response.setSuccess(false);
        response.setError(errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ApiResponse<Object> response = ApiResponse.builder().message(ex.getMessage()).build();
        response.setMessage(ex.getMessage());
        response.setSuccess(false);
        response.setError(null);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<String>> handleBadRequestException(BadRequestException ex) {
        ApiResponse<String> response = new ApiResponse();
        response.setMessage(ex.getMessage());
        response.setSuccess(false);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidDateFormatException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleInvalidDateFormatException(InvalidDateFormatException ex) {
        ApiResponse response = new ApiResponse();
        response.setMessage("Some fields are not valid.");
        response.setSuccess(false);
        Map<String, String> errors = new HashMap<>();
        errors.put(ex.getFieldName(), ex.getErrorMessage());
        response.setError(errors);
        return new ResponseEntity<ApiResponse<Map<String, String>>>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            String propertyPath = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            errors.put(propertyPath, message);
        }
        ApiResponse<Object> response = new ApiResponse<>();
        response.setError(errors);
        response.setMessage("Some fields are not valid.");
        response.setSuccess(false);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ApiResponse response = new ApiResponse();
        response.setSuccess(false);
        if (ex.getMostSpecificCause() != null && ex.getMostSpecificCause().getMessage().contains("duplicate key value")) {
            String errorMessage = ex.getMostSpecificCause().getMessage();
            String field = extractFieldName(errorMessage);
            errors.put(field, field + " already exists");
            response.setError(errors);
            response.setMessage("Duplicate record");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
        errors.put("error", "An unexpected error occurred");
        response.setError(errors);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    private String extractFieldName(String errorMessage) {
        // Assuming the error message contains the field name in quotes like "patrons_email_key"
        int startIndex = errorMessage.indexOf("\"") + 1;
        int endIndex = errorMessage.indexOf("_key\"");
        if (startIndex > 0 && endIndex > startIndex) {
            String[] parts = errorMessage.substring(startIndex, endIndex).split("_");
            return parts.length > 1 ? parts[1] : parts[0];
        }
        return "field";
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponse<Object>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        Map<String, String> errors = new HashMap<>();
        String fieldName = ex.getName();
        String errorMessage = String.format("The parameter '%s' should be of type '%s'", fieldName, ex.getRequiredType().getSimpleName());
        errors.put(fieldName, errorMessage);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Invalid value");
        apiResponse.setError(errors);
        apiResponse.setSuccess(false);
        return ResponseEntity.badRequest().body(apiResponse);
    }


//    @ExceptionHandler(HttpMessageNotReadableException.class)
//    public ResponseEntity<ApiResponse<Object>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
//        ApiResponse<Object> response = ApiResponse.builder().message(ex.getMessage()).build();
//        response.setMessage(ex.getMessage());
//        return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
//    }
}
