package com.trainme.treainmeapp.validations;


import com.trainme.treainmeapp.annotations.ValidEmail;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * It's a class that implements the ConstraintValidator interface, and it's used to validate email addresses
 */
public class EmailValidator implements ConstraintValidator<ValidEmail, String> {
    // It's a regular expression that validates email addresses.
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";


    /**
     * The initialize() function is called when the constraint is first applied to a class
     *
     * @param constraintAnnotation This is the annotation that we are validating.
     */
    @Override
    public void initialize(ValidEmail constraintAnnotation) {

    }

    /**
     * If the email is valid, return true. If the email is not valid, return false
     *
     * @param email The email address to validate.
     * @param constraintValidatorContext This is the context of the constraint.
     * @return A boolean value.
     */
    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return (validateEmail(email));
    }

    /**
     * It takes a string and returns true if the string matches the pattern, false otherwise
     *
     * @param email The email address to validate.
     * @return A boolean value.
     */
    private boolean validateEmail(String email){
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return  matcher.matches();
    }
}
