package uk.gov.companieshouse.uri.web;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import uk.gov.companieshouse.uri.web.interceptor.LoggingInterceptor;

@ExtendWith(MockitoExtension.class)
public class UriWebApplicationTest {
    private UriWebApplication testUriWebApplication;

    @Mock
    private LoggingInterceptor loggingInterceptor;
    
    @Mock
    private InterceptorRegistry registry;

    @BeforeEach
    void setUp() {
        testUriWebApplication = new UriWebApplication(loggingInterceptor);
    }

    @Test
    void addInterceptors() {
        testUriWebApplication.addInterceptors(registry);
        verify(registry, times(1)).addInterceptor(loggingInterceptor);
    }
    
    @Test
    @SuppressWarnings("squid:S2699")//Nothing to assert on calling main method
    void main() {
        UriWebApplication.main(new String[0]);
    }
}
