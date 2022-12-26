package com.trainme.treainmeapp.facade;

import com.trainme.treainmeapp.domain.Training;
import com.trainme.treainmeapp.dto.TrainingDTO;
import com.trainme.treainmeapp.dto.TrainingForTrainerDTO;
import com.trainme.treainmeapp.dto.TrainingForUserDTO;
import org.springframework.stereotype.Component;

/**
 * It converts a Training object to a TrainingDTO object for user or for trainer.
 */
@Component
public class TrainingFacade {
    /**
     * It takes a training object and returns a trainingDTO object
     *
     * @param training the training object that we want to convert to a DTO
     * @return TrainingForUserDTO
     */
    public TrainingForUserDTO trainingToTrainingDTOForUser(Training training){
        TrainingForUserDTO trainingDTO = new TrainingForUserDTO();
        trainingDTO.setId(training.getId());
        trainingDTO.setTrainerUsername(training.getTrainer().getUsername());
        trainingDTO.setPlace(training.getPlace());
        trainingDTO.setTimeFrom(training.getTimeFrom());
        trainingDTO.setTimeTo(training.getTimeTo());
        return trainingDTO;
    }

    /**
     * It takes a Training object and returns a TrainingForTrainerDTO object
     *
     * @param training the training object that we want to convert to a trainingDTO object.
     * @return TrainingForTrainerDTO
     */
    public TrainingForTrainerDTO trainingToTrainingDTOForTrainer(Training training){
        TrainingForTrainerDTO trainingDTO = new TrainingForTrainerDTO();
        trainingDTO.setId(training.getId());
        trainingDTO.setUserUsername(training.getUser().getUsername());
        trainingDTO.setPlace(training.getPlace());
        trainingDTO.setTimeFrom(training.getTimeFrom());
        trainingDTO.setTimeTo(training.getTimeTo());
        return trainingDTO;
    }
}
