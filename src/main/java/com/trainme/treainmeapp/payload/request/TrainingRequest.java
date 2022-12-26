package com.trainme.treainmeapp.payload.request;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * It's a class that represents a request to create a new training
 */
@Data
public class TrainingRequest {
    // It's a validation annotation that checks if the trainerId is not null.
    @NotNull(message = "Trainer ID cannot be null")
    private Long trainerId;
    // It's a validation annotation that checks if the place is not empty.
    @NotEmpty(message = "Place cannot be empty")
    private String place;
    // It's a validation annotation that checks if the timeFrom is not empty.
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime timeFrom;
    // It's a validation annotation that checks if the timeTo is not empty.
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime timeTo;
}
