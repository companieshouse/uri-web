package uk.gov.companieshouse.uri.web.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;

import nz.net.ultraq.thymeleaf.LayoutDialect;

import java.util.Collection;
import java.util.Collections;

@Configuration
public class ThymeleafConfiguration {

    public static final String TEMPLATES_BASE = "classpath:/templates/";

    public static final String JSON_TEMPLATES_RESOLVE_PATTERN = "json/*";
    public static final String RDF_TEMPLATES_RESOLVE_PATTERN = "rdf/*";
   
    public static final String UTF8 = "UTF-8";
    
    @Bean
    public SpringResourceTemplateResolver jsonMessageTemplateResolver() {
        SpringResourceTemplateResolver theResourceTemplateResolver =
            new SpringResourceTemplateResolver();
        theResourceTemplateResolver.setPrefix(TEMPLATES_BASE);
        theResourceTemplateResolver.setResolvablePatterns(
            Collections.singleton(JSON_TEMPLATES_RESOLVE_PATTERN));
        theResourceTemplateResolver.setSuffix(".json");
        theResourceTemplateResolver.setCharacterEncoding(UTF8);
        theResourceTemplateResolver.setTemplateMode("JAVASCRIPT");
        theResourceTemplateResolver.setCacheable(false);
        theResourceTemplateResolver.setOrder(1);
        return theResourceTemplateResolver;
    }

    @Bean
    public SpringResourceTemplateResolver rdfMessageTemplateResolver() {
        SpringResourceTemplateResolver theResourceTemplateResolver =
            new SpringResourceTemplateResolver();
        theResourceTemplateResolver.setPrefix(TEMPLATES_BASE);
        theResourceTemplateResolver.setResolvablePatterns(
            Collections.singleton(RDF_TEMPLATES_RESOLVE_PATTERN));
        theResourceTemplateResolver.setSuffix(".rdf");
        theResourceTemplateResolver.setCharacterEncoding(UTF8);
        theResourceTemplateResolver.setTemplateMode("XML");
        theResourceTemplateResolver.setCacheable(false);
        theResourceTemplateResolver.setOrder(2);
        return theResourceTemplateResolver;
    }
    
    @Bean
    public SpringTemplateEngine messageTemplateEngine(
        final Collection<SpringResourceTemplateResolver> inTemplateResolvers) {
        final SpringTemplateEngine theTemplateEngine = new SpringTemplateEngine();
        for (SpringResourceTemplateResolver theTemplateResolver : inTemplateResolvers) {
            theTemplateEngine.addTemplateResolver(theTemplateResolver);
        }
        theTemplateEngine.addDialect(new LayoutDialect());
        return theTemplateEngine;
    }
}