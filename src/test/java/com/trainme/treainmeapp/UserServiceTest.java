package com.trainme.treainmeapp;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sun.security.auth.UserPrincipal;
import com.trainme.treainmeapp.dao.UserRepository;
import com.trainme.treainmeapp.domain.User;
import com.trainme.treainmeapp.dto.UserDTO;
import com.trainme.treainmeapp.exceptions.UserExistException;
import com.trainme.treainmeapp.payload.request.SignUpRequest;

import java.security.Principal;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import com.trainme.treainmeapp.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = { UserService.class, BCryptPasswordEncoder.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {
    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    /**
     * Method under test: {@link UserService#createUser(SignUpRequest)}
     */
    @Test
    public void testCreateUser() {
        User user = new User();
        user.setAuthorities(new ArrayList<>());
        user.setDateOfBirthday(LocalDate.ofEpochDay(1L));
        user.setEmail("test.test@mail.io");
        user.setId(133L);
        user.setMembership(new HashSet<>());
        user.setMembershipFrom(LocalDate.ofEpochDay(1L));
        user.setMembershipTo(LocalDate.ofEpochDay(1L));
        user.setPassword("pass");
        user.setTrainings(new ArrayList<>());
        user.setUsername("username");
        when(userRepository.save((User) any())).thenReturn(user);

        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setConfirmPassword("pass");
        signUpRequest.setEmail("test.test@mail.io");
        signUpRequest.setPassword("pass");
        signUpRequest.setUsername("username");
        assertSame(user, userService.createUser(signUpRequest));
        verify(userRepository).save((User) any());
    }

    /**
     * Method under test: {@link UserService#updateUser(UserDTO, Principal)}
     */
    @Test
    public void testUpdateUser() {
        User user = new User();
        user.setAuthorities(new ArrayList<>());
        user.setDateOfBirthday(LocalDate.ofEpochDay(1L));
        user.setEmail("test.test@mail.io");
        user.setId(133L);
        user.setMembership(new HashSet<>());
        user.setMembershipFrom(LocalDate.ofEpochDay(1L));
        user.setMembershipTo(LocalDate.ofEpochDay(1L));
        user.setPassword("pass");
        user.setTrainings(new ArrayList<>());
        user.setUsername("username");
        Optional<User> ofResult = Optional.of(user);

        User user1 = new User();
        user1.setAuthorities(new ArrayList<>());
        user1.setDateOfBirthday(LocalDate.ofEpochDay(1L));
        user1.setEmail("test.test@mail.io");
        user1.setId(133L);
        user1.setMembership(new HashSet<>());
        user1.setMembershipFrom(LocalDate.ofEpochDay(1L));
        user1.setMembershipTo(LocalDate.ofEpochDay(1L));
        user1.setPassword("pass");
        user1.setTrainings(new ArrayList<>());
        user1.setUsername("username");
        when(userRepository.save((User) any())).thenReturn(user1);
        when(userRepository.findUserByUsername((String) any())).thenReturn(ofResult);

        UserDTO userDTO = new UserDTO();
        userDTO.setDateOfBirthday(LocalDate.ofEpochDay(1L));
        userDTO.setId(133L);
        userDTO.setMembershipFrom(LocalDate.ofEpochDay(1L));
        userDTO.setMembershipTo(LocalDate.ofEpochDay(1L));
        userDTO.setMemberships(new HashSet<>());
        userDTO.setUsername("username");
        assertSame(user1, userService.updateUser(userDTO, new UserPrincipal("principal")));
        verify(userRepository).save((User) any());
        verify(userRepository).findUserByUsername((String) any());
    }

    /**
     * Method under test: {@link UserService#updateUser(UserDTO, Principal)}
     */
    @Test
    public void testUpdateUserExists() {
        User user = new User();
        user.setAuthorities(new ArrayList<>());
        user.setDateOfBirthday(LocalDate.ofEpochDay(1L));
        user.setEmail("test.test@mail.io");
        user.setId(133L);
        user.setMembership(new HashSet<>());
        user.setMembershipFrom(LocalDate.ofEpochDay(1L));
        user.setMembershipTo(LocalDate.ofEpochDay(1L));
        user.setPassword("pass");
        user.setTrainings(new ArrayList<>());
        user.setUsername("username");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.save((User) any())).thenThrow(new UserExistException("An error occurred"));
        when(userRepository.findUserByUsername((String) any())).thenReturn(ofResult);

        UserDTO userDTO = new UserDTO();
        userDTO.setDateOfBirthday(LocalDate.ofEpochDay(1L));
        userDTO.setId(133L);
        userDTO.setMembershipFrom(LocalDate.ofEpochDay(1L));
        userDTO.setMembershipTo(LocalDate.ofEpochDay(1L));
        userDTO.setMemberships(new HashSet<>());
        userDTO.setUsername("username");
        assertThrows(UserExistException.class, () -> userService.updateUser(userDTO, new UserPrincipal("principal")));
        verify(userRepository).save((User) any());
        verify(userRepository).findUserByUsername((String) any());
    }

    /**
     * Method under test: {@link UserService#getCurrentUser(Principal)}
     */
    @Test
    public void testGetCurrentUser() {
        User user = new User();
        user.setAuthorities(new ArrayList<>());
        user.setDateOfBirthday(LocalDate.ofEpochDay(1L));
        user.setEmail("test.test@mail.io");
        user.setId(133L);
        user.setMembership(new HashSet<>());
        user.setMembershipFrom(LocalDate.ofEpochDay(1L));
        user.setMembershipTo(LocalDate.ofEpochDay(1L));
        user.setPassword("pass");
        user.setTrainings(new ArrayList<>());
        user.setUsername("username");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findUserByUsername((String) any())).thenReturn(ofResult);
        assertSame(user, userService.getCurrentUser(new UserPrincipal("principal")));
        verify(userRepository).findUserByUsername((String) any());
    }

    /**
     * Method under test: {@link UserService#getCurrentUser(Principal)}
     */
    @Test
    public void testGetCurrentUser2() {
        when(userRepository.findUserByUsername((String) any())).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> userService.getCurrentUser(new UserPrincipal("principal")));
        verify(userRepository).findUserByUsername((String) any());
    }

    /**
     * Method under test: {@link UserService#getUserById(Long)}
     */
    @Test
    public void testGetUserById() {
        User user = new User();
        user.setAuthorities(new ArrayList<>());
        user.setDateOfBirthday(LocalDate.ofEpochDay(1L));
        user.setEmail("test.test@mail.io");
        user.setId(133L);
        user.setMembership(new HashSet<>());
        user.setMembershipFrom(LocalDate.ofEpochDay(1L));
        user.setMembershipTo(LocalDate.ofEpochDay(1L));
        user.setPassword("pass");
        user.setTrainings(new ArrayList<>());
        user.setUsername("username");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findUserById((Long) any())).thenReturn(ofResult);
        assertSame(user, userService.getUserById(133L));
        verify(userRepository).findUserById((Long) any());
    }

    /**
     * Method under test: {@link UserService#getUserById(Long)}
     */
    @Test
    public void testGetUserByIdNotFound() {
        when(userRepository.findUserById((Long) any())).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> userService.getUserById(133L));
        verify(userRepository).findUserById((Long) any());
    }
}
