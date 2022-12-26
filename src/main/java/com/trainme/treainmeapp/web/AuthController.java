package com.trainme.treainmeapp.web;


import com.trainme.treainmeapp.payload.request.LoginRequest;
import com.trainme.treainmeapp.payload.request.SignUpRequest;
import com.trainme.treainmeapp.payload.response.JWTTokenSuccessResponse;
import com.trainme.treainmeapp.payload.response.MessageResponse;
import com.trainme.treainmeapp.security.JWTTokenProvider;
import com.trainme.treainmeapp.security.SecurityConstants;
import com.trainme.treainmeapp.services.UserService;
import com.trainme.treainmeapp.validations.ResponseErrorValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


//api/auth/signup
//Когда пользователь пытается зарегистрироваться он передает объект с полями  (SignUpRequest) при регистрации пользователя.
// Если нет ошибок , то создаем нового пользователя с помощью сервиса, который содает в базе нового пользователя
// При авторизации получаем объект, проводим валидацию б возвращаем ошибкиб если нет ошибок созадем секюрити котекст, генерируем токен(задаем все данные юзера) и передаем на клиент, сохраняем токен в браюзере

/**
 * It contains two endpoints, one for registering a user and one for logging in
 */
@CrossOrigin
@RestController
@RequestMapping("/api/auth")
@PreAuthorize("permitAll()")
public class AuthController {
    // The above code is injecting the dependencies into the controller.
    @Autowired
    private JWTTokenProvider jwtTokenProvider;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private ResponseErrorValidation responseErrorValidation;
    @Autowired
    private UserService userService;
    /**
     * The function takes in a username and password, authenticates the user, and returns a JWT token
     *
     * @param loginRequest The request body that contains the username and password.
     * @param bindingResult This is used to validate the request body.
     * @return A JWT token
     */
    @PostMapping("/signin")
    public ResponseEntity<Object> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult) {

        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = SecurityConstants.TOKEN_PREFIX + jwtTokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTTokenSuccessResponse(true, jwt));
    }
    /**
     * It creates a new user.
     *
     * @param signupRequest The request body that contains the user's information.
     * @param bindingResult This is used to validate the request body.
     * @return A Response Entity of any Object for error handling
     */
    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody SignUpRequest signupRequest, BindingResult bindingResult) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        userService.createUser(signupRequest);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

}
