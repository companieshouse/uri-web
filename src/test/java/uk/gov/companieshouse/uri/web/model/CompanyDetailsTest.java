package uk.gov.companieshouse.uri.web.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;

@ExtendWith(MockitoExtension.class)
class CompanyDetailsTest {
    
    private CompanyDetails testCompanyDetails;

    @BeforeEach
    protected void setUp() throws ApiErrorResponseException, URIValidationException {
        testCompanyDetails = new CompanyDetails();
    }

    @Test
    void xmlDate() {
        assertEquals("2020-08-26", testCompanyDetails.xmlDate("26/08/2020"));
        assertEquals("2020", testCompanyDetails.xmlDate("2020"));
        assertEquals("26/08/20", testCompanyDetails.xmlDate("26/08/20"));
        assertEquals("Not a date", testCompanyDetails.xmlDate("Not a date"));
        assertEquals("", testCompanyDetails.xmlDate(""));
        assertEquals(null, testCompanyDetails.xmlDate(null));
    }
}