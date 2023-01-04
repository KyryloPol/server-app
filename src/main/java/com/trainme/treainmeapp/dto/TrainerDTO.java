package com.trainme.treainmeapp.dto;

import com.trainme.treainmeapp.annotations.ValidTelephone;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;

/**
 * It's a DTO that contains all the fields that are needed to create a new Trainer
 */
@Data
public class TrainerDTO {
    private Long id;
    private String username;
    private Long reviewValue;
    private String email;
    @Nullable
    @ValidTelephone
    private String telephone;
    private String priceForOneTraining;
    private String priceForMonthTraining;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateOfBirthday;
}
