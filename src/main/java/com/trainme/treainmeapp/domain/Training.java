package com.trainme.treainmeapp.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Training is a JPA class that represents training session entity in database
 * and has a many-to-many relationship with User and Trainer entities.
 * Decomposition of many-to-many relationship with User and Trainer
 */
@Data
@Entity
public class Training {
    // It's a JPA annotation that tells the JPA provider to generate the id for the entity.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // It's a JPA annotation that tells the JPA provider to create a column in the database table for the entity.
    @Column(nullable = false)
    private String place;

    // It's a Jackson annotation that tells Jackson to format the date in the specified format.
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    // It's a JPA annotation that tells the JPA provider to create a column in the database table for the entity.
    @Column(nullable = false)
    private LocalDateTime timeFrom;

    // It's a Jackson annotation that tells Jackson to format the date in the specified format.
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    // It's a JPA annotation that tells the JPA provider to create a column in the database table for the entity.
    @Column(nullable = false)
    private LocalDateTime timeTo;

    // It's a JPA annotation that tells the JPA provider to create a column in the database table for the entity.
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    // It's a JPA annotation that tells the JPA provider to create a column in the database table for the entity.
    @ManyToOne(fetch = FetchType.LAZY)
    private Trainer trainer;

    public Training() {}
}
