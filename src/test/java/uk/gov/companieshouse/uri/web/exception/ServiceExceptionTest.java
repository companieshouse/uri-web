package uk.gov.companieshouse.uri.web.exception;

import static org.hamcrest.CoreMatchers.instanceOf;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ServiceExceptionTest {

    private static final String EXCEPTION_MESSAGE = "message";
    
    @Test
    void serviceExceptionWithCause() {
        ServiceException serviceException = new ServiceException(EXCEPTION_MESSAGE, new NullPointerException());
        
        assertEquals("message", serviceException.getMessage());
        assertThat(serviceException.getCause(), instanceOf(NullPointerException.class));
    }
    
    @Test
    void serviceExceptionWithoutCause() {
        ServiceException serviceException = new ServiceException(EXCEPTION_MESSAGE);
        
        assertEquals("message", serviceException.getMessage());
    }
}