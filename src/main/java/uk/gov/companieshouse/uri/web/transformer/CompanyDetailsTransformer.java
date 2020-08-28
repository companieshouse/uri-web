package uk.gov.companieshouse.uri.web.transformer;

import uk.gov.companieshouse.api.model.charges.ChargesApi;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.uri.web.model.CompanyDetails;
import uk.gov.companieshouse.uri.web.model.MortgageTotals;

public interface CompanyDetailsTransformer {
    
    /**
     * Converts a CompanyProfileApi model to a CompanyDetails model.
     * @param companyProfileApi - companyProfileApi model returned from API SDK
     * @return the populated CompayDetails model
     */
    CompanyDetails profileApiToDetails(final CompanyProfileApi companyProfileApi);
    
    /**
     * Converts a ChargesApi model to a MortgageTotals model.
     * @param chargesApi - ChargesApi model returned from API SDK
     * @return the populated MortgageTotals model
     */
    MortgageTotals chargesApiToMortgageTotals(final ChargesApi chargesApi);
}