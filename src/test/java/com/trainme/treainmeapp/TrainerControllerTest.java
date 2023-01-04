package com.trainme.treainmeapp;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trainme.treainmeapp.domain.Trainer;
import com.trainme.treainmeapp.dto.TrainerDTO;
import com.trainme.treainmeapp.facade.TrainerFacade;
import com.trainme.treainmeapp.services.TrainerService;
import com.trainme.treainmeapp.validations.ResponseErrorValidation;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;

import com.trainme.treainmeapp.web.TrainerController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;

@ContextConfiguration(classes = { TrainerController.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class TrainerControllerTest {
        @MockBean
        private ResponseErrorValidation responseErrorValidation;

        @Autowired
        private TrainerController trainerController;

        @MockBean
        private TrainerFacade trainerFacade;

        @MockBean
        private TrainerService trainerService;

    /**
     * Method under test: {@link TrainerController#createTrainer(TrainerDTO, BindingResult, Principal)}
     */
    @Test
    public void testCreateTrainer() throws Exception {
        when(responseErrorValidation.mapValidationService((BindingResult) any())).thenReturn(null);

        TrainerDTO trainerDTO = new TrainerDTO();
        trainerDTO.setDateOfBirthday(LocalDate.ofEpochDay(1L));
        trainerDTO.setEmail("test.test@mail.io");
        trainerDTO.setId(133L);
        trainerDTO.setPriceForMonthTraining("1000$");
        trainerDTO.setPriceForOneTraining("100$");
        trainerDTO.setReviewValue(10L);
        trainerDTO.setTelephone("+100773551");
        trainerDTO.setUsername("username");
        when(trainerFacade.trainerToTrainerDTO((Trainer) any())).thenReturn(trainerDTO);

        Trainer trainer = new Trainer();
        trainer.setDateOfBirthday(LocalDate.ofEpochDay(1L));
        trainer.setEmail("test.test@mail.io");
        trainer.setId(133L);
        trainer.setImageModels(new ArrayList<>());
        trainer.setPriceForMonthTraining("1000$");
        trainer.setPriceForOneTraining("100$");
        trainer.setReviewValue(10L);
        trainer.setTelephone("+100773551");
        trainer.setTrainings(new ArrayList<>());
        trainer.setUsername("username");
        when(trainerService.createTrainer((TrainerDTO) any(), (Principal) any())).thenReturn(trainer);

        TrainerDTO trainerDTO1 = new TrainerDTO();
        trainerDTO1.setDateOfBirthday(null);
        trainerDTO1.setEmail("test.test@mail.io");
        trainerDTO1.setId(133L);
        trainerDTO1.setPriceForMonthTraining("1000$");
        trainerDTO1.setPriceForOneTraining("100$");
        trainerDTO1.setReviewValue(10L);
        trainerDTO1.setTelephone("+100773551");
        trainerDTO1.setUsername("username");
        String content = (new ObjectMapper()).writeValueAsString(trainerDTO1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/trainer/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(trainerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":133,\"username\":\"username\",\"reviewValue\":10,\"email\":\"test.test@mail.io\",\"telephone\":\"+100773551"
                                        + "\",\"priceForOneTraining\":\"100$\",\"priceForMonthTraining\":\"1000$\","
                                        + "\"dateOfBirthday\":[1970,1,2]}"));
    }

    /**
     * Method under test: {@link TrainerController#createTrainer(TrainerDTO, BindingResult, Principal)}
     */
    @Test
    public void testCreateTrainer2() throws Exception {
        when(responseErrorValidation.mapValidationService((BindingResult) any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));

        TrainerDTO trainerDTO = new TrainerDTO();
        trainerDTO.setDateOfBirthday(null);
        trainerDTO.setEmail("test.test@mail.io");
        trainerDTO.setId(133L);
        trainerDTO.setPriceForMonthTraining("1000$");
        trainerDTO.setPriceForOneTraining("100$");
        trainerDTO.setReviewValue(10L);
        trainerDTO.setTelephone("+100773551");
        trainerDTO.setUsername("username");
        String content = (new ObjectMapper()).writeValueAsString(trainerDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/trainer/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(trainerController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    /**
     * Method under test: {@link TrainerController#updateTrainer(String, TrainerDTO, BindingResult, Principal)}
     */
    @Test
    public void testUpdateTrainer() throws Exception {
        when(responseErrorValidation.mapValidationService((BindingResult) any())).thenReturn(null);

        TrainerDTO trainerDTO = new TrainerDTO();
        trainerDTO.setDateOfBirthday(LocalDate.ofEpochDay(1L));
        trainerDTO.setEmail("test.test@mail.io");
        trainerDTO.setId(133L);
        trainerDTO.setPriceForMonthTraining("1000$");
        trainerDTO.setPriceForOneTraining("100$");
        trainerDTO.setReviewValue(10L);
        trainerDTO.setTelephone("+100773551");
        trainerDTO.setUsername("username");
        when(trainerFacade.trainerToTrainerDTO((Trainer) any())).thenReturn(trainerDTO);

        Trainer trainer = new Trainer();
        trainer.setDateOfBirthday(LocalDate.ofEpochDay(1L));
        trainer.setEmail("test.test@mail.io");
        trainer.setId(133L);
        trainer.setImageModels(new ArrayList<>());
        trainer.setPriceForMonthTraining("1000$");
        trainer.setPriceForOneTraining("100$");
        trainer.setReviewValue(10L);
        trainer.setTelephone("+100773551");
        trainer.setTrainings(new ArrayList<>());
        trainer.setUsername("username");
        when(trainerService.updateTrainer((TrainerDTO) any(), (Long) any(), (Principal) any())).thenReturn(trainer);

        TrainerDTO trainerDTO1 = new TrainerDTO();
        trainerDTO1.setDateOfBirthday(null);
        trainerDTO1.setEmail("test.test@mail.io");
        trainerDTO1.setId(133L);
        trainerDTO1.setPriceForMonthTraining("1000$");
        trainerDTO1.setPriceForOneTraining("100$");
        trainerDTO1.setReviewValue(10L);
        trainerDTO1.setTelephone("+100773551");
        trainerDTO1.setUsername("username");
        String content = (new ObjectMapper()).writeValueAsString(trainerDTO1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/trainer/{trainerId}", "10")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(trainerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":133,\"username\":\"username\",\"reviewValue\":10,\"email\":\"test.test@mail.io\",\"telephone\":\"+100773551"
                                        + "\",\"priceForOneTraining\":\"100$\",\"priceForMonthTraining\":\"1000$\","
                                        + "\"dateOfBirthday\":[1970,1,2]}"));
    }

        /**
         * Method under test:
         * {@link TrainerController#deleteTraining(String, Principal)}
         */
        @Test
        public void testDeleteTraining() throws Exception {
                doNothing().when(trainerService).deleteTrainer((Long) any(), (Principal) any());
                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/trainer/{trainerId}",
                                "10");
                MockMvcBuilders.standaloneSetup(trainerController)
                                .build()
                                .perform(requestBuilder)
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                                .andExpect(MockMvcResultMatchers.content()
                                                .string("{\"message\":\"Trainer was deleted\"}"));
        }

        /**
         * Method under test:
         * {@link TrainerController#deleteTraining(String, Principal)}
         */
        @Test
        public void testDeleteTraining2() throws Exception {
                doNothing().when(trainerService).deleteTrainer((Long) any(), (Principal) any());
                SecurityMockMvcRequestBuilders.FormLoginRequestBuilder requestBuilder = SecurityMockMvcRequestBuilders
                                .formLogin();
                ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(trainerController)
                                .build()
                                .perform(requestBuilder);
                actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
        }

    /**
     * Method under test: {@link TrainerController#getAll()}
     */
    @Test
    public void testGetAll() throws Exception {
        when(trainerService.getAllTrainerDesc()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/trainer/all");
        MockMvcBuilders.standaloneSetup(trainerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link TrainerController#getAll()}
     */
    @Test
    public void testGetAll2() throws Exception {
        when(trainerService.getAllTrainerDesc()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/trainer/all");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(trainerController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link TrainerController#getByValue(String)}
     */
    @Test
    public void testGetByValue() throws Exception {
        when(trainerService.getTrainersByValue((Long) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/trainer/byValue/{valueId}", "10");
        MockMvcBuilders.standaloneSetup(trainerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

        /**
         * Method under test: {@link TrainerController#getTheBest()}
         */
        @Test
        public void testGetTheBest() throws Exception {
                TrainerDTO trainerDTO = new TrainerDTO();
                trainerDTO.setDateOfBirthday(LocalDate.ofEpochDay(1L));
                trainerDTO.setEmail("test.test@mail.io");
                trainerDTO.setId(133L);
                trainerDTO.setPriceForMonthTraining("1000$");
                trainerDTO.setPriceForOneTraining("100$");
                trainerDTO.setReviewValue(10L);
                trainerDTO.setTelephone("+100773551");
                trainerDTO.setUsername("username");
                when(trainerFacade.trainerToTrainerDTO((Trainer) any())).thenReturn(trainerDTO);

                Trainer trainer = new Trainer();
                trainer.setDateOfBirthday(LocalDate.ofEpochDay(1L));
                trainer.setEmail("test.test@mail.io");
                trainer.setId(133L);
                trainer.setImageModels(new ArrayList<>());
                trainer.setPriceForMonthTraining("1000$");
                trainer.setPriceForOneTraining("100$");
                trainer.setReviewValue(10L);
                trainer.setTelephone("+100773551");
                trainer.setTrainings(new ArrayList<>());
                trainer.setUsername("username");
                when(trainerService.getTheBestTrainer()).thenReturn(trainer);
                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/trainer/theBest");
                MockMvcBuilders.standaloneSetup(trainerController)
                                .build()
                                .perform(requestBuilder)
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                                .andExpect(MockMvcResultMatchers.content()
                                                .string(
                                                                "{\"id\":133,\"username\":\"username\",\"reviewValue\":10,\"email\":\"test.test@mail.io\",\"telephone\":\"+100773551"
                                                                                + "\",\"priceForOneTraining\":\"100$\",\"priceForMonthTraining\":\"1000$\","
                                                                                + "\"dateOfBirthday\":[1970,1,2]}"));
        }

        /**
         * Method under test: {@link TrainerController#getTrainer(String)}
         */
        @Test
        public void testGetTrainer() throws Exception {
                TrainerDTO trainerDTO = new TrainerDTO();
                trainerDTO.setDateOfBirthday(LocalDate.ofEpochDay(1L));
                trainerDTO.setEmail("test.test@mail.io");
                trainerDTO.setId(133L);
                trainerDTO.setPriceForMonthTraining("1000$");
                trainerDTO.setPriceForOneTraining("100$");
                trainerDTO.setReviewValue(10L);
                trainerDTO.setTelephone("+100773551");
                trainerDTO.setUsername("username");
                when(trainerFacade.trainerToTrainerDTO((Trainer) any())).thenReturn(trainerDTO);

                Trainer trainer = new Trainer();
                trainer.setDateOfBirthday(LocalDate.ofEpochDay(1L));
                trainer.setEmail("test.test@mail.io");
                trainer.setId(133L);
                trainer.setImageModels(new ArrayList<>());
                trainer.setPriceForMonthTraining("1000$");
                trainer.setPriceForOneTraining("100$");
                trainer.setReviewValue(10L);
                trainer.setTelephone("+100773551");
                trainer.setTrainings(new ArrayList<>());
                trainer.setUsername("username");
                when(trainerService.getTrainerById((Long) any())).thenReturn(trainer);
                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/trainer/{trainerId}",
                                "10");
                MockMvcBuilders.standaloneSetup(trainerController)
                                .build()
                                .perform(requestBuilder)
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                                .andExpect(MockMvcResultMatchers.content()
                                                .string(
                                                                "{\"id\":133,\"username\":\"username\",\"reviewValue\":10,\"email\":\"test.test@mail.io\",\"telephone\":\"+100773551"
                                                                                + "\",\"priceForOneTraining\":\"100$\",\"priceForMonthTraining\":\"1000$\","
                                                                                + "\"dateOfBirthday\":[1970,1,2]}"));
        }
}
