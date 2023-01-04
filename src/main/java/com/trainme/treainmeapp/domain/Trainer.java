package com.trainme.treainmeapp.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Trainer is a JPA class that represents entity in database and has a one-to-many relationship with ImageModel and Training
 */
@Data
@Entity
public class Trainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    private String telephone;

    @Column(nullable = false)
    private Long reviewValue;

    private String priceForOneTraining;

    private String priceForMonthTraining;

    @JsonFormat(pattern = "yyyy-mm-dd")
    private LocalDate dateOfBirthday;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "trainerId", orphanRemoval = true)
    private List<ImageModel> imageModels = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "trainer", orphanRemoval = true)
    private List<Training> trainings = new ArrayList<>();

}
