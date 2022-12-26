package com.trainme.treainmeapp.dao;

import com.trainme.treainmeapp.domain.Trainer;
import com.trainme.treainmeapp.domain.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// Creating a repository for the Trainer class. It is a repository that is used to store and retrieve data from the database.
@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long>{
    /**
     * Find a trainer by id, and if found, return it wrapped in an Optional, otherwise return an empty Optional.
     *
     * @param id the id of the trainer to find
     * @return Optional value of type Trainer
     */
    Optional<Trainer> findTrainerById(Long id);

    /**
     * Find all trainers who have a review with the given value.
     *
     * @param value The value of the review.
     * @return A list of trainers.
     */
    List<Trainer> findTrainersByReviewValue(Long value);

    /**
     * Find all trainers, order them by their review value, and return them in descending order.
     *
     * @return A list of trainers ordered by their review value in descending order.
     */
    List<Trainer> findAllByOrderByReviewValueDesc();

}
