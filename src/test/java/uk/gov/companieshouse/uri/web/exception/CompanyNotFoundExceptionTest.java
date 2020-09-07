package uk.gov.companieshouse.uri.web.exception;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CompanyNotFoundExceptionTest {

    private static final String EXCEPTION_MESSAGE = "message";
    
    @Test
    void companyNotFoundExceptionWithCause() {
        CompanyNotFoundException exception = new CompanyNotFoundException(EXCEPTION_MESSAGE, new NullPointerException());
        
        assertEquals("message", exception.getMessage());
        assertThat(exception.getCause(), instanceOf(NullPointerException.class));
    }
    
    @Test
    void companyNotFoundExceptionWithoutCause() {
        CompanyNotFoundException exception = new CompanyNotFoundException(EXCEPTION_MESSAGE);
        
        assertEquals("message", exception.getMessage());
    }
}