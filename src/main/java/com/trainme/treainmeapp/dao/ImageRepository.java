package com.trainme.treainmeapp.dao;

import com.trainme.treainmeapp.domain.ImageModel;
import com.trainme.treainmeapp.domain.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// Creating a repository for the ImageModel class. It is a repository that is used to store and retrieve data from the database.
@Repository
public interface ImageRepository extends JpaRepository<ImageModel,Long> {
    /**
     * Find the image model that has the trainer id that matches the trainer id passed in.
     *
     * @param trainerId The trainerId of the trainer you want to find the image for.
     * @return Optional value of type ImageModel
     */
    Optional<ImageModel> findByTrainerId(Long trainerId);
}
