package uk.gov.companieshouse.uri.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.uri.web.exception.ServiceException;
import uk.gov.companieshouse.uri.web.model.CompanyDetails;
import uk.gov.companieshouse.uri.web.transformer.CompanyDetailsTransformer;

@Service
public class CompanyServiceImpl implements CompanyService {
    
    @Autowired
    private ApiService apiService;
    
    @Autowired
    private CompanyDetailsTransformer companyDetailsTransformer;
    
    @Autowired
    private Logger logger;

    @Override
    public CompanyDetails getCompanyDetails(String companyNumber) {
        CompanyProfileApi companyProfileApi = apiService.getCompanyProfile(companyNumber);
        
        try {
            return companyDetailsTransformer.profileApiToDetails(companyProfileApi);
        }
        catch (Exception e) {
            logger.error("Exception during transform of companyProfileApi", e);
            throw new ServiceException("Error transforming company profile", e);
        }
    }
}

