package com.trainme.treainmeapp.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

/**
 * It's a DTO that represents a training
 */
@Data
public class TrainingDTO {
    private Long id;
    private String place;
    private LocalDateTime timeTo;
    private LocalDateTime timeFrom;
}
