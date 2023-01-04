package com.trainme.treainmeapp.payload.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * It's a POJO that contains two fields, username and password, and it's annotated with @Data
 */
@Data
public class LoginRequest {

    // It's a validation annotation that checks if the username field is empty.
    @NotEmpty(message = "Username cannot be empty")
    private String username;

    // It's a validation annotation that checks if the password field is empty.
    @NotEmpty(message = "Password cannot be empty")
    private String password;

}
