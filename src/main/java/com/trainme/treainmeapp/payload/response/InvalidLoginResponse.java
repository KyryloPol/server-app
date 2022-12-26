package com.trainme.treainmeapp.payload.response;


import lombok.Getter;

/**
 * It's a class that has two String fields, username and password, and a constructor that sets the values of those fields
 * to "Invalid Username" and "Invalid Password" respectively
 */
@Getter
public class InvalidLoginResponse {

    private final String username;
    private final String password;

    public InvalidLoginResponse(){
        this.username = "Invalid Username";
        this.password = "Invalid Password";
    }
}
