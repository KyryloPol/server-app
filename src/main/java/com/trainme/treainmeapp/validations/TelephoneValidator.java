package com.trainme.treainmeapp.validations;

import com.trainme.treainmeapp.annotations.ValidTelephone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * It implements the ConstraintValidator interface and overrides the isValid() method
 */
public class TelephoneValidator implements ConstraintValidator<ValidTelephone, String> {
    // A regular expression that validates the telephone number.
    private static final String TELEPHONE_PATTERN = "^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$";

    /**
     * If the telephone number is not null, and the length of the telephone number is greater than or equal to 10, and the
     * telephone number contains only digits, then the telephone number is valid
     *
     * @param telephone The value of the field that is being validated.
     * @param constraintValidatorContext This is the context of the constraint.
     * @return A boolean value.
     */
    @Override
    public boolean isValid(String telephone, ConstraintValidatorContext constraintValidatorContext) {
        return validateTelephone(telephone);
    }

    /**
     * The initialize() function is called when the constraint is first applied to a class
     *
     * @param constraintAnnotation The annotation that was placed on the field.
     */
    @Override
    public void initialize(ValidTelephone constraintAnnotation) {
    }

    /**
     * It takes a string and returns true if the string matches the pattern, false otherwise
     *
     * @param telephone The telephone number to validate.
     * @return A boolean value.
     */
    private boolean validateTelephone(String telephone){
        Pattern pattern = Pattern.compile(TELEPHONE_PATTERN);
        Matcher matcher = pattern.matcher(telephone);
        return  matcher.matches();
    }
}
