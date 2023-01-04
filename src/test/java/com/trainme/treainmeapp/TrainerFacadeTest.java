package com.trainme.treainmeapp.facade;

import static org.junit.Assert.assertEquals;

import com.trainme.treainmeapp.domain.Trainer;
import com.trainme.treainmeapp.dto.TrainerDTO;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = { TrainerFacade.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class TrainerFacadeTest {
    @Autowired
    private TrainerFacade trainerFacade;

    /**
     * Method under test: {@link TrainerFacade#trainerToTrainerDTO(Trainer)}
     */
    @Test
    public void testTrainerToTrainerDTO() {
        Trainer trainer = new Trainer();
        trainer.setDateOfBirthday(LocalDate.ofEpochDay(1L));
        trainer.setEmail("test.test@mail.io");
        trainer.setId(133L);
        trainer.setImageModels(new ArrayList<>());
        trainer.setPriceForMonthTraining("1000$");
        trainer.setPriceForOneTraining("100$");
        trainer.setReviewValue(10L);
        trainer.setTelephone("+420773551");
        trainer.setTrainings(new ArrayList<>());
        trainer.setUsername("username");
        TrainerDTO actualTrainerToTrainerDTOResult = trainerFacade.trainerToTrainerDTO(trainer);
        assertEquals("1970-01-02", actualTrainerToTrainerDTOResult.getDateOfBirthday().toString());
        assertEquals("username", actualTrainerToTrainerDTOResult.getUsername());
        assertEquals("+420773551", actualTrainerToTrainerDTOResult.getTelephone());
        assertEquals(10L, actualTrainerToTrainerDTOResult.getReviewValue().longValue());
        assertEquals("100$", actualTrainerToTrainerDTOResult.getPriceForOneTraining());
        assertEquals("1000$", actualTrainerToTrainerDTOResult.getPriceForMonthTraining());
        assertEquals(133L, actualTrainerToTrainerDTOResult.getId().longValue());
        assertEquals("test.test@mail.io", actualTrainerToTrainerDTOResult.getEmail());
    }
}
