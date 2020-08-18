package uk.gov.companieshouse.uri.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriTemplate;

import uk.gov.companieshouse.api.ApiClient;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.http.ApiKeyHttpClient;
import uk.gov.companieshouse.api.model.ApiResponse;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.uri.web.exception.ServiceException;

@Service
public class ApiServiceImpl implements ApiService {

    private static final UriTemplate GET_COMPANY_URI = new UriTemplate("/company/{companyNumber}");
    
    @Value("${chs.api.key}")
    private String chsApiKey;
    
    @Autowired
    private Logger logger;
    
    public CompanyProfileApi getCompanyProfile(String companyNumber) { 

        String uri = GET_COMPANY_URI.expand(companyNumber).toString();

        CompanyProfileApi companyProfileApi;
        try {
            logger.debug("About to call api for " + companyNumber);
            ApiResponse<CompanyProfileApi> response = getApiClient().company().get(uri).execute();
            companyProfileApi = response.getData();
            
        } catch (ApiErrorResponseException e) {
            logger.debug("Failed to get company profile from: " + uri + ", due to exception:" + e);
            throw new ServiceException("Error retrieving company profile", e);
        } catch (URIValidationException e) {
            logger.debug("Failed to get company profile from: " + uri + ", due to exception:" + e);
            throw new ServiceException("Invalid URI for company resource", e);
        }
        
        return companyProfileApi;
    }
    
    protected ApiClient getApiClient() {
        ApiKeyHttpClient httpClient = new ApiKeyHttpClient(chsApiKey);
        return new ApiClient(httpClient);
    }
}
