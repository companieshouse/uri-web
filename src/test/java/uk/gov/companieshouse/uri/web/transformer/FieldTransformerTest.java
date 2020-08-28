package uk.gov.companieshouse.uri.web.transformer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;

public class FieldTransformerTest {
    
    private FieldTransformer transformer;

    @BeforeEach
    protected void setUp() throws ApiErrorResponseException, URIValidationException {
        transformer = new FieldTransformer();
    }

    @Test
    void xmlDate() {
        assertEquals("2020-08-26", transformer.xmlDate("26/08/2020"));
        assertEquals("2020", transformer.xmlDate("2020"));
        assertEquals("26/08/20", transformer.xmlDate("26/08/20"));
        assertEquals("32/08/2020", transformer.xmlDate("32/08/2020"));
        assertEquals("Not a date", transformer.xmlDate("Not a date"));
        assertEquals("", transformer.xmlDate(""));
        assertEquals(null, transformer.xmlDate(null));
    }
    
    @Test
    void csvEscape() {
        assertEquals("\"\"COMPANY\"\"", transformer.csvEscape("\"COMPANY\""));
        assertEquals("\"\"\"\"COMPANY\"\"\"\"", transformer.csvEscape("\"\"COMPANY\"\""));
        assertEquals("", transformer.csvEscape(""));
        assertEquals(null, transformer.csvEscape(null));
    }
}