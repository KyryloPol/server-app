package com.trainme.treainmeapp.payload.request;

import com.trainme.treainmeapp.annotations.PasswordMatches;
import com.trainme.treainmeapp.annotations.ValidEmail;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * It's a class that represents a request to sign up a user
 */
@Data
@PasswordMatches
public class SignUpRequest {
    // It's a validation for the email field.
    @Email(message = "It should have email format")
    @NotEmpty(message = "User email cannot be empty")
    @ValidEmail
    private String email;

    // It's a validation for the username field.
    @NotEmpty(message = "Please enter your username")
    private String username;

    // It's a validation for the password field.
    @NotEmpty(message = "Please enter your password")
    @Size(min = 6)
    private String password;
    // It's a field that is used to confirm the password.
    private String confirmPassword;
}
