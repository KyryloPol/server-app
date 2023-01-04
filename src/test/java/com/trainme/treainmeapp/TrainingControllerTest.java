package com.trainme.treainmeapp;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trainme.treainmeapp.domain.Trainer;
import com.trainme.treainmeapp.domain.Training;
import com.trainme.treainmeapp.domain.User;
import com.trainme.treainmeapp.dto.TrainingForUserDTO;
import com.trainme.treainmeapp.facade.TrainingFacade;
import com.trainme.treainmeapp.payload.request.TrainingRequest;
import com.trainme.treainmeapp.services.TrainingService;
import com.trainme.treainmeapp.validations.ResponseErrorValidation;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

import com.trainme.treainmeapp.web.TrainingController;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;

@ContextConfiguration(classes = { TrainingController.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class TrainingControllerTest {
        @MockBean
        private ResponseErrorValidation responseErrorValidation;

        @Autowired
        private TrainingController trainingController;

        @MockBean
        private TrainingFacade trainingFacade;

        @MockBean
        private TrainingService trainingService;

        /**
         * Method under test:
         * {@link TrainingController#createTraining(TrainingRequest, BindingResult, Principal)}
         */
        @Test
        @Ignore("TODO: Complete this test")
        public void testCreateTraining() throws Exception {
                // TODO: Complete this test.
                // Reason: R013 No inputs found that don't throw a trivial exception.
                // Diffblue Cover tried to run the arrange/act section, but the method under
                // test threw
                // com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Java 8
                // date/time type `java.time.LocalDateTime` not supported by default: add Module
                // "com.fasterxml.jackson.datatype:jackson-datatype-jsr310" to enable handling
                // (through reference chain:
                // com.trainme.treainmeapp.payload.request.TrainingRequest["timeFrom"])
                // at
                // com.fasterxml.jackson.databind.exc.InvalidDefinitionException.from(InvalidDefinitionException.java:77)
                // at
                // com.fasterxml.jackson.databind.SerializerProvider.reportBadDefinition(SerializerProvider.java:1300)
                // at
                // com.fasterxml.jackson.databind.ser.impl.UnsupportedTypeSerializer.serialize(UnsupportedTypeSerializer.java:35)
                // at
                // com.fasterxml.jackson.databind.ser.BeanPropertyWriter.serializeAsField(BeanPropertyWriter.java:728)
                // at
                // com.fasterxml.jackson.databind.ser.std.BeanSerializerBase.serializeFields(BeanSerializerBase.java:774)
                // at
                // com.fasterxml.jackson.databind.ser.BeanSerializer.serialize(BeanSerializer.java:178)
                // at
                // com.fasterxml.jackson.databind.ser.DefaultSerializerProvider._serialize(DefaultSerializerProvider.java:480)
                // at
                // com.fasterxml.jackson.databind.ser.DefaultSerializerProvider.serializeValue(DefaultSerializerProvider.java:319)
                // at
                // com.fasterxml.jackson.databind.ObjectMapper._writeValueAndClose(ObjectMapper.java:4568)
                // at
                // com.fasterxml.jackson.databind.ObjectMapper.writeValueAsString(ObjectMapper.java:3821)
                // See https://diff.blue/R013 to resolve this issue.

                MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/api/training/")
                                .contentType(MediaType.APPLICATION_JSON);

                TrainingRequest trainingRequest = new TrainingRequest();
                trainingRequest.setPlace("Room 404");
                trainingRequest.setTimeFrom(LocalDateTime.of(1, 1, 1, 1, 1));
                trainingRequest.setTimeTo(LocalDateTime.of(1, 1, 1, 1, 1));
                trainingRequest.setTrainerId(133L);
                MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                                .content((new ObjectMapper()).writeValueAsString(trainingRequest));
                MockMvcBuilders.standaloneSetup(trainingController).build().perform(requestBuilder);
        }

        /**
         * Method under test:
         * {@link TrainingController#deleteTraining(String, Principal)}
         */
        @Test
        public void testDeleteTraining() throws Exception {
                doNothing().when(trainingService).deleteTraining((Long) any(), (Principal) any());
                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                                .delete("/api/training/{trainingId}", "42");
                MockMvcBuilders.standaloneSetup(trainingController)
                                .build()
                                .perform(requestBuilder)
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                                .andExpect(MockMvcResultMatchers.content()
                                                .string("{\"message\":\"Training was deleted\"}"));
        }

        /**
         * Method under test:
         * {@link TrainingController#deleteTraining(String, Principal)}
         */
        @Test
        public void testDeleteTraining2() throws Exception {
                doNothing().when(trainingService).deleteTraining((Long) any(), (Principal) any());
                SecurityMockMvcRequestBuilders.FormLoginRequestBuilder requestBuilder = SecurityMockMvcRequestBuilders
                                .formLogin();
                ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(trainingController)
                                .build()
                                .perform(requestBuilder);
                actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
        }

    /**
     * Method under test: {@link TrainingController#getAllTrainingsForUser(String, Principal)}
     */
    @Test
    public void testGetAllTrainingsForUser() throws Exception {
        when(trainingService.getAllTrainingsForUser((Principal) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/training/");
        MockMvcBuilders.standaloneSetup(trainingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

        /**
         * Method under test:
         * {@link TrainingController#getAllTrainingsForUser(String, Principal)}
         */
        @Test
        public void testGetAllTrainingsForUser2() throws Exception {
                TrainingForUserDTO trainingForUserDTO = new TrainingForUserDTO();
                trainingForUserDTO.setId(133L);
                trainingForUserDTO.setPlace("Room 404");
                trainingForUserDTO.setTimeFrom(LocalDateTime.of(1, 1, 1, 1, 1));
                trainingForUserDTO.setTimeTo(LocalDateTime.of(1, 1, 1, 1, 1));
                trainingForUserDTO.setTrainerUsername("username");
                when(trainingFacade.trainingToTrainingDTOForUser((Training) any())).thenReturn(trainingForUserDTO);

                Trainer trainer = new Trainer();
                trainer.setDateOfBirthday(LocalDate.ofEpochDay(1L));
                trainer.setEmail("test.test@mail.io");
                trainer.setId(133L);
                trainer.setImageModels(new ArrayList<>());
                trainer.setPriceForMonthTraining("?");
                trainer.setPriceForOneTraining("?");
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
                training.setPlace("?");
                training.setTimeFrom(LocalDateTime.of(1, 1, 1, 1, 1));
                training.setTimeTo(LocalDateTime.of(1, 1, 1, 1, 1));
                training.setTrainer(trainer);
                training.setUser(user);

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

                Training training1 = new Training();
                training1.setId(133L);
                training1.setPlace("Room 404");
                training1.setTimeFrom(LocalDateTime.of(1, 1, 1, 1, 1));
                training1.setTimeTo(LocalDateTime.of(1, 1, 1, 1, 1));
                training1.setTrainer(trainer1);
                training1.setUser(user1);

                ArrayList<Training> trainingList = new ArrayList<>();
                trainingList.add(training1);
                trainingList.add(training);
                when(trainingService.getAllTrainingsForUser((Principal) any())).thenReturn(trainingList);
                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/training/");
                MockMvcBuilders.standaloneSetup(trainingController)
                                .build()
                                .perform(requestBuilder)
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                                .andExpect(MockMvcResultMatchers.content()
                                                .string(
                                                                "[{\"id\":133,\"place\":\"Room 404\",\"timeTo\":[1,1,1,1,1],\"timeFrom\":[1,1,1,1,1],\"trainerUsername\":\"username\"},"
                                                                                + "{\"id\":133,\"place\":\"Room 404\",\"timeTo\":[1,1,1,1,1],\"timeFrom\":[1,1,1,1,1],\"trainerUsername\":\"username\"}]"));
        }

    /**
     * Method under test: {@link TrainingController#getAllTrainingForTrainer(String)}
     */
    @Test
    public void testGetAllTrainingForTrainer() throws Exception {
        when(trainingService.getAllTrainingsForTrainer((Long) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/training/trainer/{trainerId}",
                "42");
        MockMvcBuilders.standaloneSetup(trainingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link TrainingController#getNearestTrainingForUser(Principal)}
     */
    @Test
    public void testGetNearestTrainingForUser() throws Exception {
        when(trainingService.getAllTrainingsForUser((Principal) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/training/nearest");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(trainingController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(417));
    }

        /**
         * Method under test:
         * {@link TrainingController#getTrainingByIdAndUser(String, Principal)}
         */
        @Test
        public void testGetTrainingByIdAndUser() throws Exception {
                TrainingForUserDTO trainingForUserDTO = new TrainingForUserDTO();
                trainingForUserDTO.setId(133L);
                trainingForUserDTO.setPlace("Room 404");
                trainingForUserDTO.setTimeFrom(LocalDateTime.of(1, 1, 1, 1, 1));
                trainingForUserDTO.setTimeTo(LocalDateTime.of(1, 1, 1, 1, 1));
                trainingForUserDTO.setTrainerUsername("username");
                when(trainingFacade.trainingToTrainingDTOForUser((Training) any())).thenReturn(trainingForUserDTO);

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
                when(trainingService.getTraining((Long) any(), (Principal) any())).thenReturn(training);
                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/training/{trainingId}",
                                "42");
                MockMvcBuilders.standaloneSetup(trainingController)
                                .build()
                                .perform(requestBuilder)
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                                .andExpect(MockMvcResultMatchers.content()
                                                .string(
                                                                "{\"id\":133,\"place\":\"Room 404\",\"timeTo\":[1,1,1,1,1],\"timeFrom\":[1,1,1,1,1],\"trainerUsername\":\"username\"}"));
        }

        /**
         * Method under test:
         * {@link TrainingController#updateTraining(String, TrainingForUserDTO, BindingResult, Principal)}
         */
        @Test
        @Ignore("TODO: Complete this test")
        public void testUpdateTraining() throws Exception {
                // TODO: Complete this test.
                // Reason: R013 No inputs found that don't throw a trivial exception.
                // Diffblue Cover tried to run the arrange/act section, but the method under
                // test threw
                // com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Java 8
                // date/time type `java.time.LocalDateTime` not supported by default: add Module
                // "com.fasterxml.jackson.datatype:jackson-datatype-jsr310" to enable handling
                // (through reference chain:
                // com.trainme.treainmeapp.dto.TrainingForUserDTO["timeTo"])
                // at
                // com.fasterxml.jackson.databind.exc.InvalidDefinitionException.from(InvalidDefinitionException.java:77)
                // at
                // com.fasterxml.jackson.databind.SerializerProvider.reportBadDefinition(SerializerProvider.java:1300)
                // at
                // com.fasterxml.jackson.databind.ser.impl.UnsupportedTypeSerializer.serialize(UnsupportedTypeSerializer.java:35)
                // at
                // com.fasterxml.jackson.databind.ser.BeanPropertyWriter.serializeAsField(BeanPropertyWriter.java:728)
                // at
                // com.fasterxml.jackson.databind.ser.std.BeanSerializerBase.serializeFields(BeanSerializerBase.java:774)
                // at
                // com.fasterxml.jackson.databind.ser.BeanSerializer.serialize(BeanSerializer.java:178)
                // at
                // com.fasterxml.jackson.databind.ser.DefaultSerializerProvider._serialize(DefaultSerializerProvider.java:480)
                // at
                // com.fasterxml.jackson.databind.ser.DefaultSerializerProvider.serializeValue(DefaultSerializerProvider.java:319)
                // at
                // com.fasterxml.jackson.databind.ObjectMapper._writeValueAndClose(ObjectMapper.java:4568)
                // at
                // com.fasterxml.jackson.databind.ObjectMapper.writeValueAsString(ObjectMapper.java:3821)
                // See https://diff.blue/R013 to resolve this issue.

                MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders
                                .put("/api/training/{trainingId}", "42")
                                .contentType(MediaType.APPLICATION_JSON);

                TrainingForUserDTO trainingForUserDTO = new TrainingForUserDTO();
                trainingForUserDTO.setId(133L);
                trainingForUserDTO.setPlace("Room 404");
                trainingForUserDTO.setTimeFrom(LocalDateTime.of(1, 1, 1, 1, 1));
                trainingForUserDTO.setTimeTo(LocalDateTime.of(1, 1, 1, 1, 1));
                trainingForUserDTO.setTrainerUsername("username");
                MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                                .content((new ObjectMapper()).writeValueAsString(trainingForUserDTO));
                MockMvcBuilders.standaloneSetup(trainingController).build().perform(requestBuilder);
        }
}
