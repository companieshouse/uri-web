package uk.gov.companieshouse.uri.web.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.net.URISyntaxException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class RedirectControllerTest {

    private static final String COMPANY_NUMBER = "12345678";
    private static final String INVALID_REDIRECT_URI_PREFIX = "xttp://test-server/doc/company";
    private static final String VALID_REDIRECT_URI_PREFIX = "http://test-server/doc/company";
    
    private RedirectController testRedirectController;

    @Rule
    public final ExpectedException exception = ExpectedException.none();
    
    @Mock
    private HttpServletRequest request;
    
    @Mock
    private HttpServletResponse response;

    @BeforeEach
    protected void setUp() {
        testRedirectController = new RedirectController();
    }

    @Test
    void redirectWithValidRedirectPrefix() throws URISyntaxException {     
        ReflectionTestUtils.setField(testRedirectController, "redirectURIPrefix", VALID_REDIRECT_URI_PREFIX);
        when(request.getRequestURI()).thenReturn("/id/company/" + COMPANY_NUMBER);
        
        ResponseEntity<String> responseEntity = testRedirectController.redirect(request, response);
        
        assertEquals(HttpStatus.SEE_OTHER, responseEntity.getStatusCode());
        
        HttpHeaders headers = responseEntity.getHeaders();
        String locationHeader = headers.get("location").get(0);
        assertEquals(VALID_REDIRECT_URI_PREFIX + "/" + COMPANY_NUMBER, locationHeader);
    }
    
    @Test
    void redirectWithInValidRedirectPrefix() throws URISyntaxException {     
        ReflectionTestUtils.setField(testRedirectController, "redirectURIPrefix", INVALID_REDIRECT_URI_PREFIX);
        when(request.getRequestURI()).thenReturn("/id/company/" + COMPANY_NUMBER);
        
        exception.expect(URISyntaxException.class);
        testRedirectController.redirect(request, response);
    }
}
