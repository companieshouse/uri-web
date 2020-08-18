package uk.gov.companieshouse.uri.web.service;

import org.springframework.stereotype.Service;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.uri.web.model.CompanyDetails;
import uk.gov.companieshouse.uri.web.transformer.CompanyDetailsTransformer;

@Service
public class CompanyServiceImpl implements CompanyService {
    
    private ApiService apiService;
    
    private CompanyDetailsTransformer companyDetailsTransformer;

    /**
     * Constructor
     *
     * @param apiService calls CompanyProfile API
     * @param companyDetailsTransformer for transform to CompanyDetails model
     */
    public CompanyServiceImpl(ApiService apiService, CompanyDetailsTransformer companyDetailsTransformer) {
        this.apiService = apiService;
        this.companyDetailsTransformer = companyDetailsTransformer;
    }

    @Override
    public CompanyDetails getCompanyDetails(String companyNumber) {
        CompanyProfileApi companyProfileApi = apiService.getCompanyProfile(companyNumber);
        return companyDetailsTransformer.profileApiToDetails(companyProfileApi);
    }
}

