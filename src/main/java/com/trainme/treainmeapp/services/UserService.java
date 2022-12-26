package com.trainme.treainmeapp.services;


import com.trainme.treainmeapp.dto.UserDTO;
import com.trainme.treainmeapp.domain.User;
import com.trainme.treainmeapp.domain.enums.EMembership;
import com.trainme.treainmeapp.dao.UserRepository;
import com.trainme.treainmeapp.exceptions.UserExistException;
import com.trainme.treainmeapp.payload.request.SignUpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;


/**
 * It's a service class that contains methods for creating, updating and retrieving users
 */
@Service
public class UserService {
    // It's a logger that will be used to log information about the user service.
    public static final Logger LOG = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * It creates a new user and saves it to the database.
     *
     * @param userIn The user object that is passed in from the controller.
     * @return A User object
     */
    public User createUser(SignUpRequest userIn) {
        User user = new User();
        user.setEmail(userIn.getEmail());
        user.setUsername(userIn.getUsername());
        user.setPassword(passwordEncoder.encode(userIn.getPassword()));
        user.getMembership().add(EMembership.ROLE_STANDARD);

        try {
            LOG.info("Saving User {}", userIn.getEmail());
            return userRepository.save(user);
        } catch (Exception e) {
            LOG.error("Error during registration. {}", e.getMessage());
            throw new UserExistException("The user " + user.getUsername() + " already exist. Please check credentials");
        }
    }

    /**
     * > It updates the user with the data from the DTO, and saves it to the database
     *
     * @param userDTO This is the object that contains the data that the user has entered in the form.
     * @param principal The principal object is used to get the currently logged-in user.
     * @return User
     */
    public User updateUser(UserDTO userDTO, Principal principal){
        User user = getUserByPrincipal(principal);
        user.setUsername(userDTO.getUsername());
        user.setDateOfBirthday(userDTO.getDateOfBirthday());
        user.setMembershipFrom(userDTO.getMembershipFrom());
        user.setMembershipTo(userDTO.getMembershipTo());

        return userRepository.save(user);
    }

    /**
     * Get the current user by the principal.
     *
     * @param principal The principal object is the currently logged in user.
     * @return The current user.
     */
    public User getCurrentUser(Principal principal){
        return getUserByPrincipal(principal);
    }

    /**
     * If the user is found, return the user, otherwise throw an exception.
     *
     * @param userId The userId of the user you want to retrieve.
     * @return User object
     */
    public User getUserById(Long userId) {
        return userRepository.findUserById(userId)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
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

