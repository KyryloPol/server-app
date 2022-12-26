package com.trainme.treainmeapp.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * It's a simple POJO that holds a boolean success flag and a String token
 */
@Data
@AllArgsConstructor
public class JWTTokenSuccessResponse {
    private boolean success;
    private String token;
}

