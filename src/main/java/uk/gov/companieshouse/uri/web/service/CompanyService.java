package uk.gov.companieshouse.uri.web.service;

import uk.gov.companieshouse.uri.web.model.CompanyDetails;

public interface CompanyService {

    CompanyDetails getCompanyDetails(String companyNumber);

}
