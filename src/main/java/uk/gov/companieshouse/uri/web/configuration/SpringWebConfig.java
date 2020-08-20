package uk.gov.companieshouse.uri.web.configuration;

import java.util.Locale;
import java.util.ResourceBundle;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Provides configuration for the web application
 */
@SpringBootConfiguration
public class SpringWebConfig implements WebMvcConfigurer {

    @Bean
    ResourceBundle resourceBundle() {
        return ResourceBundle.getBundle("messages", Locale.UK);
    }

}
