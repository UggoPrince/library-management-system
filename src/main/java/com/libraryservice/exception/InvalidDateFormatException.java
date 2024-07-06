package com.libraryservice.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;

@Getter
public class InvalidDateFormatException extends JsonProcessingException {
    private final String fieldName;
    private final String errorMessage;

    public InvalidDateFormatException(String fieldName, String errorMessage) {
        super(errorMessage);
        this.fieldName = fieldName;
        this.errorMessage = errorMessage;
    }

}
