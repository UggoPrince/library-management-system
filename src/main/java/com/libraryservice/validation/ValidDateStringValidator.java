package com.libraryservice.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ValidDateStringValidator implements ConstraintValidator<ValidDateString, String> {

    private String[] dateFormats;

    @Override
    public void initialize(ValidDateString constraintAnnotation) {
        this.dateFormats = constraintAnnotation.formats();
    }

    @Override
    public boolean isValid(String dateStr, ConstraintValidatorContext context) {
        if (dateStr == null || dateStr.isEmpty()) {
            return true; // @NotNull should be used to validate null values
        }
        for (String dateFormat : dateFormats) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
                LocalDate.parse(dateStr, formatter);
                return true;
            } catch (DateTimeParseException e) {
                // Continue to the next format
            }
        }
        return false;
    }
}

