package uk.gov.companieshouse.uri.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.uri.web.model.CompanyDetails;
import uk.gov.companieshouse.uri.web.service.CompanyService;

@Controller
@RequestMapping("/doc/company/")
public class ViewController {

    static final String TEMPLATE_NAME = "templateName";
    
    private Logger logger;
    
    private CompanyService companyService;
    
    /**
     * @param logger - the CH logger
     * @param companyService - CompanyService responsible for API calls
     */
    @Autowired
    public ViewController(final Logger logger, CompanyService companyService) {
        this.logger=logger;
        this.companyService=companyService;
    }

    @GetMapping({"{companyNumber:[A-Z0-9]{8}}","{companyNumber:[A-Z0-9]{8}}.html"})
    public String html(Model model, @PathVariable String companyNumber) {

        CompanyDetails companyDetails = companyService.getCompanyDetails(companyNumber);
        logger.debug(companyDetails.toString());

        model.addAttribute("company", companyDetails);
        return "legacy_style_html";
    }
}