package uk.gov.companieshouse.uri.web.controller;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.eq;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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

    @Mock
    private ServletContext context;
    
    @Captor
    ArgumentCaptor<WebContext> contextCaptor;

    @BeforeEach
    protected void setUp() {
        testViewController = new ViewController(logger, companyService, templateEngine);
        companyDetails = new CompanyDetails();
        companyDetails.setCompanyNumber(COMPANY_NUMBER);
        when(companyService.getCompanyDetails(COMPANY_NUMBER)).thenReturn(companyDetails);
        when(request.getServletContext()).thenReturn(context);
    }

    @Test
    void html() {
        mockTemplateProcess(ViewController.HTML_VIEW);
        
        assertModelAndResponse(testViewController.html(COMPANY_NUMBER, request, response), 
                MediaType.TEXT_HTML_VALUE);
    }
    
    @Test
    void json() {
        mockTemplateProcess(ViewController.JSON_VIEW);
        
        assertModelAndResponse(testViewController.json(COMPANY_NUMBER, request, response), 
                MediaType.APPLICATION_JSON_VALUE);
    }
    
    @Test
    void rdf() {
        mockTemplateProcess(ViewController.RDF_VIEW);
        
        assertModelAndResponse(testViewController.rdf(COMPANY_NUMBER, request, response), 
                ViewController.APPLICATION_RDFXML);
    }
    
    @Test
    void xml() {
        mockTemplateProcess(ViewController.XML_VIEW);
        
        assertModelAndResponse(testViewController.xml(COMPANY_NUMBER, request, response), 
                MediaType.TEXT_XML_VALUE);
    }
    
    @Test
    void csv() {
        mockTemplateProcess(ViewController.CSV_VIEW);

        assertModelAndResponse(testViewController.csv(COMPANY_NUMBER, request, response), 
                ViewController.TEXT_CSV);
    }
    
    @Test
    void yaml() {
        mockTemplateProcess(ViewController.YAML_VIEW);

        assertModelAndResponse(testViewController.yaml(COMPANY_NUMBER, request, response), 
                ViewController.APPLICATION_YAML);
    }
    
    private void mockTemplateProcess(String viewName) {
        when(templateEngine.process(eq(viewName), contextCaptor.capture()))
                .thenReturn(viewName);
    }
    
    private void assertModelAndResponse(ResponseEntity<String> responseEntity, String contentType) {
        CompanyDetails companyDetails = (CompanyDetails) contextCaptor.getValue()
                .getVariable(ViewController.CONTEXT_VAR_NAME);
        assertEquals(COMPANY_NUMBER, companyDetails.getCompanyNumber());
        String responseContentType = responseEntity.getHeaders().getContentType().toString();
        assertEquals(contentType, responseContentType.split(";")[0]);
        assertEquals("charset=UTF-8", responseContentType.split(";")[1]);
        assertEquals("*",responseEntity.getHeaders().getAccessControlAllowOrigin());
        assertEquals("no-store, must-revalidate",responseEntity.getHeaders().getCacheControl());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
