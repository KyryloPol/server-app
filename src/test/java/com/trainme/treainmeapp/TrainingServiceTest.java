package com.trainme.treainmeapp;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sun.security.auth.UserPrincipal;
import com.trainme.treainmeapp.dao.TrainerRepository;
import com.trainme.treainmeapp.dao.TrainingRepository;
import com.trainme.treainmeapp.dao.UserRepository;
import com.trainme.treainmeapp.domain.Trainer;
import com.trainme.treainmeapp.domain.Training;
import com.trainme.treainmeapp.domain.User;
import com.trainme.treainmeapp.dto.TrainingDTO;
import com.trainme.treainmeapp.exceptions.NotAllowedException;
import com.trainme.treainmeapp.exceptions.TrainerNotFoundException;
import com.trainme.treainmeapp.exceptions.TrainingNotFoundException;
import com.trainme.treainmeapp.payload.request.TrainingRequest;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import com.trainme.treainmeapp.services.TrainingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = { TrainingService.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class TrainingServiceTest {
        @MockBean
        private TrainerRepository trainerRepository;

        @MockBean
        private TrainingRepository trainingRepository;

        @Autowired
        private TrainingService trainingService;

        @MockBean
        private UserRepository userRepository;

    /**
     * Method under test: {@link TrainingService#createTraining(TrainingRequest, Principal)}
     */
    @Test
    public void testCreateTraining() {
        when(trainingRepository.existsTrainingByTimeFromAndTimeToAndTrainer((LocalDateTime) any(), (LocalDateTime) any(),
                (Trainer) any())).thenReturn(true);
        when(trainingRepository.findAllByTrainer((Trainer) any())).thenReturn(new ArrayList<>());
        when(trainingRepository.findAllByUser((User) any())).thenReturn(new ArrayList<>());

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

        Trainer trainer = new Trainer();
        trainer.setDateOfBirthday(LocalDate.ofEpochDay(1L));
        trainer.setEmail("test.test@mail.io");
        trainer.setId(133L);
        trainer.setImageModels(new ArrayList<>());
        trainer.setPriceForMonthTraining("1000$");
        trainer.setPriceForOneTraining("100$");
        trainer.setReviewValue(42L);
        trainer.setTelephone("+420773551");
        trainer.setTrainings(new ArrayList<>());
        trainer.setUsername("username");
        Optional<Trainer> ofResult1 = Optional.of(trainer);
        when(trainerRepository.findById((Long) any())).thenReturn(ofResult1);

        TrainingRequest trainingRequest = new TrainingRequest();
        trainingRequest.setPlace("Room 404");
        trainingRequest.setTimeFrom(LocalDateTime.of(1, 1, 1, 1, 1));
        trainingRequest.setTimeTo(LocalDateTime.of(1, 1, 1, 1, 1));
        trainingRequest.setTrainerId(133L);
        assertThrows(NotAllowedException.class,
                () -> trainingService.createTraining(trainingRequest, new UserPrincipal("principal")));
        verify(trainingRepository).existsTrainingByTimeFromAndTimeToAndTrainer((LocalDateTime) any(), (LocalDateTime) any(),
                (Trainer) any());
        verify(userRepository).findUserByUsername((String) any());
        verify(trainerRepository).findById((Long) any());
    }

        /**
         * Method under test: {@link TrainingService#getTraining(Long, Principal)}
         */
        @Test
        public void testGetTraining() {
                Trainer trainer = new Trainer();
                trainer.setDateOfBirthday(LocalDate.ofEpochDay(1L));
                trainer.setEmail("test.test@mail.io");
                trainer.setId(133L);
                trainer.setImageModels(new ArrayList<>());
                trainer.setPriceForMonthTraining("1000$");
                trainer.setPriceForOneTraining("100$");
                trainer.setReviewValue(42L);
                trainer.setTelephone("+420773551");
                trainer.setTrainings(new ArrayList<>());
                trainer.setUsername("username");

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

                Training training = new Training();
                training.setId(133L);
                training.setPlace("Room 404");
                training.setTimeFrom(LocalDateTime.of(1, 1, 1, 1, 1));
                training.setTimeTo(LocalDateTime.of(1, 1, 1, 1, 1));
                training.setTrainer(trainer);
                training.setUser(user);
                Optional<Training> ofResult = Optional.of(training);
                when(trainingRepository.findByIdAndUserId((Long) any(), (Long) any())).thenReturn(ofResult);

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
                Optional<User> ofResult1 = Optional.of(user1);
                when(userRepository.findUserByUsername((String) any())).thenReturn(ofResult1);
                assertSame(training, trainingService.getTraining(133L, new UserPrincipal("principal")));
                verify(trainingRepository).findByIdAndUserId((Long) any(), (Long) any());
                verify(userRepository).findUserByUsername((String) any());
        }

    /**
     * Method under test: {@link TrainingService#getTraining(Long, Principal)}
     */
    @Test
    public void testGetTraining2() {
        when(trainingRepository.findByIdAndUserId((Long) any(), (Long) any()))
                .thenThrow(new NotAllowedException("Training not found"));

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
        assertThrows(NotAllowedException.class, () -> trainingService.getTraining(133L, new UserPrincipal("principal")));
        verify(trainingRepository).findByIdAndUserId((Long) any(), (Long) any());
        verify(userRepository).findUserByUsername((String) any());
    }

        /**
         * Method under test:
         * {@link TrainingService#updateTraining(TrainingDTO, Long, Principal)}
         */
        @Test
        public void testUpdateTraining() {
                Trainer trainer = new Trainer();
                trainer.setDateOfBirthday(LocalDate.ofEpochDay(1L));
                trainer.setEmail("test.test@mail.io");
                trainer.setId(133L);
                trainer.setImageModels(new ArrayList<>());
                trainer.setPriceForMonthTraining("1000$");
                trainer.setPriceForOneTraining("100$");
                trainer.setReviewValue(42L);
                trainer.setTelephone("+420773551");
                trainer.setTrainings(new ArrayList<>());
                trainer.setUsername("username");

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

                Training training = new Training();
                training.setId(133L);
                training.setPlace("Room 404");
                training.setTimeFrom(LocalDateTime.of(1, 1, 1, 1, 1));
                training.setTimeTo(LocalDateTime.of(1, 1, 1, 1, 1));
                training.setTrainer(trainer);
                training.setUser(user);
                Optional<Training> ofResult = Optional.of(training);
                when(trainingRepository.existsTrainingByTimeFromAndTimeToAndTrainer((LocalDateTime) any(),
                                (LocalDateTime) any(),
                                (Trainer) any())).thenReturn(true);
                when(trainingRepository.findAllByTrainer((Trainer) any())).thenReturn(new ArrayList<>());
                when(trainingRepository.findAllByUser((User) any())).thenReturn(new ArrayList<>());
                when(trainingRepository.findByIdAndUserId((Long) any(), (Long) any())).thenReturn(ofResult);

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
                Optional<User> ofResult1 = Optional.of(user1);
                when(userRepository.findUserByUsername((String) any())).thenReturn(ofResult1);

                Trainer trainer1 = new Trainer();
                trainer1.setDateOfBirthday(LocalDate.ofEpochDay(1L));
                trainer1.setEmail("test.test@mail.io");
                trainer1.setId(133L);
                trainer1.setImageModels(new ArrayList<>());
                trainer1.setPriceForMonthTraining("1000$");
                trainer1.setPriceForOneTraining("100$");
                trainer1.setReviewValue(42L);
                trainer1.setTelephone("+420773551");
                trainer1.setTrainings(new ArrayList<>());
                trainer1.setUsername("username");
                Optional<Trainer> ofResult2 = Optional.of(trainer1);
                when(trainerRepository.findTrainerById((Long) any())).thenReturn(ofResult2);

                TrainingDTO trainingDTO = new TrainingDTO();
                trainingDTO.setId(133L);
                trainingDTO.setPlace("Room 404");
                trainingDTO.setTimeFrom(LocalDateTime.of(1, 1, 1, 1, 1));
                trainingDTO.setTimeTo(LocalDateTime.of(1, 1, 1, 1, 1));
                assertThrows(NotAllowedException.class,
                                () -> trainingService.updateTraining(trainingDTO, 133L,
                                                new UserPrincipal("principal")));
                verify(trainingRepository).existsTrainingByTimeFromAndTimeToAndTrainer((LocalDateTime) any(),
                                (LocalDateTime) any(), (Trainer) any());
                verify(trainingRepository).findByIdAndUserId((Long) any(), (Long) any());
                verify(userRepository).findUserByUsername((String) any());
                verify(trainerRepository).findTrainerById((Long) any());
        }

        /**
         * Method under test:
         * {@link TrainingService#updateTraining(TrainingDTO, Long, Principal)}
         */
        @Test
        public void testUpdateTraining2() {
                Trainer trainer = new Trainer();
                trainer.setDateOfBirthday(LocalDate.ofEpochDay(1L));
                trainer.setEmail("test.test@mail.io");
                trainer.setId(133L);
                trainer.setImageModels(new ArrayList<>());
                trainer.setPriceForMonthTraining("1000$");
                trainer.setPriceForOneTraining("100$");
                trainer.setReviewValue(42L);
                trainer.setTelephone("+420773551");
                trainer.setTrainings(new ArrayList<>());
                trainer.setUsername("username");

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

                Training training = new Training();
                training.setId(133L);
                training.setPlace("Room 404");
                training.setTimeFrom(LocalDateTime.of(1, 1, 1, 1, 1));
                training.setTimeTo(LocalDateTime.of(1, 1, 1, 1, 1));
                training.setTrainer(trainer);
                training.setUser(user);
                Optional<Training> ofResult = Optional.of(training);
                when(trainingRepository.existsTrainingByTimeFromAndTimeToAndTrainer((LocalDateTime) any(),
                                (LocalDateTime) any(),
                                (Trainer) any()))
                                .thenThrow(new TrainerNotFoundException(
                                                "You are not allowed to do this, please change your time data"));
                when(trainingRepository.findAllByTrainer((Trainer) any()))
                                .thenThrow(new TrainerNotFoundException(
                                                "You are not allowed to do this, please change your time data"));
                when(trainingRepository.findAllByUser((User) any()))
                                .thenThrow(new TrainerNotFoundException(
                                                "You are not allowed to do this, please change your time data"));
                when(trainingRepository.findByIdAndUserId((Long) any(), (Long) any())).thenReturn(ofResult);

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
                Optional<User> ofResult1 = Optional.of(user1);
                when(userRepository.findUserByUsername((String) any())).thenReturn(ofResult1);

                Trainer trainer1 = new Trainer();
                trainer1.setDateOfBirthday(LocalDate.ofEpochDay(1L));
                trainer1.setEmail("test.test@mail.io");
                trainer1.setId(133L);
                trainer1.setImageModels(new ArrayList<>());
                trainer1.setPriceForMonthTraining("1000$");
                trainer1.setPriceForOneTraining("100$");
                trainer1.setReviewValue(42L);
                trainer1.setTelephone("+420773551");
                trainer1.setTrainings(new ArrayList<>());
                trainer1.setUsername("username");
                Optional<Trainer> ofResult2 = Optional.of(trainer1);
                when(trainerRepository.findTrainerById((Long) any())).thenReturn(ofResult2);

                TrainingDTO trainingDTO = new TrainingDTO();
                trainingDTO.setId(133L);
                trainingDTO.setPlace("Room 404");
                trainingDTO.setTimeFrom(LocalDateTime.of(1, 1, 1, 1, 1));
                trainingDTO.setTimeTo(LocalDateTime.of(1, 1, 1, 1, 1));
                assertThrows(TrainerNotFoundException.class,
                                () -> trainingService.updateTraining(trainingDTO, 133L,
                                                new UserPrincipal("principal")));
                verify(trainingRepository).existsTrainingByTimeFromAndTimeToAndTrainer((LocalDateTime) any(),
                                (LocalDateTime) any(), (Trainer) any());
                verify(trainingRepository).findByIdAndUserId((Long) any(), (Long) any());
                verify(userRepository).findUserByUsername((String) any());
                verify(trainerRepository).findTrainerById((Long) any());
        }

        /**
         * Method under test: {@link TrainingService#getAllTrainingsForTrainer(Long)}
         */
        @Test
        public void testGetAllTrainingsForTrainer() {
                ArrayList<Training> trainingList = new ArrayList<>();
                when(trainingRepository.findAllByTrainer((Trainer) any())).thenReturn(trainingList);

                Trainer trainer = new Trainer();
                trainer.setDateOfBirthday(LocalDate.ofEpochDay(1L));
                trainer.setEmail("test.test@mail.io");
                trainer.setId(133L);
                trainer.setImageModels(new ArrayList<>());
                trainer.setPriceForMonthTraining("1000$");
                trainer.setPriceForOneTraining("100$");
                trainer.setReviewValue(42L);
                trainer.setTelephone("+420773551");
                trainer.setTrainings(new ArrayList<>());
                trainer.setUsername("username");
                Optional<Trainer> ofResult = Optional.of(trainer);
                when(trainerRepository.findById((Long) any())).thenReturn(ofResult);
                List<Training> actualAllTrainingsForTrainer = trainingService.getAllTrainingsForTrainer(133L);
                assertSame(trainingList, actualAllTrainingsForTrainer);
                assertTrue(actualAllTrainingsForTrainer.isEmpty());
                verify(trainingRepository).findAllByTrainer((Trainer) any());
                verify(trainerRepository).findById((Long) any());
        }

    /**
     * Method under test: {@link TrainingService#getAllTrainingsForTrainer(Long)}
     */
    @Test
    public void testGetAllTrainingsForTrainer2() {
        when(trainingRepository.findAllByTrainer((Trainer) any())).thenReturn(new ArrayList<>());
        when(trainerRepository.findById((Long) any())).thenReturn(Optional.empty());
        assertThrows(TrainerNotFoundException.class, () -> trainingService.getAllTrainingsForTrainer(133L));
        verify(trainerRepository).findById((Long) any());
    }

        /**
         * Method under test: {@link TrainingService#getAllTrainingsForUser(Principal)}
         */
        @Test
        public void testGetAllTrainingsForUser() {
                ArrayList<Training> trainingList = new ArrayList<>();
                when(trainingRepository.findAllByUser((User) any())).thenReturn(trainingList);

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
                List<Training> actualAllTrainingsForUser = trainingService
                                .getAllTrainingsForUser(new UserPrincipal("principal"));
                assertSame(trainingList, actualAllTrainingsForUser);
                assertTrue(actualAllTrainingsForUser.isEmpty());
                verify(trainingRepository).findAllByUser((User) any());
                verify(userRepository).findUserByUsername((String) any());
        }

    /**
     * Method under test: {@link TrainingService#getAllTrainingsForUser(Principal)}
     */
    @Test
    public void testGetAllTrainingsForUser2() {
        when(trainingRepository.findAllByUser((User) any())).thenReturn(new ArrayList<>());
        when(userRepository.findUserByUsername((String) any())).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class,
                () -> trainingService.getAllTrainingsForUser(new UserPrincipal("principal")));
        verify(userRepository).findUserByUsername((String) any());
    }

        /**
         * Method under test: {@link TrainingService#deleteTraining(Long, Principal)}
         */
        @Test
        public void testDeleteTraining() {
                Trainer trainer = new Trainer();
                trainer.setDateOfBirthday(LocalDate.ofEpochDay(1L));
                trainer.setEmail("test.test@mail.io");
                trainer.setId(133L);
                trainer.setImageModels(new ArrayList<>());
                trainer.setPriceForMonthTraining("1000$");
                trainer.setPriceForOneTraining("100$");
                trainer.setReviewValue(42L);
                trainer.setTelephone("+420773551");
                trainer.setTrainings(new ArrayList<>());
                trainer.setUsername("username");

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

                Training training = new Training();
                training.setId(133L);
                training.setPlace("Room 404");
                training.setTimeFrom(LocalDateTime.of(1, 1, 1, 1, 1));
                training.setTimeTo(LocalDateTime.of(1, 1, 1, 1, 1));
                training.setTrainer(trainer);
                training.setUser(user);
                Optional<Training> ofResult = Optional.of(training);
                doNothing().when(trainingRepository).delete((Training) any());
                when(trainingRepository.findByIdAndUserId((Long) any(), (Long) any())).thenReturn(ofResult);

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
                Optional<User> ofResult1 = Optional.of(user1);
                when(userRepository.findUserByUsername((String) any())).thenReturn(ofResult1);
                trainingService.deleteTraining(133L, new UserPrincipal("principal"));
                verify(trainingRepository).findByIdAndUserId((Long) any(), (Long) any());
                verify(trainingRepository).delete((Training) any());
                verify(userRepository).findUserByUsername((String) any());
        }

        /**
         * Method under test: {@link TrainingService#deleteTraining(Long, Principal)}
         */
        @Test
        public void testDeleteTraining2() {
                doNothing().when(trainingRepository).delete((Training) any());
                when(trainingRepository.findByIdAndUserId((Long) any(), (Long) any())).thenReturn(Optional.empty());

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
                assertThrows(TrainingNotFoundException.class,
                                () -> trainingService.deleteTraining(133L, new UserPrincipal("principal")));
                verify(trainingRepository).findByIdAndUserId((Long) any(), (Long) any());
                verify(userRepository).findUserByUsername((String) any());
        }
}
