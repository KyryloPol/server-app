package com.trainme.treainmeapp.security;

import com.trainme.treainmeapp.payload.response.InvalidLoginResponse;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * If the user is not authenticated, then send a 401 Unauthorized response
 */
@Component
public class JWTAuthentificationEntryPoint implements AuthenticationEntryPoint {

    /**
     * If the user is not authenticated, then return a 401 status code and a JSON response with a message saying that the
     * user is not authenticated
     *
     * @param httpServletRequest The request object that was made to the server.
     * @param httpServletResponse This is the response object that we will use to send the response to the client.
     * @param e The exception thrown by the authentication manager.
     */
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        InvalidLoginResponse loginResponse = new InvalidLoginResponse();
        String jsonLoginResponse = new Gson().toJson(loginResponse);
        httpServletResponse.setContentType(SecurityConstants.CONTENT_TYPE);
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.getWriter().println(jsonLoginResponse);
    }
}
