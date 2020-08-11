package uk.gov.companieshouse.uri.web.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class RequestLoggingFilterConfig {
    
    static final int MAX_PAYLOAD_CHARACTERS = 10000;
    
    @Bean
    public CommonsRequestLoggingFilter logFilter() {
        final CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();

        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(MAX_PAYLOAD_CHARACTERS);
        filter.setIncludeHeaders(false);
        filter.setAfterMessagePrefix("REQUEST DATA : [");

        return filter;
    }
}
