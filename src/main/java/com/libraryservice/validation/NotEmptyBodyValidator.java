package com.libraryservice.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotEmptyBodyValidator implements ConstraintValidator<NotEmptyBody, Object> {
    @Override
    public void initialize(NotEmptyBody constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return true;
    }
}
