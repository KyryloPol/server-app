package com.trainme.treainmeapp.annotations;

import com.trainme.treainmeapp.validations.PasswordMatchesValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

// A custom annotation that is used to validate the password and confirm password fields.
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchesValidator.class)
@Documented
public @interface PasswordMatches {
    // The default message that will be displayed if the validation fails.
    String message() default "Password do not match";

    // A group of constraints.
    Class<?>[] groups() default {};

    // Used to pass additional information about the validation failure.
    Class<? extends Payload>[] payload() default {};
}