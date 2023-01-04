package com.trainme.treainmeapp.dto;

import lombok.Data;

/**
 * It's a DTO that contains all the information about a training, plus the username of the trainer
 */
@Data
public class TrainingForUserDTO extends TrainingDTO{
    private String trainerUsername;
}
