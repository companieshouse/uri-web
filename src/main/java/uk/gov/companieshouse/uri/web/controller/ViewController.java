package uk.gov.companieshouse.uri.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
    
    protected static final String RDF_CONTENT_TYPE = "application/rdf+xml";
    protected static final String CSV_CONTENT_TYPE = "text/csv";
    protected static final String YAML_CONTENT_TYPE = "application/yaml";
    
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
        
        return new ResponseEntity<>(renderView(companyNumber, HTML_VIEW, request, response), 
                contentTypeHeader(MediaType.TEXT_HTML_VALUE),
                HttpStatus.OK);
    }
    
    @GetMapping(value = {"{companyNumber:[A-Z0-9]{8}}","{companyNumber:[A-Z0-9]{8}}.json"}, 
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> json(@PathVariable String companyNumber, HttpServletRequest request, 
            HttpServletResponse response) {

        return new ResponseEntity<>(renderView(companyNumber, JSON_VIEW, request, response),
                contentTypeHeader(MediaType.APPLICATION_JSON_VALUE),
                HttpStatus.OK);
    }
    
    @GetMapping(value = {"{companyNumber:[A-Z0-9]{8}}","{companyNumber:[A-Z0-9]{8}}.rdf"}, 
            produces = RDF_CONTENT_TYPE)
    public ResponseEntity<String> rdf(@PathVariable String companyNumber, HttpServletRequest request, 
            HttpServletResponse response) {

        return new ResponseEntity<>(renderView(companyNumber, RDF_VIEW, request, response),
                contentTypeHeader(RDF_CONTENT_TYPE),
                HttpStatus.OK);
    }
    
    @GetMapping(value = {"{companyNumber:[A-Z0-9]{8}}","{companyNumber:[A-Z0-9]{8}}.xml"}, 
            produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> xml(@PathVariable String companyNumber, HttpServletRequest request, 
            HttpServletResponse response) {

        return new ResponseEntity<>(renderView(companyNumber, XML_VIEW, request, response),
                contentTypeHeader(MediaType.APPLICATION_XML_VALUE),
                HttpStatus.OK);
    }
    
    @GetMapping(value = {"{companyNumber:[A-Z0-9]{8}}","{companyNumber:[A-Z0-9]{8}}.csv"}, 
            produces = CSV_CONTENT_TYPE)
    public ResponseEntity<String> csv(@PathVariable String companyNumber, HttpServletRequest request, 
            HttpServletResponse response) {

        return new ResponseEntity<>(renderView(companyNumber, CSV_VIEW, request, response),
                contentTypeHeader(CSV_CONTENT_TYPE),
                HttpStatus.OK);
    }
    
    @GetMapping(value = {"{companyNumber:[A-Z0-9]{8}}","{companyNumber:[A-Z0-9]{8}}.yaml"}, 
            produces = YAML_CONTENT_TYPE)
    public ResponseEntity<String> yaml(@PathVariable String companyNumber, HttpServletRequest request, 
            HttpServletResponse response) {

        return new ResponseEntity<>(renderView(companyNumber, YAML_VIEW, request, response),
                contentTypeHeader(YAML_CONTENT_TYPE),
                HttpStatus.OK);
    }
    
    private String renderView(String companyNumber, String viewName, HttpServletRequest request, HttpServletResponse response) {
        CompanyDetails companyDetails = companyService.getCompanyDetails(companyNumber);
        logger.debug(companyDetails.toString());

        WebContext context = new WebContext(request, response, request.getServletContext());
        context.setVariable(CONTEXT_VAR_NAME, companyDetails);
        
        return templateEngine.process(viewName, context);
    }
    
    private HttpHeaders contentTypeHeader(String contentTypeValue) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, contentTypeValue);
        return headers;
    }
}