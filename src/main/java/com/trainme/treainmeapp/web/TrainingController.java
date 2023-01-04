package com.trainme.treainmeapp.web;

import com.trainme.treainmeapp.domain.Training;
import com.trainme.treainmeapp.dto.TrainingDTO;
import com.trainme.treainmeapp.dto.TrainingForTrainerDTO;
import com.trainme.treainmeapp.dto.TrainingForUserDTO;
import com.trainme.treainmeapp.facade.TrainingFacade;
import com.trainme.treainmeapp.payload.request.ReviewValueForTrainer;
import com.trainme.treainmeapp.payload.request.TrainingRequest;
import com.trainme.treainmeapp.payload.response.MessageResponse;
import com.trainme.treainmeapp.services.TrainingService;
import com.trainme.treainmeapp.validations.ResponseErrorValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is responsible for handling all requests related to the training entity
 */
@RestController
@RequestMapping("api/training")
@CrossOrigin(origins = "*")
public class TrainingController {
    @Autowired
    private TrainingService trainingService;
    @Autowired
    private TrainingFacade trainingFacade;
    @Autowired
    private ResponseErrorValidation responseErrorValidation;

    /**
     * It creates a new training.
     *
     * @param trainingRequest The request body that contains the training data.
     * @param bindingResult This is used to validate the request body.
     * @param principal This is the user who is currently logged in.
     * @return A Response Entity of any Object for error handling
     */
    @PostMapping("/")
    public ResponseEntity<Object> createTraining(@Valid @RequestBody TrainingRequest trainingRequest, BindingResult bindingResult, Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        Training training = trainingService.createTraining(trainingRequest, principal);
        TrainingDTO trainingDTO = trainingFacade.trainingToTrainingDTOForUser(training);
        return new ResponseEntity<>(trainingDTO, HttpStatus.OK);
    }

    /**
     * It updates a training.
     *
     * @param trainingId the id of the training to be updated
     * @param trainingDTO The object that will be used to update the training.
     * @param bindingResult This is used to validate the request body.
     * @param principal The principal object represents the currently logged-in user.
     * @return A Response Entity of any Object for error handling
     */
    @PutMapping("/{trainingId}")
    public ResponseEntity<Object> updateTraining(@PathVariable("trainingId") String trainingId, @Valid @RequestBody TrainingForUserDTO trainingDTO, BindingResult bindingResult, Principal principal){
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        Training training = trainingService.updateTraining(trainingDTO, Long.parseLong(trainingId), principal);
        TrainingForUserDTO updatedTraining = trainingFacade.trainingToTrainingDTOForUser(training);
        return new ResponseEntity<>(updatedTraining, HttpStatus.OK);
    }

    /**
     * It takes a trainingId and a principal (which is the user) and returns a trainingDTO
     *
     * @param trainingId the id of the training you want to get
     * @param principal The principal object is used to get the currently logged-in user.
     * @return TrainingDTO
     */
    @GetMapping("/{trainingId}")
    public ResponseEntity<TrainingDTO> getTrainingByIdAndUser(@PathVariable("trainingId") String trainingId,Principal principal){
        TrainingForUserDTO training = trainingFacade.trainingToTrainingDTOForUser(trainingService.getTraining(Long.parseLong(trainingId), principal));
        return new ResponseEntity<>(training, HttpStatus.OK);
    }

    /**
     * The function takes in a trainingId and a principal object, and deletes the training with the given trainingId
     *
     * @param trainingId The id of the training to be deleted
     * @param principal This is the user who is logged in.
     * @return A ResponseEntity with a MessageResponse object and an HttpStatus.OK
     */
    @DeleteMapping("/{trainingId}")
    public ResponseEntity<MessageResponse> deleteTraining(@PathVariable("trainingId") String trainingId, Principal principal){
        trainingService.deleteTraining(Long.parseLong(trainingId), principal);
        return new ResponseEntity<>(new MessageResponse("Training was deleted"), HttpStatus.OK);
    }

    /**
     * It returns a list of all trainings for a user, sorted by timeFrom in ascending or descending order
     *
     * @param order asc or desc
     * @param principal the user who is logged in
     * @return List of TrainingForUserDTO
     */
    @GetMapping("/")
    public ResponseEntity<List<TrainingForUserDTO>> getAllTrainingsForUser(@RequestParam(required = false) String order, Principal principal){
        List<TrainingForUserDTO> trainingDTOList = trainingService.getAllTrainingsForUser(principal)
                .stream()
                .map(trainingFacade::trainingToTrainingDTOForUser)
                .collect(Collectors.toList());
        if(order == null || order.equals("asc")){
            trainingDTOList.sort(((o1, o2) -> o2.getTimeFrom().compareTo(o1.getTimeFrom())));
        }
        else if(order.equals("desc")){
            trainingDTOList.sort(((o1, o2) -> o1.getTimeFrom().compareTo(o2.getTimeFrom())));
        }
        else{
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<>(trainingDTOList, HttpStatus.OK);
    }

    /**
     * Get the nearest training for the user.
     *
     * @param principal the user who is logged in
     * @return TrainingForUserDTO
     */
    @GetMapping("/nearest")
    public ResponseEntity<TrainingForUserDTO> getNearestTrainingForUser(Principal principal){
        List<TrainingForUserDTO> trainingDTOList = trainingService.getAllTrainingsForUser(principal)
                .stream()
                .sorted(((o1, o2) -> o1.getTimeFrom().compareTo(o2.getTimeFrom())))
                .map(trainingFacade::trainingToTrainingDTOForUser)
                .toList();
        if(trainingDTOList.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<>(trainingDTOList.get(0), HttpStatus.OK);
    }

    /**
     * It gets all the trainings for a trainer, sorts them by timeFrom, maps them to TrainingDTOs and returns them
     *
     * @param trainerId the id of the trainer
     * @return List of TrainingForTrainerDTO
     */
    @GetMapping("/trainer/{trainerId}")
    public ResponseEntity<List<TrainingForTrainerDTO>> getAllTrainingForTrainer(@PathVariable("trainerId") String trainerId){
        List<TrainingForTrainerDTO> trainingDTOList = trainingService.getAllTrainingsForTrainer(Long.parseLong(trainerId))
                .stream()
                .sorted(((o1, o2) -> o2.getTimeFrom().compareTo(o1.getTimeFrom())))
                .map(trainingFacade::trainingToTrainingDTOForTrainer)
                .collect(Collectors.toList());
        return new ResponseEntity<>(trainingDTOList, HttpStatus.OK);
    }

    /**
     * This function is used to update the review value of a training
     *
     * @param reviewValueForTrainer This is the object that contains the review value and the training id.
     * @param principal The principal object is used to get the currently logged-in user.
     * @return Message response
     */
    @PostMapping("/review")
    public ResponseEntity<Object> setReviewValueFromTraining(@Valid @RequestBody ReviewValueForTrainer reviewValueForTrainer, Principal principal){
        trainingService.updateReviewByTraining(reviewValueForTrainer, principal);
        return new ResponseEntity<>(new MessageResponse("Value successfully uploaded"), HttpStatus.OK);
    }
}
