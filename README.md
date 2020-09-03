
# uri-web
Microservice version of CH URI service.

- Provides a URI style endpoint `/doc/company/<company number>` that returns basic company profile information, in a variety of formats, from the existing CHS APIs.   

The format can be selected by specifying a suitable `Accept` header, or by adding a 'file type' extension, as follows:

Format    |   Accept header(s)   |  Extension
----------|----------------------|-------------
HTML|None, `text/html`|`.html` e.g.`/doc/company/<company number>.html`
JSON|`application/json`|`.json`
RDF|`application/rdf+xml`|`.rdf`
XML|`application/xml`, `application/x-xml`|`.xml`
CSV|`text/csv`, `text/comma-separated-values`, `application/csv`, `application/excel`|`.csv`
YAML|`application/yaml`, `application/x-yaml`, `text/yaml`, `text/x-yaml`|`.yaml`

Please see https://www.gov.uk/guidance/companies-house-data-products#uri-info for further information on the data provided.

This application is written using the [Spring Boot](http://projects.spring.io/spring-boot/) Java framework.

### Requirements
In order to build and run this Web App locally you will need to install:

- [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven](https://maven.apache.org/download.cgi)

### Getting Started

1. [Configure your service](#configuration) if you want to override any of the defaults.
1. Run `make dist`
1. Run `java -jar target/uri-web-unversioned.jar`


### Configuration
Required environment variables:
Key                | Description
-------------------|------------------------------------
`CHS_API_KEY` |The API key to use to authenticate with the CHS APIs (see https://developer.companieshouse.gov.uk)
`API_URL`|The URL of the CHS API to use - e.g. https://api.companieshouse.gov.uk

Optional environment variables, mainly for links and common Javascript/CSS on error pages:
Key                | Description
-------------------|------------------------------------
`HUMAN_LOG`        |`1`- For human readable logs, `0`(default)- for JSON logs
`CDN_URL`|E.g. http://dpvdz1eqcaytm.cloudfront.net
`POLICIES_URL`|E.g. http://resources.companieshouse.gov.uk/serviceInformation.shtml
`DEVELOPER_URL`|E.g. https://developer.companieshouse.gov.uk
`CHS_URL`|E.g. https://beta.companieshouse.gov.uk
`GOVUK_CH_URL`|E.g. https://www.gov.uk/government/organisations/companies-house
