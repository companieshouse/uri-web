package uk.gov.companieshouse.uri.web.controller;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/id/company/*")
public class RedirectController {
    
    @Value("${redirect.uri.prefix}")
    private String redirectURIPrefix;

    @GetMapping
    public ResponseEntity<String> redirect(HttpServletRequest request,
            HttpServletResponse response) throws URISyntaxException {
        
        return ResponseEntity.status(HttpStatus.SEE_OTHER)
                .location(getRedirectURI(request)).build();
    }
    
    private URI getRedirectURI(HttpServletRequest request) throws URISyntaxException {
        String originalURIPath = request.getRequestURI();
        String lastPathElement = originalURIPath.substring(originalURIPath.lastIndexOf('/'));
        return new URI(redirectURIPrefix + lastPathElement);
    }
}