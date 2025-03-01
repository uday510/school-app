package com.app.school.annotation;

import com.app.school.validations.PasswordStrengthValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordStrengthValidator.class)
@Target( {ElementType.METHOD, ElementType.FIELD } )
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordValidator {
    String message() default "Please enter a valid password";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
