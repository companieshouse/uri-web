package uk.gov.companieshouse.uri.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) 
public class CompanyNotFoundException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new {@code CompanyNotFoundException} with a custom message and the specified
     * cause.
     *
     * @param message a custom message
     * @param cause the cause
     */
    public CompanyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new {@code CompanyNotFoundException} with a custom message.
     *
     * @param message a custom message
     */
    public CompanyNotFoundException(final String message) {
        super(message);
    }
}
