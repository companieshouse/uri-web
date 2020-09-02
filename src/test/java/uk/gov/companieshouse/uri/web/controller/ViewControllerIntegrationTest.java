package uk.gov.companieshouse.uri.web.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import uk.gov.companieshouse.uri.web.UriWebApplication;
import uk.gov.companieshouse.uri.web.model.Accounts;
import uk.gov.companieshouse.uri.web.model.Address;
import uk.gov.companieshouse.uri.web.model.CompanyDetails;
import uk.gov.companieshouse.uri.web.model.Returns;
import uk.gov.companieshouse.uri.web.model.SicCodes;
import uk.gov.companieshouse.uri.web.service.CompanyService;

@AutoConfigureMockMvc
@SpringBootTest(classes = UriWebApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class ViewControllerIntegrationTest {
    
    private static final String UTF8_CHARSET_SUFFIX = ";charset=UTF-8";
    private static final String COMPANY_NUMBER = "05448736";
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private CompanyService companyService;
    
    private CompanyDetails companyDetails;
    
    @BeforeEach
    protected void setUp() {
        companyDetails = new CompanyDetails();
        companyDetails.setRegisteredOfficeAddress(new Address());
        companyDetails.setAccounts(new Accounts());
        companyDetails.setReturns(new Returns());
        companyDetails.setSicCodes(new SicCodes());
        
        when(companyService.getCompanyDetails(COMPANY_NUMBER)).thenReturn(companyDetails);
    }
    
    @Test
    void noAcceptHeaderAndNoExtensionReturnsHTML() throws Exception { 
       mockMvc.perform(get("/doc/company/{company}", COMPANY_NUMBER))
       .andExpect(status().isOk())
       .andExpect(header().string(HttpHeaders.CONTENT_TYPE, 
               MediaType.TEXT_HTML_VALUE + UTF8_CHARSET_SUFFIX));
    }
    
    @Test
    void noAcceptHeaderAndHTMLExtensionReturnsHTML() throws Exception {
       mockMvc.perform(get("/doc/company/{company}.html", COMPANY_NUMBER))
       .andExpect(status().isOk())
       .andExpect(header().string(HttpHeaders.CONTENT_TYPE, 
               MediaType.TEXT_HTML_VALUE + UTF8_CHARSET_SUFFIX));
    }
    
    @Test
    void textHtmlAcceptHeaderAndNoExtensionReturnsHTML() throws Exception {
        ResultActions result = mockMvc.perform(get("/doc/company/{company}", COMPANY_NUMBER)
               .accept(MediaType.TEXT_HTML_VALUE));
  
        result.andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, 
                        MediaType.TEXT_HTML_VALUE + UTF8_CHARSET_SUFFIX));
    }
    
    @Test
    void noAcceptHeaderAndJSONExtensionReturnsJSON() throws Exception {
       mockMvc.perform(get("/doc/company/{company}.json", COMPANY_NUMBER))
       .andExpect(status().isOk())
       .andExpect(header().string(HttpHeaders.CONTENT_TYPE, 
               MediaType.APPLICATION_JSON_VALUE + UTF8_CHARSET_SUFFIX));
    }
    
    @Test
    void applicationJsonAcceptHeaderAndNoExtensionReturnsJSON() throws Exception {
        ResultActions result = mockMvc.perform(get("/doc/company/{company}", COMPANY_NUMBER)
               .accept(MediaType.APPLICATION_JSON_VALUE));
  
        result.andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, 
                        MediaType.APPLICATION_JSON_VALUE + UTF8_CHARSET_SUFFIX));
    }
    
    @Test
    void noAcceptHeaderAndRDFExtensionReturnsRDF() throws Exception {
       mockMvc.perform(get("/doc/company/{company}.rdf", COMPANY_NUMBER))
       .andExpect(status().isOk())
       .andExpect(header().string(HttpHeaders.CONTENT_TYPE, 
               ViewController.APPLICATION_RDFXML + UTF8_CHARSET_SUFFIX));
    }
    
    @Test
    void applicationRdfAcceptHeaderAndNoExtensionReturnsRDF() throws Exception {
        ResultActions result = mockMvc.perform(get("/doc/company/{company}", COMPANY_NUMBER)
               .accept(ViewController.APPLICATION_RDFXML));
  
        result.andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, 
                        ViewController.APPLICATION_RDFXML + UTF8_CHARSET_SUFFIX));
    }
    
    @Test
    void noAcceptHeaderAndXMLExtensionReturnsXML() throws Exception {
       mockMvc.perform(get("/doc/company/{company}.xml", COMPANY_NUMBER))
       .andExpect(status().isOk())
       .andExpect(header().string(HttpHeaders.CONTENT_TYPE, 
               MediaType.TEXT_XML_VALUE + UTF8_CHARSET_SUFFIX));
    }
    
    @Test
    void applicationXmlAcceptHeaderAndNoExtensionReturnsXML() throws Exception {
        ResultActions result = mockMvc.perform(get("/doc/company/{company}", COMPANY_NUMBER)
               .accept(MediaType.APPLICATION_XML_VALUE));
  
        result.andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, 
                        MediaType.TEXT_XML_VALUE + UTF8_CHARSET_SUFFIX));
    }

    @Test
    void applicationXxmlAcceptHeaderAndNoExtensionReturnsXML() throws Exception {
        ResultActions result = mockMvc.perform(get("/doc/company/{company}", COMPANY_NUMBER)
               .accept(ViewController.APPLICATION_XXML));
  
        result.andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, 
                        MediaType.TEXT_XML_VALUE + UTF8_CHARSET_SUFFIX));
    }

    @Test
    void noAcceptHeaderAndCSVExtensionReturnsCSV() throws Exception {
       mockMvc.perform(get("/doc/company/{company}.csv", COMPANY_NUMBER))
       .andExpect(status().isOk())
       .andExpect(header().string(HttpHeaders.CONTENT_TYPE, 
               ViewController.TEXT_CSV + UTF8_CHARSET_SUFFIX));
    }
    
    @Test
    void textCsvAcceptHeaderAndNoExtensionReturnsCSV() throws Exception {
        ResultActions result = mockMvc.perform(get("/doc/company/{company}", COMPANY_NUMBER)
               .accept(ViewController.TEXT_CSV));
  
        result.andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, 
                        ViewController.TEXT_CSV + UTF8_CHARSET_SUFFIX));
    }
    
    @Test
    void textCommaSeparatedValuesAcceptHeaderAndNoExtensionReturnsCSV() throws Exception {
        ResultActions result = mockMvc.perform(get("/doc/company/{company}", COMPANY_NUMBER)
               .accept(ViewController.TEXT_COMMA_SEPARATED_VALUES));
  
        result.andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, 
                        ViewController.TEXT_CSV + UTF8_CHARSET_SUFFIX));
    }
    
    @Test
    void applicationCsvAcceptHeaderAndNoExtensionReturnsCSV() throws Exception {
        ResultActions result = mockMvc.perform(get("/doc/company/{company}", COMPANY_NUMBER)
               .accept(ViewController.APPLICATION_CSV));
  
        result.andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, 
                        ViewController.TEXT_CSV + UTF8_CHARSET_SUFFIX));
    }
    
    @Test
    void applicationExcelAcceptHeaderAndNoExtensionReturnsCSV() throws Exception {
        ResultActions result = mockMvc.perform(get("/doc/company/{company}", COMPANY_NUMBER)
               .accept(ViewController.APPLICATION_EXCEL));
  
        result.andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, 
                        ViewController.TEXT_CSV + UTF8_CHARSET_SUFFIX));
    }
    
    @Test
    void noAcceptHeaderAndYAMLExtensionReturnsYAML() throws Exception {
       mockMvc.perform(get("/doc/company/{company}.yaml", COMPANY_NUMBER))
       .andExpect(status().isOk())
       .andExpect(header().string(HttpHeaders.CONTENT_TYPE, 
               ViewController.APPLICATION_YAML + UTF8_CHARSET_SUFFIX));
    }
    
    @Test
    void applicationYamlAcceptHeaderAndNoExtensionReturnsYAML() throws Exception { 
        ResultActions result = mockMvc.perform(get("/doc/company/{company}", COMPANY_NUMBER)
               .accept(ViewController.APPLICATION_YAML));
  
        result.andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, 
                        ViewController.APPLICATION_YAML + UTF8_CHARSET_SUFFIX));
    }
    
    @Test
    void applicationXyamlAcceptHeaderAndNoExtensionReturnsYAML() throws Exception {  
        ResultActions result = mockMvc.perform(get("/doc/company/{company}", COMPANY_NUMBER)
               .accept(ViewController.APPLICATION_XYAML));
  
        result.andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, 
                        ViewController.APPLICATION_YAML + UTF8_CHARSET_SUFFIX));
    }
    
    @Test
    void textYamlAcceptHeaderAndNoExtensionReturnsYAML() throws Exception {  
        ResultActions result = mockMvc.perform(get("/doc/company/{company}", COMPANY_NUMBER)
               .accept(ViewController.TEXT_YAML));
  
        result.andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, 
                        ViewController.APPLICATION_YAML + UTF8_CHARSET_SUFFIX));
    }
    
    @Test
    void textXyamlExcelAcceptHeaderAndNoExtensionReturnsYAML() throws Exception {   
        ResultActions result = mockMvc.perform(get("/doc/company/{company}", COMPANY_NUMBER)
               .accept(ViewController.TEXT_XYAML));
  
        result.andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, 
                        ViewController.APPLICATION_YAML + UTF8_CHARSET_SUFFIX));
    }
    
    @Test
    void confirmCacheControlHeaderInResponse() throws Exception {    
        ResultActions result = mockMvc.perform(get("/doc/company/{company}", COMPANY_NUMBER));
  
        result.andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, 
                        MediaType.TEXT_HTML_VALUE + UTF8_CHARSET_SUFFIX))
                .andExpect(header().string(HttpHeaders.CACHE_CONTROL, "no-store, must-revalidate"));
    }
    
    @Test
    void confirmCORSHeaderInResponse() throws Exception {
        ResultActions result = mockMvc.perform(get("/doc/company/{company}", COMPANY_NUMBER));
  
        result.andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, 
                        MediaType.TEXT_HTML_VALUE + UTF8_CHARSET_SUFFIX))
                .andExpect(header().string(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*"));
    }
}
