package com.trainme.treainmeapp.validations;


import com.trainme.treainmeapp.annotations.PasswordMatches;
import com.trainme.treainmeapp.payload.request.SignUpRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * It implements the ConstraintValidator interface and overrides the isValid() method
 */
public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    /**
     * The initialize function is called when the annotation is first used
     *
     * @param constraintAnnotation The annotation that was placed on the field.
     */
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {

    }

    /**
     * If the password and confirm password fields are equal, return true
     *
     * @param obj The object to validate.
     * @param constraintValidatorContext This is the context in which the constraint is evaluated.
     * @return A boolean value.
     */
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {
        SignUpRequest userSignupRequest = (SignUpRequest) obj;
        return userSignupRequest.getPassword().equals(userSignupRequest.getConfirmPassword());

    }
}

