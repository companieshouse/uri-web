package uk.gov.companieshouse.uri.web;

import static org.springframework.boot.SpringApplication.run;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import uk.gov.companieshouse.uri.web.interceptor.LoggingInterceptor;

@SpringBootApplication
public class UriWebApplication implements WebMvcConfigurer {


    private LoggingInterceptor loggingInterceptor;


    /**
     * Constructor for UriWebApplication
     *
     * @param loggingInterceptor responsible for logging the start and end of the requests
     */
    @Autowired
    public UriWebApplication(LoggingInterceptor loggingInterceptor) {
        this.loggingInterceptor = loggingInterceptor;
    }

    /**
     * Adds interceptor for logging.
     *
     * @param registry
     */
    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(loggingInterceptor);
    }

    /**
     * Required spring boot application main method
     *
     * @param args array of String arguments
     */
    public static void main(String[] args) {
        run(UriWebApplication.class, args);
    }
}
