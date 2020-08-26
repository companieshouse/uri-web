package uk.gov.companieshouse.uri.web.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.HttpStatusCodes;
import com.google.api.client.http.HttpResponseException.Builder;

import uk.gov.companieshouse.api.ApiClient;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.charges.ChargesResourceHandler;
import uk.gov.companieshouse.api.handler.charges.request.ChargesGet;
import uk.gov.companieshouse.api.handler.company.CompanyResourceHandler;
import uk.gov.companieshouse.api.handler.company.request.CompanyGet;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.http.ApiKeyHttpClient;
import uk.gov.companieshouse.api.model.ApiResponse;
import uk.gov.companieshouse.api.model.charges.ChargesApi;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;

import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.uri.web.exception.ServiceException;

@ExtendWith(MockitoExtension.class)
class ApiServiceImplTest {

    private static final String URI_PREFIX = "/company/";
    private static final String GET_CHARGES_URI_SUFFIX = "/charges";
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
    private ApiResponse<CompanyProfileApi> companyProfileApiResponse;
    
    @Mock
    private ChargesResourceHandler chargesResourceHandler;
    
    @Mock
    private ChargesGet chargesGet;
    
    @Mock
    private ApiResponse<ChargesApi> chargesApiResponse;

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
        when(companyProfileApiResponse.getData()).thenReturn(mockCompanyProfileApi);
        
        CompanyProfileApi companyProfileApi = testApiService.getCompanyProfile(COMPANY_NUMBER);
        
        assertEquals(COMPANY_NUMBER, companyProfileApi.getCompanyNumber());
        verify(mockApiClient, times(1)).company();
        verify(companyResourceHandler,times(1)).get(URI_PREFIX + COMPANY_NUMBER);
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
        verify(companyResourceHandler,times(1)).get(URI_PREFIX + COMPANY_NUMBER);
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
        verify(companyResourceHandler,times(1)).get(URI_PREFIX + COMPANY_NUMBER);
        verify(companyGet, times(1)).execute();
    }
    
    @Test
    void getCharges() throws ApiErrorResponseException, URIValidationException {
        mockChargesApiCall();
        ChargesApi mockChargesApi = new ChargesApi();
        mockChargesApi.setTotalCount(99l);
        when(chargesApiResponse.getData()).thenReturn(mockChargesApi);
        
        ChargesApi chargesApi = testApiService.getCharges(COMPANY_NUMBER);
        
        assertEquals(99l, chargesApi.getTotalCount());
        verify(mockApiClient, times(1)).charges();
        verify(chargesResourceHandler,times(1)).get(URI_PREFIX + COMPANY_NUMBER + GET_CHARGES_URI_SUFFIX);
        verify(chargesGet, times(1)).execute();
    }
    
    @Test
    void getChargesApiErrorResponseException() throws ApiErrorResponseException, URIValidationException {
        mockChargesApiCall();
        when(chargesGet.execute()).thenThrow(ApiErrorResponseException.class);
        
        ServiceException exception = assertThrows(ServiceException.class, () -> {
            testApiService.getCharges(COMPANY_NUMBER);
        });

        assertEquals("Error retrieving charges", exception.getMessage());
        verify(mockApiClient, times(1)).charges();
        verify(chargesResourceHandler,times(1)).get(URI_PREFIX + COMPANY_NUMBER + GET_CHARGES_URI_SUFFIX);
        verify(chargesGet, times(1)).execute();
    }
    
    @Test
    void getChargesApiErrorResponseException404() throws ApiErrorResponseException, URIValidationException {
        mockChargesApiCall();
        Builder builder = new HttpResponseException.Builder(HttpStatusCodes.STATUS_CODE_NOT_FOUND, "", new HttpHeaders());
        ApiErrorResponseException apiErrorResponseException = new ApiErrorResponseException(builder);
        when(chargesGet.execute()).thenThrow(apiErrorResponseException);
        
        assertNull(testApiService.getCharges(COMPANY_NUMBER));

        verify(mockApiClient, times(1)).charges();
        verify(chargesResourceHandler,times(1)).get(URI_PREFIX + COMPANY_NUMBER + GET_CHARGES_URI_SUFFIX);
        verify(chargesGet, times(1)).execute();
    }
    
    @Test
    void getChargesURIValidationException() throws ApiErrorResponseException, URIValidationException {
        mockChargesApiCall();
        when(chargesGet.execute()).thenThrow(URIValidationException.class);
        
        ServiceException exception = assertThrows(ServiceException.class, () -> {
            testApiService.getCharges(COMPANY_NUMBER);
        });

        assertEquals("Invalid URI for charges resource", exception.getMessage());
        verify(mockApiClient, times(1)).charges();
        verify(chargesResourceHandler,times(1)).get(URI_PREFIX + COMPANY_NUMBER + GET_CHARGES_URI_SUFFIX);
        verify(chargesGet, times(1)).execute();
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
        when(companyResourceHandler.get(URI_PREFIX + COMPANY_NUMBER)).thenReturn(companyGet);
        when(companyGet.execute()).thenReturn(companyProfileApiResponse);
    }
    
    private void mockChargesApiCall() throws ApiErrorResponseException, URIValidationException {
        when(mockApiClient.charges()).thenReturn(chargesResourceHandler);
        when(chargesResourceHandler.get(URI_PREFIX + COMPANY_NUMBER + GET_CHARGES_URI_SUFFIX)).thenReturn(chargesGet);
        when(chargesGet.execute()).thenReturn(chargesApiResponse);
    }
}