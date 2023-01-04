package com.trainme.treainmeapp;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trainme.treainmeapp.domain.User;
import com.trainme.treainmeapp.dto.UserDTO;
import com.trainme.treainmeapp.facade.UserFacade;
import com.trainme.treainmeapp.services.UserService;
import com.trainme.treainmeapp.validations.ResponseErrorValidation;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

import com.trainme.treainmeapp.web.UserController;
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

@ContextConfiguration(classes = { UserController.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class UserControllerTest {
        @MockBean
        private ResponseErrorValidation responseErrorValidation;

        @Autowired
        private UserController userController;

        @MockBean
        private UserFacade userFacade;

        @MockBean
        private UserService userService;

        /**
         * Method under test: {@link UserController#getCurrentUser(Principal)}
         */
        @Test
        public void testGetCurrentUser() throws Exception {
                UserDTO userDTO = new UserDTO();
                userDTO.setDateOfBirthday(LocalDate.ofEpochDay(1L));
                userDTO.setId(133L);
                userDTO.setMembershipFrom(LocalDate.ofEpochDay(1L));
                userDTO.setMembershipTo(LocalDate.ofEpochDay(1L));
                userDTO.setMemberships(new HashSet<>());
                userDTO.setUsername("username");
                when(userFacade.userToUserDTO((User) any())).thenReturn(userDTO);

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
                when(userService.getCurrentUser((Principal) any())).thenReturn(user);
                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/user/");
                MockMvcBuilders.standaloneSetup(userController)
                                .build()
                                .perform(requestBuilder)
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                                .andExpect(MockMvcResultMatchers.content()
                                                .string(
                                                                "{\"id\":133,\"username\":\"username\",\"dateOfBirthday\":[1970,1,2],\"membershipFrom\":[1970,1,2],\"membershipTo"
                                                                                + "\":[1970,1,2],\"memberships\":[]}"));
        }

        /**
         * Method under test: {@link UserController#getCurrentUser(Principal)}
         */
        @Test
        public void testGetCurrentUser2() throws Exception {
                UserDTO userDTO = new UserDTO();
                userDTO.setDateOfBirthday(LocalDate.ofEpochDay(1L));
                userDTO.setId(133L);
                userDTO.setMembershipFrom(LocalDate.ofEpochDay(1L));
                userDTO.setMembershipTo(LocalDate.ofEpochDay(1L));
                userDTO.setMemberships(new HashSet<>());
                userDTO.setUsername("username");
                when(userFacade.userToUserDTO((User) any())).thenReturn(userDTO);

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
                when(userService.getCurrentUser((Principal) any())).thenReturn(user);
                SecurityMockMvcRequestBuilders.FormLoginRequestBuilder requestBuilder = SecurityMockMvcRequestBuilders
                                .formLogin();
                ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController)
                                .build()
                                .perform(requestBuilder);
                actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
        }

        /**
         * Method under test: {@link UserController#getUserProfile(String)}
         */
        @Test
        public void testGetUserProfile() throws Exception {
                UserDTO userDTO = new UserDTO();
                userDTO.setDateOfBirthday(LocalDate.ofEpochDay(1L));
                userDTO.setId(133L);
                userDTO.setMembershipFrom(LocalDate.ofEpochDay(1L));
                userDTO.setMembershipTo(LocalDate.ofEpochDay(1L));
                userDTO.setMemberships(new HashSet<>());
                userDTO.setUsername("username");
                when(userFacade.userToUserDTO((User) any())).thenReturn(userDTO);

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
                when(userService.getUserById((Long) any())).thenReturn(user);
                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/user/{userId}", "42");
                MockMvcBuilders.standaloneSetup(userController)
                                .build()
                                .perform(requestBuilder)
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                                .andExpect(MockMvcResultMatchers.content()
                                                .string(
                                                                "{\"id\":133,\"username\":\"username\",\"dateOfBirthday\":[1970,1,2],\"membershipFrom\":[1970,1,2],\"membershipTo"
                                                                                + "\":[1970,1,2],\"memberships\":[]}"));
        }

        /**
         * Method under test:
         * {@link UserController#updateUser(UserDTO, BindingResult, Principal)}
         */
        @Test
        @Ignore("TODO: Complete this test")
        public void testUpdateUser() throws Exception {
                // TODO: Complete this test.
                // Reason: R013 No inputs found that don't throw a trivial exception.
                // Diffblue Cover tried to run the arrange/act section, but the method under
                // test threw
                // com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Java 8
                // date/time type `java.time.LocalDate` not supported by default: add Module
                // "com.fasterxml.jackson.datatype:jackson-datatype-jsr310" to enable handling
                // (through reference chain:
                // com.trainme.treainmeapp.dto.UserDTO["dateOfBirthday"])
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

                MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.put("/api/user/")
                                .contentType(MediaType.APPLICATION_JSON);
                LocalDate dateOfBirthday = LocalDate.ofEpochDay(1L);

                UserDTO userDTO = new UserDTO();
                userDTO.setDateOfBirthday(dateOfBirthday);
                userDTO.setId(133L);
                userDTO.setMembershipFrom(LocalDate.ofEpochDay(1L));
                userDTO.setMembershipTo(LocalDate.ofEpochDay(1L));
                userDTO.setMemberships(new HashSet<>());
                userDTO.setUsername("username");
                MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                                .content((new ObjectMapper()).writeValueAsString(userDTO));
                MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);
        }
}
