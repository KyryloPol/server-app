package com.trainme.treainmeapp.web;

import com.trainme.treainmeapp.domain.Trainer;
import com.trainme.treainmeapp.dto.TrainerDTO;
import com.trainme.treainmeapp.facade.TrainerFacade;
import com.trainme.treainmeapp.payload.response.MessageResponse;
import com.trainme.treainmeapp.services.TrainerService;
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
 * This class is a REST controller that handles all requests that are related to the Trainer entity
 */
@RestController
@RequestMapping("api/trainer")
@CrossOrigin(origins = "*")
public class TrainerController {
    @Autowired
    private TrainerService trainerService;
    @Autowired
    private TrainerFacade trainerFacade;
    @Autowired
    private ResponseErrorValidation responseErrorValidation;

    /**
     * It creates a trainer.
     *
     * @param trainerDTO The object that will be created.
     * @param bindingResult This is used to validate the request body.
     * @param principal This is the currently logged-in user.
     * @return TrainerDTO
     */
    @PostMapping("/")
    public ResponseEntity<Object> createTrainer(@Valid @RequestBody TrainerDTO trainerDTO, BindingResult bindingResult, Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        Trainer trainer = trainerService.createTrainer(trainerDTO, principal);
        TrainerDTO createdTrainer = trainerFacade.trainerToTrainerDTO(trainer);
        return new ResponseEntity<>(createdTrainer, HttpStatus.OK);
    }

    /**
     * It updates a trainer.
     *
     * @param trainerID The ID of the trainer to be updated.
     * @param trainerDTO The object that will be updated.
     * @param bindingResult This is the object that will hold the results of the validation and binding and that will be
     * examined in the controller method to determine if there were any validation errors.
     * @param principal This is the user who is logged in.
     * @return A trainer object
     */
    @PutMapping("/{trainerId}")
    public ResponseEntity<Object> updateTrainer(@PathVariable("trainerId") String trainerID, @Valid @RequestBody TrainerDTO trainerDTO, BindingResult bindingResult, Principal principal){
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        Trainer trainer = trainerService.updateTrainer(trainerDTO, Long.parseLong(trainerID),principal);
        TrainerDTO trainerUpdated = trainerFacade.trainerToTrainerDTO(trainer);
        return new ResponseEntity<>(trainerUpdated, HttpStatus.OK);
    }

    /**
     * It takes a trainerId from the URL, gets the trainer from the database, converts it to a TrainerDTO, and returns it
     *
     * @param trainerId The id of the trainer we want to get.
     * @return TrainerDTO
     */
    @GetMapping("/{trainerId}")
    public ResponseEntity<TrainerDTO> getTrainer(@PathVariable("trainerId") String trainerId){
        Trainer trainer = trainerService.getTrainerById(Long.parseLong(trainerId));
        TrainerDTO trainerDTO = trainerFacade.trainerToTrainerDTO(trainer);
        return new ResponseEntity<>(trainerDTO, HttpStatus.OK);
    }

    /**
     * The function deletes a trainer from the database
     *
     * @param trainerId The id of the trainer to be deleted
     * @param principal This is the currently logged-in user.
     * @return A ResponseEntity with a MessageResponse object and an HttpStatus.OK
     */
    @DeleteMapping("/{trainerId}")
    public ResponseEntity<MessageResponse> deleteTraining(@PathVariable("trainerId") String trainerId, Principal principal){
        trainerService.deleteTrainer(Long.parseLong(trainerId), principal);
        return new ResponseEntity<>(new MessageResponse("Trainer was deleted"), HttpStatus.OK);
    }

    /**
     * We get all the trainers from the database, convert them to DTOs, and return them in a list
     *
     * @return A list of TrainerDTO objects
     */
    @GetMapping("/all")
    public ResponseEntity<List<TrainerDTO>> getAll(){
        List<TrainerDTO> trainerDTOList = trainerService.getAllTrainerDesc()
                .stream()
                .map(trainerFacade::trainerToTrainerDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(trainerDTOList, HttpStatus.OK);
    }

    /**
     * It returns the best trainer in the database
     *
     * @return TrainerDTO
     */
    @GetMapping("/theBest")
    public ResponseEntity<TrainerDTO> getTheBest(){
        TrainerDTO trainer = trainerFacade.trainerToTrainerDTO(trainerService.getTheBestTrainer());
        return new ResponseEntity<>(trainer, HttpStatus.OK);
    }

    /**
     * It takes a valueId as a path variable, gets a list of trainers by that value, maps them to trainerDTOs, and returns
     * them as a response entity
     *
     * @param valueId the id of the value you want to get the trainers for
     * @return List of TrainerDTO
     */
    @GetMapping("/byValue/{valueId}")
    public ResponseEntity<List<TrainerDTO>> getByValue(@PathVariable("valueId") String valueId){
        List<TrainerDTO> postDTOList = trainerService.getTrainersByValue(Long.parseLong(valueId))
                .stream()
                .map(trainerFacade::trainerToTrainerDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(postDTOList, HttpStatus.OK);
    }
}
