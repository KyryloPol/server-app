package com.trainme.treainmeapp.services;

import com.trainme.treainmeapp.dao.TrainerRepository;
import com.trainme.treainmeapp.dao.UserRepository;
import com.trainme.treainmeapp.domain.Trainer;
import com.trainme.treainmeapp.domain.User;
import com.trainme.treainmeapp.domain.enums.EMembership;
import com.trainme.treainmeapp.dto.TrainerDTO;
import com.trainme.treainmeapp.exceptions.NotAllowedException;
import com.trainme.treainmeapp.exceptions.TrainerNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

/**
 * This class is responsible for all the business logic related to the Trainer entity
 */
@Service
public class TrainerService {
    public static final Logger LOG = LoggerFactory.getLogger(Trainer.class);

    public final UserRepository userRepository;
    private final TrainerRepository trainerRepository;

    @Autowired
    public TrainerService(TrainerRepository trainerRepository, UserRepository userRepository) {
        this.trainerRepository = trainerRepository;
        this.userRepository = userRepository;
    }

    /**
     * > This function creates a new trainer, but only if the user is an admin
     *
     * @param trainerDTO This is the object that is passed in from the front end. It contains the information that the user
     * has entered.
     * @param principal The principal object is used to get the currently logged in user.
     * @return Trainer
     */
    public Trainer createTrainer(TrainerDTO trainerDTO, Principal principal){
        User user = getUserByPrincipal(principal);
        if (!user.getMembership().contains(EMembership.ROLE_ADMIN)) {
            throw new NotAllowedException("You dont have permission to do that.");
        }

        Trainer trainer = new Trainer();
        trainer.setEmail(trainerDTO.getEmail());
        trainer.setUsername(trainerDTO.getUsername());
        trainer.setTelephone(trainerDTO.getTelephone());
        trainer.setReviewValue(0L);
        LOG.info("Saving Trainer {}", trainer.getUsername());
        return trainerRepository.save(trainer);
    }

    /**
     * It updates the trainer.
     *
     * @param trainerDTO TrainerDTO object that contains the data that will be updated.
     * @param trainerId The id of the trainer you want to update.
     * @param principal The principal object is used to get the currently logged in user.
     * @return Trainer
     */
    public Trainer updateTrainer(TrainerDTO trainerDTO, Long trainerId, Principal principal){
        User user = getUserByPrincipal(principal);
        if (!user.getMembership().contains(EMembership.ROLE_ADMIN)) {
            throw new NotAllowedException("You dont have permission to do that.");
        }
        Trainer trainer = trainerRepository.findTrainerById(trainerId)
                .orElseThrow(()-> new TrainerNotFoundException("Trainer cannot found"));
        trainer.setUsername(trainerDTO.getUsername());
        trainer.setReviewValue((long)Math.floor((trainerDTO.getReviewValue() + trainer.getReviewValue()) / 2));
        trainer.setTelephone(trainerDTO.getTelephone());
        trainer.setPriceForMonthTraining(trainerDTO.getPriceForMonthTraining());
        trainer.setPriceForOneTraining(trainerDTO.getPriceForOneTraining());
        trainer.setDateOfBirthday(trainerDTO.getDateOfBirthday());
        LOG.info("Update Trainer {}", trainer.getUsername());
        return trainerRepository.save(trainer);
    }

    public Trainer getTrainerById(Long trainerId){
        return trainerRepository.findTrainerById(trainerId)
                .orElseThrow(()-> new TrainerNotFoundException("Trainer cannot found"));
    }

    /**
     * > If the user is not an admin, throw an exception. Otherwise, delete the trainer
     *
     * @param trainerId The id of the trainer to be deleted.
     * @param principal This is the object that contains the information about the user who is currently logged in.
     */
    public void deleteTrainer(Long trainerId, Principal principal){
        User user = getUserByPrincipal(principal);
        if (!user.getMembership().contains(EMembership.ROLE_ADMIN)) {
            throw new NotAllowedException("You dont have permission to do that.");
        }
        Trainer trainer = trainerRepository.findTrainerById(trainerId)
                        .orElseThrow(()-> new TrainerNotFoundException("Trainer cannot found"));
        trainerRepository.delete(trainer);
    }

    /**
     * Find all trainers who have a review with a value of the value parameter.
     *
     * @param value The value of the review.
     * @return A list of trainers with the specified review value.
     */
    public List<Trainer> getTrainersByValue(Long value){
        return trainerRepository.findTrainersByReviewValue(value);
    }

    /**
     * Get all trainers, ordered by their review value in descending order for main CRUD request:
     * Who will be the best coach of the month by the number of trainings conducted, and who can demand a salary increase.
     *
     * @return A list of trainers ordered by their review value in descending order.
     */
    public List<Trainer> getAllTrainerDesc(){
        return trainerRepository.findAllByOrderByReviewValueDesc();
    }

    /**
     * Get the trainer with the highest review value.
     *
     * @return The best trainer
     */
    public Trainer getTheBestTrainer(){
        return trainerRepository.findAllByOrderByReviewValueDesc().get(0);
    }

    /**
     * Get the username from the principal, find the user in the database, and throw an exception if the user is not found.
     *
     * @param principal This is the user object that is currently logged in.
     * @return A User object
     */
    private User getUserByPrincipal(Principal principal){
        String username = principal.getName();
        return userRepository.findUserByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("Username not found with username: " + username));
    }
}