package uk.gov.companieshouse.uri.web.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;

import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.uri.web.transformer.CompanyDetailsTransformer;

@ExtendWith(MockitoExtension.class)
class CompanyServiceImplTest {

    private static final String COMPANY_NUMBER = "12345678";
    
    private CompanyService testService;

    @Mock
    private ApiService apiService;

    @Mock
    private CompanyDetailsTransformer companyDetailsTransformer;
    
    @Mock
    private Logger logger;

    @BeforeEach
    protected void setUp() throws ApiErrorResponseException, URIValidationException {
        testService = new CompanyServiceImpl(apiService, companyDetailsTransformer, logger);
    }

    @Test
    void getCompanyDetails() {
        CompanyProfileApi companyProfileApi = new CompanyProfileApi();
        when(apiService.getCompanyProfile(COMPANY_NUMBER)).thenReturn(companyProfileApi);

        testService.getCompanyDetails(COMPANY_NUMBER);
        
        verify(apiService, times(1)).getCompanyProfile(COMPANY_NUMBER);
        verify(companyDetailsTransformer, times(1)).profileApiToDetails(companyProfileApi);
    }
}