package com.trainme.treainmeapp.facade;

import static org.junit.Assert.assertEquals;

import com.trainme.treainmeapp.domain.Trainer;
import com.trainme.treainmeapp.domain.Training;
import com.trainme.treainmeapp.domain.User;
import com.trainme.treainmeapp.dto.TrainingForTrainerDTO;
import com.trainme.treainmeapp.dto.TrainingForUserDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = { TrainingFacade.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class TrainingFacadeTest {
    @Autowired
    private TrainingFacade trainingFacade;

    /**
     * Method under test:
     * {@link TrainingFacade#trainingToTrainingDTOForUser(Training)}
     */
    @Test
    public void testTrainingToTrainingDTOForUser() {
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
        TrainingForUserDTO actualTrainingToTrainingDTOForUserResult = trainingFacade
                .trainingToTrainingDTOForUser(training);
        assertEquals(133L, actualTrainingToTrainingDTOForUserResult.getId().longValue());
        assertEquals("username", actualTrainingToTrainingDTOForUserResult.getTrainerUsername());
        assertEquals("01:01", actualTrainingToTrainingDTOForUserResult.getTimeTo().toLocalTime().toString());
        assertEquals("01:01", actualTrainingToTrainingDTOForUserResult.getTimeFrom().toLocalTime().toString());
        assertEquals("Room 404", actualTrainingToTrainingDTOForUserResult.getPlace());
    }

    /**
     * Method under test:
     * {@link TrainingFacade#trainingToTrainingDTOForTrainer(Training)}
     */
    @Test
    public void testTrainingToTrainingDTOForTrainer() {
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
        TrainingForTrainerDTO actualTrainingToTrainingDTOForTrainerResult = trainingFacade
                .trainingToTrainingDTOForTrainer(training);
        assertEquals(133L, actualTrainingToTrainingDTOForTrainerResult.getId().longValue());
        assertEquals("username", actualTrainingToTrainingDTOForTrainerResult.getUserUsername());
        assertEquals("01:01", actualTrainingToTrainingDTOForTrainerResult.getTimeTo().toLocalTime().toString());
        assertEquals("01:01", actualTrainingToTrainingDTOForTrainerResult.getTimeFrom().toLocalTime().toString());
        assertEquals("Room 404", actualTrainingToTrainingDTOForTrainerResult.getPlace());
    }
}
