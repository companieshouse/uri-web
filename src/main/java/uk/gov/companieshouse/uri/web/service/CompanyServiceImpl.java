package uk.gov.companieshouse.uri.web.service;

import org.springframework.stereotype.Service;

import uk.gov.companieshouse.api.model.charges.ChargesApi;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.uri.web.exception.ServiceException;
import uk.gov.companieshouse.uri.web.model.CompanyDetails;
import uk.gov.companieshouse.uri.web.transformer.CompanyDetailsTransformer;

@Service
public class CompanyServiceImpl implements CompanyService {
    
    private ApiService apiService;
    
    private CompanyDetailsTransformer companyDetailsTransformer;
    
    private Logger logger;
    
    /**
     * Constructor
     *
     * @param apiService calls CompanyProfile API
     * @param companyDetailsTransformer for transform to CompanyDetails model
     */
    public CompanyServiceImpl(ApiService apiService, CompanyDetailsTransformer companyDetailsTransformer, Logger logger) {
        this.apiService = apiService;
        this.companyDetailsTransformer = companyDetailsTransformer;
        this.logger = logger;
    }
    
    @Override
    public CompanyDetails getCompanyDetails(String companyNumber) {
        CompanyProfileApi companyProfileApi = apiService.getCompanyProfile(companyNumber);
        
        CompanyDetails companyDetails;
        try {
            companyDetails = companyDetailsTransformer.profileApiToDetails(companyProfileApi);
        }
        catch (RuntimeException e) {
            logger.error("Exception during transform of companyProfileApi", e);
            throw new ServiceException("Error transforming company profile", e);
        }
        
        if (companyDetails.hasCharges()) {
            ChargesApi chargesApi = apiService.getCharges(companyNumber);
            try {
                companyDetails.setMortgageTotals(companyDetailsTransformer.chargesApiToMortgageTotals(chargesApi));
            }
            catch (RuntimeException e) {
                logger.error("Exception during transform of chargesApi", e);
                throw new ServiceException("Error transforming charges", e);
            }
        }
        return companyDetails;
        
    }
}

