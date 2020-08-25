package uk.gov.companieshouse.uri.web.controller;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;

import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.uri.web.model.CompanyDetails;
import uk.gov.companieshouse.uri.web.service.CompanyService;

@ExtendWith(MockitoExtension.class)
class ViewControllerTest {

    private static final String COMPANY_NUMBER = "12345678";
    
    private ViewController testViewController;

    private CompanyDetails companyDetails;
    
    @Mock
    private CompanyService companyService;
    
    @Mock
    private Logger logger;
    
    @Mock
    private ITemplateEngine templateEngine;
    
    @Mock
    private HttpServletRequest request;
    
    @Mock
    private HttpServletResponse response;
    
    @Captor
    ArgumentCaptor<WebContext> contextCaptor;

    @BeforeEach
    protected void setUp() {
        testViewController = new ViewController(logger, companyService, templateEngine);
        companyDetails = new CompanyDetails();
        companyDetails.setCompanyNumber(COMPANY_NUMBER);
        when(companyService.getCompanyDetails(COMPANY_NUMBER)).thenReturn(companyDetails);
    }

    @Test
    void html() {
        mockTemplateProcess(ViewController.HTML_VIEW);
        
        ResponseEntity<String> responseEntity = testViewController.html(COMPANY_NUMBER, request, response);
        
        CompanyDetails companyDetails = (CompanyDetails) contextCaptor.getValue()
                .getVariable(ViewController.CONTEXT_VAR_NAME);
        assertEquals(COMPANY_NUMBER, companyDetails.getCompanyNumber());
        assertEquals(MediaType.TEXT_HTML, responseEntity.getHeaders().getContentType());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    
    @Test
    void json() {
        mockTemplateProcess(ViewController.JSON_VIEW);
        
        ResponseEntity<String> responseEntity = testViewController.json(COMPANY_NUMBER, request, response);
        
        CompanyDetails companyDetails = (CompanyDetails) contextCaptor.getValue()
                .getVariable(ViewController.CONTEXT_VAR_NAME);
        assertEquals(COMPANY_NUMBER, companyDetails.getCompanyNumber());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    
    private void mockTemplateProcess(String viewName) {
        when(templateEngine.process(eq(viewName), contextCaptor.capture()))
                .thenReturn(viewName);
    }
}
