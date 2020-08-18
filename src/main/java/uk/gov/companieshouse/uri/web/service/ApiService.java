package uk.gov.companieshouse.uri.web.service;

import uk.gov.companieshouse.api.model.company.CompanyProfileApi;

public interface ApiService {

    CompanyProfileApi getCompanyProfile(String companyNumber);
}
