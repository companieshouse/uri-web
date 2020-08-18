package uk.gov.companieshouse.uri.web.transformer;

import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.uri.web.model.CompanyDetails;

public interface CompanyDetailsTransformer {
    CompanyDetails profileApiToDetails(final CompanyProfileApi companyProfile);
}