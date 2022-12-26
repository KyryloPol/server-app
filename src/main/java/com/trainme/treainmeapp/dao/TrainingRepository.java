package com.trainme.treainmeapp.dao;

import com.trainme.treainmeapp.domain.Trainer;
import com.trainme.treainmeapp.domain.Training;
import com.trainme.treainmeapp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

// Creating a repository for the ImageModel class. It is a repository that is used to store and retrieve data from the database.
@Repository
public interface TrainingRepository extends JpaRepository<Training, Long>{
    /**
     * Find all trainings for a given user.
     *
     * @param user The user whose trainings you want to find.
     * @return List of all trainings for a given user.
     */
    List<Training> findAllByUser(User user);

    /**
     * Find all trainings where the trainer is the given trainer.
     *
     * @param trainer The trainer whose trainings you want to find.
     * @return List of all trainings by trainer
     */
    List<Training> findAllByTrainer(Trainer trainer);

    /**
     * Find all the training records in the database.
     *
     * @return A list of all the trainings in the database.
     */
    List<Training> findAll();

    /**
     * Find a training by its id, and return it wrapped in an Optional.
     *
     * @param trainerId The id of the trainer to be searched for.
     * @return Optional value of type Training
     */
    Optional<Training> findById(Long trainerId);

    /**
     * Find a training by its id and the id of the user who owns it.
     *
     * @param trainingId The id of the training you want to find.
     * @param userId The id of the user who created the training.
     * @return Optional value of type Training
     */
    Optional<Training> findByIdAndUserId(Long trainingId, Long userId);

    /**
     * > Check if there is a training with the given place, timeFrom, timeTo, trainer and user
     *
     * @param place the place where the training will be held
     * @param timeFrom the start time of the training
     * @param timeTo the end time of the training
     * @param trainer Trainer
     * @param user the user who is trying to create a training
     * @return boolean
     */
    boolean existsTrainingByPlaceAndTimeFromAndTimeToAndTrainerAndUser(String place,
                                                                       LocalDateTime timeFrom,
                                                                       LocalDateTime timeTo,
                                                                       Trainer trainer,
                                                                       User user);

    /**
     * > Check if there is a training with the given timeFrom, timeTo and trainer
     *
     * @param timeFrom the start time of the training
     * @param timeTo the end time of the training
     * @param trainer the trainer who is assigned to the training
     * @return boolean
     */
    boolean existsTrainingByTimeFromAndTimeToAndTrainer(LocalDateTime timeFrom, LocalDateTime timeTo, Trainer trainer);

}
