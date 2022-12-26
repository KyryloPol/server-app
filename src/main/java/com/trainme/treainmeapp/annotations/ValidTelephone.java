package com.trainme.treainmeapp.annotations;

import com.trainme.treainmeapp.validations.TelephoneValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

// A custom annotation that can be used to validate a telephone number.
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TelephoneValidator.class)
@Documented
public @interface ValidTelephone {
    // The default message that will be displayed if the validation fails.
    String message() default "Invalid Telephone";

    // A way to group constraints together.
    Class<?>[] groups() default {};

    // A way to attach metadata to the constraint.
    Class<? extends Payload>[] payload() default {};
}
