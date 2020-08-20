package uk.gov.companieshouse.uri.web.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import uk.gov.companieshouse.api.model.charges.ChargesApi;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;

import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.uri.web.exception.ServiceException;
import uk.gov.companieshouse.uri.web.model.CompanyDetails;
import uk.gov.companieshouse.uri.web.model.MortgageTotals;
import uk.gov.companieshouse.uri.web.transformer.CompanyDetailsTransformer;

@ExtendWith(MockitoExtension.class)
class CompanyServiceImplTest {

    private static final String COMPANY_NUMBER = "12345678";
    
    private CompanyService testCompanyService;

    @Mock
    private ApiService apiService;

    @Mock
    private CompanyDetailsTransformer companyDetailsTransformer;
    
    @Mock
    private Logger logger;

    @BeforeEach
    protected void setUp() {
        testCompanyService = new CompanyServiceImpl(apiService, companyDetailsTransformer, logger);
    }

    @Test
    void getCompanyDetailsWithoutCharges() {
        CompanyProfileApi companyProfileApi = new CompanyProfileApi();
        when(apiService.getCompanyProfile(COMPANY_NUMBER)).thenReturn(companyProfileApi);
        when(companyDetailsTransformer.profileApiToDetails(companyProfileApi)).thenReturn(new CompanyDetails());
        
        testCompanyService.getCompanyDetails(COMPANY_NUMBER);
        
        verify(apiService, times(1)).getCompanyProfile(COMPANY_NUMBER);
        verify(companyDetailsTransformer, times(1)).profileApiToDetails(companyProfileApi);
    }
    
    @Test
    void getCompanyDetailsWithServiceException() throws Exception {
        CompanyProfileApi companyProfileApi = new CompanyProfileApi();
        when(apiService.getCompanyProfile(COMPANY_NUMBER)).thenReturn(companyProfileApi);

        when(companyDetailsTransformer.profileApiToDetails(companyProfileApi)).thenThrow(RuntimeException.class);
        
        ServiceException exception = assertThrows(ServiceException.class, () -> {
            testCompanyService.getCompanyDetails(COMPANY_NUMBER);
        });
        
        assertEquals("Error transforming company profile", exception.getMessage());
        verify(apiService, times(1)).getCompanyProfile(COMPANY_NUMBER);
        verify(companyDetailsTransformer, times(1)).profileApiToDetails(companyProfileApi);
    }
    
    @Test
    void getCompanyDetailsWithCharges() {
        CompanyProfileApi companyProfileApi = new CompanyProfileApi();
        companyProfileApi.setHasCharges(true);
        when(apiService.getCompanyProfile(COMPANY_NUMBER)).thenReturn(companyProfileApi);
        CompanyDetails companyDetails = new CompanyDetails();
        companyDetails.setHasCharges(true);
        when(companyDetailsTransformer.profileApiToDetails(companyProfileApi)).thenReturn(companyDetails);
        
        ChargesApi chargesApi = new ChargesApi();
        when(apiService.getCharges(COMPANY_NUMBER)).thenReturn(chargesApi);
        when(companyDetailsTransformer.chargesApiToMortgageTotals(chargesApi)).thenReturn(new MortgageTotals());
        
        testCompanyService.getCompanyDetails(COMPANY_NUMBER);
        
        verify(apiService, times(1)).getCompanyProfile(COMPANY_NUMBER);
        verify(companyDetailsTransformer, times(1)).profileApiToDetails(companyProfileApi);
        verify(apiService, times(1)).getCharges(COMPANY_NUMBER);
        verify(companyDetailsTransformer, times(1)).chargesApiToMortgageTotals(chargesApi);
    }
    
    @Test
    void getCompanyDetailsWithChargesAndServiceException() throws Exception {
        CompanyProfileApi companyProfileApi = new CompanyProfileApi();
        when(apiService.getCompanyProfile(COMPANY_NUMBER)).thenReturn(companyProfileApi);
        CompanyDetails companyDetails = new CompanyDetails();
        companyDetails.setHasCharges(true);
        when(companyDetailsTransformer.profileApiToDetails(companyProfileApi)).thenReturn(companyDetails);

        ChargesApi chargesApi = new ChargesApi();
        when(apiService.getCharges(COMPANY_NUMBER)).thenReturn(chargesApi);
        when(companyDetailsTransformer.chargesApiToMortgageTotals(chargesApi)).thenThrow(RuntimeException.class);
        
        ServiceException exception = assertThrows(ServiceException.class, () -> {
            testCompanyService.getCompanyDetails(COMPANY_NUMBER);
        });
        
        assertEquals("Error transforming charges", exception.getMessage());
        verify(apiService, times(1)).getCompanyProfile(COMPANY_NUMBER);
        verify(companyDetailsTransformer, times(1)).profileApiToDetails(companyProfileApi);
        verify(apiService, times(1)).getCharges(COMPANY_NUMBER);
        verify(companyDetailsTransformer, times(1)).chargesApiToMortgageTotals(chargesApi);
    }
}