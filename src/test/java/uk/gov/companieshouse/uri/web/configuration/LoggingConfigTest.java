package uk.gov.companieshouse.uri.web.configuration;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.util.RequestLogger;


@ExtendWith(MockitoExtension.class)
public class LoggingConfigTest {
    private LoggingConfig testLoggingConfig;

    @Mock
    private Logger logger;

    @BeforeEach
    void setUp() {
        testLoggingConfig = new LoggingConfig();
    }

    @Test
    void structuredLoggerBean() {
        assertThat(testLoggingConfig.logger(), instanceOf(Logger.class));
    }

    @Test
    void loggingInterceptorBean() {
        assertThat(testLoggingConfig.loggingInterceptor(logger), instanceOf(RequestLogger.class));
    }
}
