package com.trainme.treainmeapp.facade;

import com.trainme.treainmeapp.domain.Trainer;
import com.trainme.treainmeapp.dto.TrainerDTO;
import org.springframework.stereotype.Component;

/**
 * It converts a Trainer object to a TrainerDTO object
 */
@Component
public class TrainerFacade {
    public TrainerDTO trainerToTrainerDTO(Trainer trainer){
        TrainerDTO trainerDTO = new TrainerDTO();
        trainerDTO.setId(trainer.getId());
        trainerDTO.setUsername(trainer.getUsername());
        trainerDTO.setEmail(trainer.getEmail());
        trainerDTO.setReviewValue(trainer.getReviewValue());
        trainerDTO.setTelephone(trainer.getTelephone());
        trainerDTO.setDateOfBirthday(trainer.getDateOfBirthday());
        trainerDTO.setPriceForMonthTraining(trainer.getPriceForMonthTraining());
        trainerDTO.setPriceForOneTraining(trainer.getPriceForOneTraining());
        return trainerDTO;
    }
}
