package com.flywithus.validation;

import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ValidationService {

    private Validator validator;

    public ValidationService(Validator validator) {
        this.validator = validator;
    }

    public void validate(Object o) {
        validate(o, Default.class);
    }

    public void validate(Object o, Class<?>... group) {
        Set<ConstraintViolation<Object>> result = validator.validate(o, group);
        if (result.isEmpty()) {
            return;
        }
        String messages = result.stream()
                .map(constraint -> String.format("%s %s", constraint.getPropertyPath(), constraint.getMessage()))
                .collect(Collectors.joining("\n"));
        throw new ValidationException(messages);
    }
}
