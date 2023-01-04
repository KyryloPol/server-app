package com.trainme.treainmeapp;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sun.security.auth.UserPrincipal;
import com.trainme.treainmeapp.dao.TrainerRepository;
import com.trainme.treainmeapp.dao.UserRepository;
import com.trainme.treainmeapp.domain.Trainer;
import com.trainme.treainmeapp.domain.User;
import com.trainme.treainmeapp.dto.TrainerDTO;
import com.trainme.treainmeapp.exceptions.NotAllowedException;
import com.trainme.treainmeapp.exceptions.TrainerNotFoundException;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import com.trainme.treainmeapp.services.TrainerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = { TrainerService.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class TrainerServiceTest {
    @MockBean
    private TrainerRepository trainerRepository;

    @Autowired
    private TrainerService trainerService;

    @MockBean
    private UserRepository userRepository;

    /**
     * Method under test:
     * {@link TrainerService#createTrainer(TrainerDTO, Principal)}
     */
    @Test
    public void testCreateTrainer() {
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

        TrainerDTO trainerDTO = new TrainerDTO();
        trainerDTO.setDateOfBirthday(LocalDate.ofEpochDay(1L));
        trainerDTO.setEmail("test.test@mail.io");
        trainerDTO.setId(133L);
        trainerDTO.setPriceForMonthTraining("1000$");
        trainerDTO.setPriceForOneTraining("100$");
        trainerDTO.setReviewValue(10L);
        trainerDTO.setTelephone("+420773551");
        trainerDTO.setUsername("username");
        assertThrows(NotAllowedException.class,
                () -> trainerService.createTrainer(trainerDTO, new UserPrincipal("principal")));
        verify(userRepository).findUserByUsername((String) any());
    }

    /**
     * Method under test: {@link TrainerService#createTrainer(TrainerDTO, Principal)}
     */
    @Test
    public void testCreateTrainer2() {
        when(userRepository.findUserByUsername((String) any())).thenReturn(Optional.empty());

        TrainerDTO trainerDTO = new TrainerDTO();
        trainerDTO.setDateOfBirthday(LocalDate.ofEpochDay(1L));
        trainerDTO.setEmail("test.test@mail.io");
        trainerDTO.setId(133L);
        trainerDTO.setPriceForMonthTraining("1000$");
        trainerDTO.setPriceForOneTraining("100$");
        trainerDTO.setReviewValue(10L);
        trainerDTO.setTelephone("+420773551");
        trainerDTO.setUsername("username");
        assertThrows(UsernameNotFoundException.class,
                () -> trainerService.createTrainer(trainerDTO, new UserPrincipal("principal")));
        verify(userRepository).findUserByUsername((String) any());
    }

    /**
     * Method under test:
     * {@link TrainerService#updateTrainer(TrainerDTO, Long, Principal)}
     */
    @Test
    public void testUpdateTrainer() {
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

        TrainerDTO trainerDTO = new TrainerDTO();
        trainerDTO.setDateOfBirthday(LocalDate.ofEpochDay(1L));
        trainerDTO.setEmail("test.test@mail.io");
        trainerDTO.setId(133L);
        trainerDTO.setPriceForMonthTraining("1000$");
        trainerDTO.setPriceForOneTraining("100$");
        trainerDTO.setReviewValue(10L);
        trainerDTO.setTelephone("+420773551");
        trainerDTO.setUsername("username");
        assertThrows(NotAllowedException.class,
                () -> trainerService.updateTrainer(trainerDTO, 133L, new UserPrincipal("principal")));
        verify(userRepository).findUserByUsername((String) any());
    }

    /**
     * Method under test: {@link TrainerService#updateTrainer(TrainerDTO, Long, Principal)}
     */
    @Test
    public void testUpdateTrainer2() {
        when(userRepository.findUserByUsername((String) any())).thenReturn(Optional.empty());

        TrainerDTO trainerDTO = new TrainerDTO();
        trainerDTO.setDateOfBirthday(LocalDate.ofEpochDay(1L));
        trainerDTO.setEmail("test.test@mail.io");
        trainerDTO.setId(133L);
        trainerDTO.setPriceForMonthTraining("1000$");
        trainerDTO.setPriceForOneTraining("100$");
        trainerDTO.setReviewValue(10L);
        trainerDTO.setTelephone("+420773551");
        trainerDTO.setUsername("username");
        assertThrows(UsernameNotFoundException.class,
                () -> trainerService.updateTrainer(trainerDTO, 133L, new UserPrincipal("principal")));
        verify(userRepository).findUserByUsername((String) any());
    }

    /**
     * Method under test: {@link TrainerService#updateTrainer(TrainerDTO, Long, Principal)}
     */
    @Test
    public void testUpdateTrainer4() {
        when(userRepository.findUserByUsername((String) any())).thenThrow(new NotAllowedException("foo"));

        TrainerDTO trainerDTO = new TrainerDTO();
        trainerDTO.setDateOfBirthday(LocalDate.ofEpochDay(1L));
        trainerDTO.setEmail("test.test@mail.io");
        trainerDTO.setId(133L);
        trainerDTO.setPriceForMonthTraining("1000$");
        trainerDTO.setPriceForOneTraining("100$");
        trainerDTO.setReviewValue(10L);
        trainerDTO.setTelephone("+420773551");
        trainerDTO.setUsername("username");
        assertThrows(NotAllowedException.class,
                () -> trainerService.updateTrainer(trainerDTO, 133L, new UserPrincipal("principal")));
        verify(userRepository).findUserByUsername((String) any());
    }

    /**
     * Method under test: {@link TrainerService#getTrainerById(Long)}
     */
    @Test
    public void testGetTrainerById() {
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
        Optional<Trainer> ofResult = Optional.of(trainer);
        when(trainerRepository.findTrainerById((Long) any())).thenReturn(ofResult);
        assertSame(trainer, trainerService.getTrainerById(133L));
        verify(trainerRepository).findTrainerById((Long) any());
    }

    /**
     * Method under test: {@link TrainerService#getTrainerById(Long)}
     */
    @Test
    public void testGetTrainerById2() {
        when(trainerRepository.findTrainerById((Long) any())).thenReturn(Optional.empty());
        assertThrows(TrainerNotFoundException.class, () -> trainerService.getTrainerById(133L));
        verify(trainerRepository).findTrainerById((Long) any());
    }

    /**
     * Method under test: {@link TrainerService#deleteTrainer(Long, Principal)}
     */
    @Test
    public void testDeleteTrainer() {
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
        assertThrows(NotAllowedException.class,
                () -> trainerService.deleteTrainer(133L, new UserPrincipal("principal")));
        verify(userRepository).findUserByUsername((String) any());
    }

    /**
     * Method under test: {@link TrainerService#deleteTrainer(Long, Principal)}
     */
    @Test
    public void testDeleteTrainer2() {
        when(userRepository.findUserByUsername((String) any())).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class,
                () -> trainerService.deleteTrainer(133L, new UserPrincipal("principal")));
        verify(userRepository).findUserByUsername((String) any());
    }

    /**
     * Method under test: {@link TrainerService#getTrainersByValue(Long)}
     */
    @Test
    public void testGetTrainersByValue() {
        ArrayList<Trainer> trainerList = new ArrayList<>();
        when(trainerRepository.findTrainersByReviewValue((Long) any())).thenReturn(trainerList);
        List<Trainer> actualTrainersByValue = trainerService.getTrainersByValue(10L);
        assertSame(trainerList, actualTrainersByValue);
        assertTrue(actualTrainersByValue.isEmpty());
        verify(trainerRepository).findTrainersByReviewValue((Long) any());
    }

    /**
     * Method under test: {@link TrainerService#getTrainersByValue(Long)}
     */
    @Test
    public void testGetTrainersByValue2() {
        when(trainerRepository.findTrainersByReviewValue((Long) any())).thenThrow(new NotAllowedException("foo"));
        assertThrows(NotAllowedException.class, () -> trainerService.getTrainersByValue(10L));
        verify(trainerRepository).findTrainersByReviewValue((Long) any());
    }

    /**
     * Method under test: {@link TrainerService#getAllTrainerDesc()}
     */
    @Test
    public void testGetAllTrainerDesc() {
        ArrayList<Trainer> trainerList = new ArrayList<>();
        when(trainerRepository.findAllByOrderByReviewValueDesc()).thenReturn(trainerList);
        List<Trainer> actualAllTrainerDesc = trainerService.getAllTrainerDesc();
        assertSame(trainerList, actualAllTrainerDesc);
        assertTrue(actualAllTrainerDesc.isEmpty());
        verify(trainerRepository).findAllByOrderByReviewValueDesc();
    }

    /**
     * Method under test: {@link TrainerService#getAllTrainerDesc()}
     */
    @Test
    public void testGetAllTrainerDesc2() {
        when(trainerRepository.findAllByOrderByReviewValueDesc()).thenThrow(new NotAllowedException("foo"));
        assertThrows(NotAllowedException.class, () -> trainerService.getAllTrainerDesc());
        verify(trainerRepository).findAllByOrderByReviewValueDesc();
    }

    /**
     * Method under test: {@link TrainerService#getTheBestTrainer()}
     */
    @Test
    public void testGetTheBestTrainer() {
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

        ArrayList<Trainer> trainerList = new ArrayList<>();
        trainerList.add(trainer);
        when(trainerRepository.findAllByOrderByReviewValueDesc()).thenReturn(trainerList);
        assertSame(trainer, trainerService.getTheBestTrainer());
        verify(trainerRepository).findAllByOrderByReviewValueDesc();
    }

    /**
     * Method under test: {@link TrainerService#getTheBestTrainer()}
     */
    @Test
    public void testGetTheBestTrainer2() {
        when(trainerRepository.findAllByOrderByReviewValueDesc()).thenThrow(new NotAllowedException("foo"));
        assertThrows(NotAllowedException.class, () -> trainerService.getTheBestTrainer());
        verify(trainerRepository).findAllByOrderByReviewValueDesc();
    }
}
