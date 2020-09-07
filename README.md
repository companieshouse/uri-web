
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
Variable | Description|Example| Default
-|-|-|-
`CHS_API_KEY` |The API key to use to authenticate with the CHS APIs (see https://developer.companieshouse.gov.uk)|`7aPeBkeorUFphM_6PKrMuI-uB9r-3Z92bBe1iTT0`|N/A
`API_URL`|The URL of the CHS API to use|https://api.companieshouse.gov.uk|N/A

Optional environment variables, mainly for links and common Javascript/CSS on error pages:
Variable|Description|Example|Default
-|-|-|-
`HUMAN_LOG`        |Output JSON or human readable logs|`0` (JSON), `1` (human readable)| `0`
`CDN_URL`|Base URL for CND hosted resources|http://dpvdz1eqcaytm.cloudfront.net|N/A
`POLICIES_URL`|URL for link to policy information|http://resources.companieshouse.gov.uk/serviceInformation.shtml|N/A
`DEVELOPER_URL`|URL for link to developer site|https://developer.companieshouse.gov.uk| N/A
`CHS_URL`|URL for main CH service site|https://beta.companieshouse.gov.uk|N/A
`GOVUK_CH_URL`|URL for CH gov.uk site|https://www.gov.uk/government/organisations/companies-house|N/A
