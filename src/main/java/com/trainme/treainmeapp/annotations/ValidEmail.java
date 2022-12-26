package com.trainme.treainmeapp.annotations;

import com.trainme.treainmeapp.validations.EmailValidator;
import org.springframework.context.annotation.Configuration;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

// A custom annotation that is used to validate the email field.
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
@Documented
public @interface ValidEmail {
    // The default message that will be displayed if the email is not valid.
    String message() default "Invalid Email";

    // A group of constraints that can be applied to a single element.
    Class<?>[] groups() default {};

    // Used to pass additional information about the validation failure.
    Class<? extends Payload>[] payload() default {};
}
