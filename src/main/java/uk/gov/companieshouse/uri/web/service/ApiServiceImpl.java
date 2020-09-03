package uk.gov.companieshouse.uri.web.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriTemplate;

import com.google.api.client.http.HttpStatusCodes;

import uk.gov.companieshouse.api.ApiClient;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.http.ApiKeyHttpClient;
import uk.gov.companieshouse.api.model.ApiResponse;
import uk.gov.companieshouse.api.model.charges.ChargesApi;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.uri.web.exception.CompanyNotFoundException;
import uk.gov.companieshouse.uri.web.exception.ServiceException;

@Service
public class ApiServiceImpl implements ApiService {

    private static final UriTemplate GET_COMPANY_URI = new UriTemplate("/company/{companyNumber}");    
    private static final UriTemplate GET_CHARGES_URI = new UriTemplate("/company/{companyNumber}/charges");
    
    private static final String FAILED_COMPANY_PROFILE_PREFIX = "Failed to get company profile from: ";
    private static final String FAILED_CHARGES_PREFIX = "Failed to get charges from: ";
    private static final String FAILED_SUFFIX = ", due to exception:";
    
    @Value("${chs.api.key}")
    private String chsApiKey;
    
    @Value("${api.url}")
    private String apiUrl;
    
    private Logger logger;
    
    public ApiServiceImpl(final Logger logger) {
        this.logger = logger;
    }
    
    public CompanyProfileApi getCompanyProfile(String companyNumber) { 

        String uri = GET_COMPANY_URI.expand(companyNumber).toString();

        try {
            logger.debug("About to call companyProfile api for " + companyNumber);
            ApiResponse<CompanyProfileApi> response = getApiClient().company().get(uri).execute();
            return response.getData();
            
        } catch (ApiErrorResponseException e) {
            if (e.getStatusCode() == HttpStatusCodes.STATUS_CODE_NOT_FOUND) {
                throw new CompanyNotFoundException("Error retrieving company profile", e);
            }
            logger.debug(FAILED_COMPANY_PROFILE_PREFIX + uri + FAILED_SUFFIX + e);
            throw new ServiceException("Error retrieving company profile", e);
        } catch (URIValidationException e) {
            logger.debug(FAILED_COMPANY_PROFILE_PREFIX + uri + FAILED_SUFFIX + e);
            throw new ServiceException("Invalid URI for company resource", e);
        }
    }
    
    public ChargesApi getCharges(String companyNumber) { 

        String uri = GET_CHARGES_URI.expand(companyNumber).toString();

        try {
            logger.debug("About to call charges api for " + companyNumber);
            ApiResponse<ChargesApi> response = getApiClient().charges().get(uri).execute();
            return response.getData();
            
        } catch (ApiErrorResponseException e) {
            if (e.getStatusCode() == HttpStatusCodes.STATUS_CODE_NOT_FOUND) {
                // Don't throw a ServiceException if just 404 as we should
                // still return the company profile data without the mortgages.
                return null;
            }
            logger.debug(FAILED_CHARGES_PREFIX + uri + FAILED_SUFFIX + e);
            throw new ServiceException("Error retrieving charges", e);
        } catch (URIValidationException e) {
            logger.debug(FAILED_CHARGES_PREFIX + uri + FAILED_SUFFIX + e);
            throw new ServiceException("Invalid URI for charges resource", e);
        }
    }
    
    public ApiClient getApiClient() {
        ApiKeyHttpClient httpClient = new ApiKeyHttpClient(chsApiKey);
        ApiClient apiClient = new ApiClient(httpClient);
        apiClient.setBasePath(apiUrl);
        return apiClient;
    }
}
