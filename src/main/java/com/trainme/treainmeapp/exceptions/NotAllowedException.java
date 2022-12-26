package com.trainme.treainmeapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This class is a custom exception class that extends the RuntimeException class.
 * <p>
 * The @ResponseStatus annotation is used to map the exception to a specific HTTP status code.
 * In that case it is code 403: Forbidden Request
 * <p>
 * The constructor of the class takes a String argument that is used to set the message of the exception.
 * <p>
 * The message is then used to display the error message to the user
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class NotAllowedException extends RuntimeException {
    public NotAllowedException(String not_allowed_to_do_that) {
        super(not_allowed_to_do_that);
    }
}
