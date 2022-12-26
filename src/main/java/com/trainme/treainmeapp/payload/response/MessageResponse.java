package com.trainme.treainmeapp.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * It's a simple POJO that contains a single String field called message
 */
@Data
@AllArgsConstructor
public class MessageResponse {
    private String message;
}

