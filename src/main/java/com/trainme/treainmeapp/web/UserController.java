package com.trainme.treainmeapp.web;

import com.trainme.treainmeapp.domain.User;
import com.trainme.treainmeapp.dto.UserDTO;
import com.trainme.treainmeapp.facade.UserFacade;
import com.trainme.treainmeapp.services.UserService;
import com.trainme.treainmeapp.validations.ResponseErrorValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

/**
 * This class is a controller that handles all the requests that are related to the user
 */
@RestController
@RequestMapping("api/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserFacade userFacade;
    @Autowired
    private ResponseErrorValidation responseErrorValidation;

    /**
     * It takes the current user's principal, gets the user from the database, converts it to a DTO, and returns it
     *
     * @param principal The principal object represents the currently authenticated user.
     * @return A userDTO object
     */
    @GetMapping("/")
    public ResponseEntity<UserDTO> getCurrentUser(Principal principal){
        User user = userService.getCurrentUser(principal);
        UserDTO userDTO = userFacade.userToUserDTO(user);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    /**
     * It takes a userId as a path variable, gets the user from the database, converts it to a DTO, and returns it as a
     * response entity
     *
     * @param userId The id of the user whose profile is to be fetched.
     * @return A userDTO object
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserProfile(@PathVariable("userId") String userId){
        User user = userService.getUserById(Long.parseLong(userId));
        UserDTO userDTO = userFacade.userToUserDTO(user);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    /**
     * It updates the user.
     *
     * @param userDTO The user object that will be updated.
     * @param bindingResult This is used to validate the request body.
     * @param principal The principal object represents the currently authenticated user.
     * @return A Response Entity of any Object for error handling
     */
    @PutMapping("/")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult, Principal principal){
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        User user = userService.updateUser(userDTO, principal);
        UserDTO userUpdated = userFacade.userToUserDTO(user);
        return new ResponseEntity<>(userUpdated, HttpStatus.OK);
    }
}
