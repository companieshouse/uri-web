package uk.gov.companieshouse.uri.web.service;

import uk.gov.companieshouse.api.ApiClient;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;

public interface ApiService {

    CompanyProfileApi getCompanyProfile(String companyNumber);
    ApiClient getApiClient();
}
