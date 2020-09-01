package uk.gov.companieshouse.uri.web.controller;

import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;

import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.uri.web.model.CompanyDetails;
import uk.gov.companieshouse.uri.web.service.CompanyService;

@Controller
@RequestMapping("/doc/company/")
public class ViewController {
    
    protected static final String HTML_VIEW = "html/companyView";
    protected static final String JSON_VIEW = "json/companyView";
    protected static final String RDF_VIEW = "rdf/companyView";
    protected static final String XML_VIEW = "xml/companyView";
    protected static final String CSV_VIEW = "csv/companyView";
    protected static final String YAML_VIEW = "yaml/companyView";
    
    protected static final String CONTEXT_VAR_NAME = "company";
    protected static final String LINES_WITH_JUST_WHITESPACE_REG_EXP = "(?m)^\\s*\n";
    
    protected static final String APPLICATION_RDFXML = "application/rdf+xml";
    protected static final String APPLICATION_XXML = "application/x-xml";
    protected static final String TEXT_CSV = "text/csv";
    protected static final String TEXT_COMMA_SEPARATED_VALUES = "text/comma-separated-values";
    protected static final String APPLICATION_CSV = "application/csv";
    protected static final String APPLICATION_EXCEL = "application/excel";
    protected static final String APPLICATION_YAML = "application/yaml";
    protected static final String APPLICATION_XYAML = "application/x-yaml";
    protected static final String TEXT_YAML = "text/yaml";
    protected static final String TEXT_XYAML = "text/x-yaml";
    
    protected static final MediaType HTML_RESPONSE_MEDIA_TYPE = new MediaType(MediaType.TEXT_HTML, 
            StandardCharsets.UTF_8);
    protected static final MediaType JSON_RESPONSE_MEDIA_TYPE = new MediaType(MediaType.APPLICATION_JSON, 
            StandardCharsets.UTF_8);
    protected static final MediaType RDF_RESPONSE_MEDIA_TYPE = new MediaType("application", "rdf+xml", 
            StandardCharsets.UTF_8);
    protected static final MediaType XML_RESPONSE_MEDIA_TYPE = new MediaType(MediaType.TEXT_XML, 
            StandardCharsets.UTF_8);
    protected static final MediaType CSV_RESPONSE_MEDIA_TYPE = new MediaType("text", "csv", 
            StandardCharsets.UTF_8);
    protected static final MediaType YAML_RESPONSE_MEDIA_TYPE = new MediaType("application", "yaml", 
            StandardCharsets.UTF_8);
  
    private Logger logger;
    
    private CompanyService companyService;
    
    private ITemplateEngine templateEngine;
    
    /**
     * @param logger - the CH logger
     * @param companyService - CompanyService responsible for API calls
     * @param templateEngine - the template engine used to render the view
     */
    public ViewController(final Logger logger, CompanyService companyService, ITemplateEngine templateEngine) {
        this.logger = logger;
        this.companyService = companyService;
        this.templateEngine = templateEngine;
    }

    @GetMapping(value = {"{companyNumber:[A-Z0-9]{8}}","{companyNumber:[A-Z0-9]{8}}.html"}, 
            produces = {MediaType.TEXT_HTML_VALUE, MediaType.ALL_VALUE} )
    public ResponseEntity<String> html(@PathVariable String companyNumber, HttpServletRequest request,
            HttpServletResponse response) {
        
        return okResponse(renderView(companyNumber, HTML_VIEW, request, response), HTML_RESPONSE_MEDIA_TYPE);
    }
    
    @GetMapping(value = {"{companyNumber:[A-Z0-9]{8}}","{companyNumber:[A-Z0-9]{8}}.json"}, 
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> json(@PathVariable String companyNumber, HttpServletRequest request, 
            HttpServletResponse response) {

        return okResponse(renderView(companyNumber, JSON_VIEW, request, response), JSON_RESPONSE_MEDIA_TYPE);
    }
    
    @GetMapping(value = {"{companyNumber:[A-Z0-9]{8}}","{companyNumber:[A-Z0-9]{8}}.rdf"}, 
            produces = APPLICATION_RDFXML)
    public ResponseEntity<String> rdf(@PathVariable String companyNumber, HttpServletRequest request, 
            HttpServletResponse response) {

        return okResponse(renderView(companyNumber, RDF_VIEW, request, response), RDF_RESPONSE_MEDIA_TYPE);
    }
    
    @GetMapping(value = {"{companyNumber:[A-Z0-9]{8}}","{companyNumber:[A-Z0-9]{8}}.xml"}, 
            produces = {MediaType.TEXT_XML_VALUE, MediaType.APPLICATION_XML_VALUE, APPLICATION_XXML})
    public ResponseEntity<String> xml(@PathVariable String companyNumber, HttpServletRequest request, 
            HttpServletResponse response) {

        return okResponse(renderView(companyNumber, XML_VIEW, request, response), XML_RESPONSE_MEDIA_TYPE);
    }
    
    @GetMapping(value = {"{companyNumber:[A-Z0-9]{8}}","{companyNumber:[A-Z0-9]{8}}.csv"}, 
            produces = {TEXT_CSV, TEXT_COMMA_SEPARATED_VALUES, APPLICATION_CSV, APPLICATION_EXCEL})
    public ResponseEntity<String> csv(@PathVariable String companyNumber, HttpServletRequest request, 
            HttpServletResponse response) {

        return okResponse(renderView(companyNumber, CSV_VIEW, request, response), CSV_RESPONSE_MEDIA_TYPE);
    }
    
    @GetMapping(value = {"{companyNumber:[A-Z0-9]{8}}","{companyNumber:[A-Z0-9]{8}}.yaml"}, 
            produces = {APPLICATION_YAML, APPLICATION_XYAML, TEXT_YAML, TEXT_XYAML})
    public ResponseEntity<String> yaml(@PathVariable String companyNumber, HttpServletRequest request, 
            HttpServletResponse response) {

        return okResponse(renderView(companyNumber, YAML_VIEW, request, response), YAML_RESPONSE_MEDIA_TYPE);
    }
    
    private String renderView(String companyNumber, String viewName, HttpServletRequest request, HttpServletResponse response) {
        CompanyDetails companyDetails = companyService.getCompanyDetails(companyNumber);
        logger.debug(companyDetails.toString());

        WebContext context = new WebContext(request, response, request.getServletContext());
        context.setVariable(CONTEXT_VAR_NAME, companyDetails);
        
        String rendered = templateEngine.process(viewName, context);
        return rendered.replaceAll(LINES_WITH_JUST_WHITESPACE_REG_EXP, "");
    }
    
    private ResponseEntity<String> okResponse(String body, MediaType mediaType) {
        return ResponseEntity.ok()
                .contentType(mediaType)
                .header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
                .cacheControl(CacheControl.noStore().mustRevalidate())
                .body(body);
    }
}