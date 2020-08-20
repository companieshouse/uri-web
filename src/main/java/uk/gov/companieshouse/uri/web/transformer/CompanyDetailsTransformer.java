package uk.gov.companieshouse.uri.web.transformer;

import uk.gov.companieshouse.api.model.charges.ChargesApi;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.uri.web.model.CompanyDetails;
import uk.gov.companieshouse.uri.web.model.MortgageTotals;

public interface CompanyDetailsTransformer {
    CompanyDetails profileApiToDetails(final CompanyProfileApi companyProfile);
    MortgageTotals chargesApiToMortgageTotals(final ChargesApi chargesApi);
}