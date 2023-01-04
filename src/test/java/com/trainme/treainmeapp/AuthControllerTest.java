package com.trainme.treainmeapp.web;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trainme.treainmeapp.payload.request.LoginRequest;
import com.trainme.treainmeapp.payload.request.SignUpRequest;
import com.trainme.treainmeapp.security.JWTTokenProvider;
import com.trainme.treainmeapp.validations.ResponseErrorValidation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;

@ContextConfiguration(classes = { AuthController.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class AuthControllerTest {
        @Autowired
        private AuthController authController;

        @MockBean
        private JWTTokenProvider jWTTokenProvider;

        @MockBean
        private ResponseErrorValidation responseErrorValidation;

    /**
     * Method under test: {@link AuthController#authenticateUser(LoginRequest, BindingResult)}
     */
    @Test
    public void testAuthenticateUser() throws Exception {
        when(jWTTokenProvider.generateToken((Authentication) any())).thenReturn("token");
        when(responseErrorValidation.mapValidationService((BindingResult) any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword("pass");
        loginRequest.setUsername("username");
        String content = (new ObjectMapper()).writeValueAsString(loginRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(authController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    /**
     * Method under test: {@link AuthController#registerUser(SignUpRequest, BindingResult)}
     */
    @Test
    public void testRegisterUser() throws Exception {
        when(responseErrorValidation.mapValidationService((BindingResult) any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));

        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setConfirmPassword("pass");
        signUpRequest.setEmail("test.test@mail.io");
        signUpRequest.setPassword("pass");
        signUpRequest.setUsername("username");
        String content = (new ObjectMapper()).writeValueAsString(signUpRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(authController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }
}
