package uk.gov.companieshouse.uri.web.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.uri.web.model.CompanyDetails;
import uk.gov.companieshouse.uri.web.service.CompanyService;

@ExtendWith(MockitoExtension.class)
class ViewControllerTest {

    private static final String COMPANY_NUMBER = "12345678";
    
    private ViewController testViewController;

    private CompanyDetails companyDetails = new CompanyDetails();
    
    @Mock
    private CompanyService companyService;
    
    @Mock
    private Logger logger;
    
    @Mock
    private Model model;

    @BeforeEach
    protected void setUp() {
        testViewController = new ViewController(logger, companyService);
        
        when(companyService.getCompanyDetails(COMPANY_NUMBER)).thenReturn(companyDetails);
    }

    @Test
    void html() {
        String viewName = testViewController.html(model, COMPANY_NUMBER);
        
        assertEquals("legacy_style_html", viewName);
        verify(model, times(1)).addAttribute("company", companyDetails);
    }
}
