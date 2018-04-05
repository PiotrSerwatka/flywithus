package com.flywithus.validation.validator.airport;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AirportExistValidator.class)
@Documented
public @interface AirportExist {

    String message() default "airport not found";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
