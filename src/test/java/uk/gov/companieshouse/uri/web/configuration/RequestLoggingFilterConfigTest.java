package uk.gov.companieshouse.uri.web.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
public class RequestLoggingFilterConfigTest {
    private RequestLoggingFilterConfig testRequestLoggingFilterConfig;

    @BeforeEach
    void setUp() {
        testRequestLoggingFilterConfig = new RequestLoggingFilterConfig();
    }

    @Test
    void logFilter() {
        CommonsRequestLoggingFilter filter = testRequestLoggingFilterConfig.logFilter();

        assertEquals(true, ReflectionTestUtils.getField(filter, "includeQueryString"));
        assertEquals(true, ReflectionTestUtils.getField(filter, "includePayload"));
        assertEquals(RequestLoggingFilterConfig.MAX_PAYLOAD_CHARACTERS, ReflectionTestUtils.getField(filter, "maxPayloadLength"));
        assertEquals(false, ReflectionTestUtils.getField(filter, "includeHeaders"));
        assertEquals("REQUEST DATA : [", ReflectionTestUtils.getField(filter, "afterMessagePrefix"));
    }
}
