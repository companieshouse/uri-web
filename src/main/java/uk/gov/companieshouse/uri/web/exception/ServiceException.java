package uk.gov.companieshouse.uri.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The class {@code ServiceException} is intended to abstract lower level
 * exceptions from being propagated up the call stack.
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) 
public class ServiceException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new {@code ServiceException} with a custom message and the specified
     * cause.
     *
     * @param message a custom message
     * @param cause the cause
     */
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new {@code ServiceException} with a custom message.
     *
     * @param message a custom message
     */
    public ServiceException(final String message) {
        super(message);
    }
}
