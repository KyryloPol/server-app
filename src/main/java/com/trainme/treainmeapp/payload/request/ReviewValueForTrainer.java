package com.trainme.treainmeapp.payload.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * It's a class that contains the data that is needed to create a review for a trainer
 */
@Data
public class ReviewValueForTrainer {
    // It's a validation annotation that checks if the trainingId is not null.
    @NotNull(message = "Training Id cannot be null")
    private Long trainingId;
    // It's a validation annotation that checks if the trainerUsername is not empty.
    @NotEmpty(message = "Trainer Username cannot be empty")
    private String trainerUsername;
    // It's a validation annotation that checks if the reviewValue is not null.
    @NotNull(message = "Review Value cannot be null")
    private Long reviewValue;
}
