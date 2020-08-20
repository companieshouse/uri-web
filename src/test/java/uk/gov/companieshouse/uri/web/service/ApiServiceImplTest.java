package uk.gov.companieshouse.uri.web.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import uk.gov.companieshouse.api.ApiClient;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.company.CompanyResourceHandler;
import uk.gov.companieshouse.api.handler.company.request.CompanyGet;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.http.ApiKeyHttpClient;
import uk.gov.companieshouse.api.model.ApiResponse;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;

import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.uri.web.exception.ServiceException;

@ExtendWith(MockitoExtension.class)
class ApiServiceImplTest {

    private static final String GET_COMPANY_URI_PRFIX = "/company/";
    private static final String CHS_API_KEY = "bu8G-WVuMIcMA467mX7M1W03SGcti6LP5Bod2yqX";
    private static final String COMPANY_NUMBER = "05448736";
    
    private ApiService testApiService;

    @Mock
    private Logger logger;
    
    @Mock
    private ApiClient mockApiClient;
    
    @Mock
    private CompanyResourceHandler companyResourceHandler;
    
    @Mock
    private CompanyGet companyGet;
    
    @Mock
    private ApiResponse<CompanyProfileApi> apiResponse;

    @BeforeEach
    protected void setUp() throws ApiErrorResponseException, URIValidationException {
        testApiService = new ApiServiceImpl(logger) {
            public ApiClient getApiClient() {
                return mockApiClient;
            }
        };
    }

    @Test
    void getCompanyDetails() throws ApiErrorResponseException, URIValidationException {
        mockCompanyProfileApiCall();
        CompanyProfileApi mockCompanyProfileApi = new CompanyProfileApi();
        mockCompanyProfileApi.setCompanyNumber(COMPANY_NUMBER);
        when(apiResponse.getData()).thenReturn(mockCompanyProfileApi);
        
        CompanyProfileApi companyProfileApi = testApiService.getCompanyProfile(COMPANY_NUMBER);
        
        assertEquals(COMPANY_NUMBER, companyProfileApi.getCompanyNumber());
        verify(mockApiClient, times(1)).company();
        verify(companyResourceHandler,times(1)).get(GET_COMPANY_URI_PRFIX + COMPANY_NUMBER);
        verify(companyGet, times(1)).execute();
    }
    
    @Test
    void getCompanyDetailsApiErrorResponseException() throws ApiErrorResponseException, URIValidationException {
        mockCompanyProfileApiCall();
        when(companyGet.execute()).thenThrow(ApiErrorResponseException.class);
        
        ServiceException exception = assertThrows(ServiceException.class, () -> {
            testApiService.getCompanyProfile(COMPANY_NUMBER);
        });

        assertEquals("Error retrieving company profile", exception.getMessage());
        verify(mockApiClient, times(1)).company();
        verify(companyResourceHandler,times(1)).get(GET_COMPANY_URI_PRFIX + COMPANY_NUMBER);
        verify(companyGet, times(1)).execute();
    }
    
    @Test
    void getCompanyDetailsURIValidationException() throws ApiErrorResponseException, URIValidationException {
        mockCompanyProfileApiCall();
        when(companyGet.execute()).thenThrow(URIValidationException.class);
        
        ServiceException exception = assertThrows(ServiceException.class, () -> {
            testApiService.getCompanyProfile(COMPANY_NUMBER);
        });

        assertEquals("Invalid URI for company resource", exception.getMessage());
        verify(mockApiClient, times(1)).company();
        verify(companyResourceHandler,times(1)).get(GET_COMPANY_URI_PRFIX + COMPANY_NUMBER);
        verify(companyGet, times(1)).execute();
    }
    
    @Test
    void getApiClient() throws ApiErrorResponseException, URIValidationException {
        testApiService = new ApiServiceImpl(logger);
        ReflectionTestUtils.setField(testApiService, "chsApiKey", CHS_API_KEY);
        
        ApiClient apiClient = testApiService.getApiClient();
        
        assertTrue(apiClient.getHttpClient() instanceof ApiKeyHttpClient);
        assertEquals(CHS_API_KEY, ReflectionTestUtils.getField(apiClient.getHttpClient(), "apiKey"));
    }
    
    private void mockCompanyProfileApiCall() throws ApiErrorResponseException, URIValidationException {
        when(mockApiClient.company()).thenReturn(companyResourceHandler);
        when(companyResourceHandler.get(GET_COMPANY_URI_PRFIX + COMPANY_NUMBER)).thenReturn(companyGet);
        when(companyGet.execute()).thenReturn(apiResponse);
    }
}