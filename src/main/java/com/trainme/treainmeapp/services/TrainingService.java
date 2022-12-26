package com.trainme.treainmeapp.services;

import com.trainme.treainmeapp.dao.TrainerRepository;
import com.trainme.treainmeapp.dao.TrainingRepository;
import com.trainme.treainmeapp.dao.UserRepository;
import com.trainme.treainmeapp.domain.Trainer;
import com.trainme.treainmeapp.domain.Training;
import com.trainme.treainmeapp.domain.User;
import com.trainme.treainmeapp.dto.TrainingDTO;
import com.trainme.treainmeapp.exceptions.IdenticalTrainingExists;
import com.trainme.treainmeapp.exceptions.NotAllowedException;
import com.trainme.treainmeapp.exceptions.TrainerNotFoundException;
import com.trainme.treainmeapp.exceptions.TrainingNotFoundException;
import com.trainme.treainmeapp.payload.request.TrainingRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * This class is responsible for all the business logic related to the training
 */
@Service
public class TrainingService {
    public static final Logger LOG = LoggerFactory.getLogger(TrainingService.class);
    private final TrainingRepository trainingRepository;
    private final UserRepository userRepository;
    private final TrainerRepository trainerRepository;

    @Autowired
    public TrainingService(TrainingRepository trainingRepository, UserRepository userRepository, TrainerRepository trainerRepository) {
        this.trainingRepository = trainingRepository;
        this.userRepository = userRepository;
        this.trainerRepository = trainerRepository;
    }

    /**
     * It creates a training for a user and a trainer.
     *
     * @param trainingRequest This is the object that contains the data that the user sends to the server.
     * @param principal The principal object is used to get the currently logged in user.
     * @return Training object
     */
    public Training createTraining(TrainingRequest trainingRequest, Principal principal){
        User user = getUserByPrincipal(principal);
        Trainer trainer = trainerRepository.findById(trainingRequest.getTrainerId())
                .orElseThrow(()-> new TrainerNotFoundException("Trainer cannot found"));

        // This is a validation method that checks if the user is allowed to create a training with the given time.
        if(validateTimeStampToTrainer(trainingRequest.getTimeFrom(), trainingRequest.getTimeTo(), trainer, user)){
            throw new NotAllowedException("You are not allowed to do this, please change your time data");
        }

        Training training = new Training();
        training.setUser(user);
        training.setPlace(trainingRequest.getPlace());
        training.setTrainer(trainer);
        training.setTimeTo(trainingRequest.getTimeTo());
        training.setTimeFrom(trainingRequest.getTimeFrom());
        // This is a validation method that checks if the user is allowed to create a training with the given time.
        if(trainingRepository.existsTrainingByPlaceAndTimeFromAndTimeToAndTrainerAndUser(
                training.getPlace(), training.getTimeFrom(), training.getTimeTo(), trainer, user
        )){
            LOG.error("Identical training is already exists");
            throw new IdenticalTrainingExists("Identical training is already exists");
        }

        LOG.info("Saving Training For User {} And Trainer {}", user.getUsername(), trainer.getUsername());

        return trainingRepository.save(training);
    }

    /**
     * "Get the training with the given id, if it exists and belongs to the user making the request."
     *
     * The first thing we do is get the user making the request. We do this by calling the getUserByPrincipal() function we
     * created earlier
     *
     * @param trainingId The id of the training to be retrieved.
     * @param principal This is the user who is logged in.
     * @return Training object
     */
    public Training getTraining(Long trainingId, Principal principal){
        User user = getUserByPrincipal(principal);
        return trainingRepository.findByIdAndUserId(trainingId, user.getId())
                .orElseThrow(()->new TrainingNotFoundException("Training not found"));
    }

    /**
     * It updates the training.
     *
     * @param trainingDTO This is the object that contains the data that the user wants to change.
     * @param trainingId The id of the training you want to update
     * @param principal The principal object is used to get the currently logged-in user.
     * @return Training
     */
    public Training updateTraining(TrainingDTO trainingDTO, Long trainingId ,Principal principal){
        User user = getUserByPrincipal(principal);
        Training training = trainingRepository.findByIdAndUserId(trainingId, user.getId())
                .orElseThrow(()->new TrainingNotFoundException("Training not found"));
        Trainer trainer = trainerRepository.findTrainerById(training.getTrainer().getId())
                .orElseThrow(()-> new TrainerNotFoundException("Trainer cannot be found"));
        // This is a validation method that checks if the user is allowed to create a training with the given time.
        if(validateTimeStampToTrainer(trainingDTO.getTimeFrom(), trainingDTO.getTimeTo(), trainer, user)){
            throw new NotAllowedException("You are not allowed to do this, please change your time data");
        }
        training.setPlace(trainingDTO.getPlace());
        training.setTimeTo(trainingDTO.getTimeTo());
        training.setTimeFrom(trainingDTO.getTimeFrom());
        LOG.info("Update Training For User {} And Trainer {}", user.getUsername(), training.getTrainer().getUsername());
        return trainingRepository.save(training);
    }

    /**
     * > This function returns a list of all trainings for a given trainer
     *
     * @param trainerId The id of the trainer whose trainings we want to retrieve.
     * @return List of Trainings
     */
    public List<Training> getAllTrainingsForTrainer(Long trainerId){
        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(()-> new TrainerNotFoundException("Trainer cannot found"));
        return trainingRepository.findAllByTrainer(trainer);
    }

    /**
     * "Get all the trainings for the user that is currently logged-in."
     *
     * The first line of the function is a comment. Comments are ignored by the compiler. They are used to explain what the
     * code does
     *
     * @param principal This is the user who is logged in.
     * @return A list of all the trainings for a user.
     */
    public List<Training> getAllTrainingsForUser(Principal principal){
        User user = getUserByPrincipal(principal);
        return trainingRepository.findAllByUser(user);
    }

    /**
     * > Delete a training by id and user id
     *
     * @param trainingId The id of the training to be deleted.
     * @param principal This is the user who is logged in.
     */
    public void deleteTraining(Long trainingId, Principal principal){
        Training training = trainingRepository.findByIdAndUserId(trainingId,getUserByPrincipal(principal).getId())
                .orElseThrow(()->new TrainingNotFoundException("Training not found"));;
        trainingRepository.delete(training);
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

    /**
     * It validates the time of the training.
     *
     * @param timeReqFrom the time the user wants to start the training
     * @param timeReqTo the end time of the training
     * @param trainer Trainer
     * @param user the user who is creating the training
     * @return boolean
     */
    private boolean validateTimeStampToTrainer(LocalDateTime timeReqFrom,
                                               LocalDateTime timeReqTo,
                                               Trainer trainer,
                                               User user){
        if(trainingRepository.existsTrainingByTimeFromAndTimeToAndTrainer(timeReqFrom, timeReqTo, trainer)){
            LOG.error("You cannot create/update training with this time and Trainer because Trainer has already training at this time");
            return true;
        }

        if(!validateToTrainer(timeReqFrom,timeReqTo,trainer) || !validateToUser(timeReqFrom,timeReqTo,user)){
            return true;
        }

        if(timeReqFrom.equals(timeReqTo)){
            return true;
        }

        long hours = ChronoUnit.HOURS.between(timeReqFrom, timeReqTo);
        long days = ChronoUnit.DAYS.between(timeReqFrom, timeReqTo);
        if(timeReqFrom.isAfter(timeReqTo)
                || timeReqFrom.isBefore(LocalDateTime.now())){
            LOG.error("You cannot create/update training with undefined dates");
            return true;
        }
        if(hours > 12 || days >= 1){
            LOG.error("You cannot create/update so long training");
            return true;
        }

        return false;
    }

    /**
     * It checks if the trainer has a training at the time of the new training.
     *
     * @param timeReqFrom time from which the user wants to create a training
     * @param timeReqTo the time to which the training is to be created
     * @param trainer Trainer object
     * @return ```
     *     @Override
     *     public List<Training> findAllByTrainer(Trainer trainer) {
     *         return trainingRepository.findAllByTrainer(trainer);
     *     }
     * ```
     */
    private boolean validateToTrainer(LocalDateTime timeReqFrom, LocalDateTime timeReqTo, Trainer trainer){
        List<Training> trainingsToCheckTime = trainingRepository.findAllByTrainer(trainer);
        if(!trainingsToCheckTime.isEmpty()) {
            for (Training trainingToCheck: trainingsToCheckTime) {
                if(
                        (timeReqFrom.isAfter(trainingToCheck.getTimeFrom())
                                && timeReqTo.isBefore(trainingToCheck.getTimeTo()))
                                || (timeReqTo.isAfter(trainingToCheck.getTimeTo())
                                && timeReqTo.isBefore(trainingToCheck.getTimeTo()))
                                ||(timeReqFrom.isBefore(trainingToCheck.getTimeTo())
                                && timeReqTo.isAfter(trainingToCheck.getTimeTo()))){
                    LOG.error("You cannot create training with this time and Trainer because Trainer has already training at this time");
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * It checks if the user has a training at the time of the new training.
     *
     * @param timeReqFrom the time from which the user wants to create a training
     * @param timeReqTo the time to which the user wants to create a training
     * @param user the user who is creating the training
     * @return ```
     *     @GetMapping("/trainings")
     *     public List<Training> getAllTrainings() {
     *         return trainingRepository.findAll();
     *     }
     * ```
     */
    private boolean validateToUser(LocalDateTime timeReqFrom, LocalDateTime timeReqTo, User user){
        List<Training> trainingsToCheckTime = trainingRepository.findAllByUser(user);
        if(!trainingsToCheckTime.isEmpty()) {
            for (Training trainingToCheck: trainingsToCheckTime) {
                if(
                        (timeReqFrom.isAfter(trainingToCheck.getTimeFrom())
                                && timeReqTo.isBefore(trainingToCheck.getTimeTo()))
                                || (timeReqTo.isAfter(trainingToCheck.getTimeTo())
                                && timeReqTo.isBefore(trainingToCheck.getTimeTo()))
                                ||(timeReqFrom.isBefore(trainingToCheck.getTimeTo())
                                && timeReqTo.isAfter(trainingToCheck.getTimeTo()))){
                    LOG.error("You cannot create training with this time and Trainer because You have already training at this time");
                    return false;
                }
            }
        }
        return true;
    }
}