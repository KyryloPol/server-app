package com.trainme.treainmeapp.dto;

import lombok.Data;

/**
 * It's a DTO that extends the TrainingDTO class and adds a userUsername field
 */
@Data
public class TrainingForTrainerDTO extends TrainingDTO{
    private String userUsername;
}
