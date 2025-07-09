

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

- [Java 21](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven](https://maven.apache.org/download.cgi)

### Getting Started

1. [Configure your service](#configuration) if you want to override any of the defaults.
1. Run `make dist`
1. Run `java -jar target/uri-web-unversioned.jar`


### Configuration
Required environment variables:

Variable | Description|Example| Default
-|-|-|-
`CHS_API_KEY` |The API key to use to authenticate with the CHS APIs (see https://developer.companieshouse.gov.uk). Provided by global env on CHS environment.|`7aPeBkeorUFphM_6PKrMuI-uB9r-3Z92bBe1iTT0`|N/A
`API_URL`|The URL of the CHS API to use. Provided by global env on CHS environment.|https://api.companieshouse.gov.uk|N/A
`REDIRECT_URI_PREFIX`|The URL to redirect requests received for /id/company/*. These requests are normally when the business.data.gov.uk address has been used and need to be redirected to the CH version of the address|http://data-staging.companieshouse.gov.uk/doc/company|N/A

Optional environment variables, mainly for links and common Javascript/CSS on error pages:

Variable|Description|Example|Default
-|-|-|-
`HUMAN_LOG`        |Output JSON or human readable logs|`0` (JSON), `1` (human readable)| `0`
`CDN_HOST`|Host for CND hosted resources. Provided by global env on CHS environment.|dpvdz1eqcaytm.cloudfront.net|N/A
`DEVELOPER_URL`|URL for link to developer site. Provided by global env on CHS environment.|https://developer.companieshouse.gov.uk| N/A
`CHS_URL`|URL for main CH service site. Provided by global env on CHS environment.|https://beta.companieshouse.gov.uk|N/A
`GOVUK_CH_URL`|URL for CH gov.uk site|https://www.gov.uk/government/organisations/companies-house|N/A
`POLICIES_URL`|URL for link to policy information|http://resources.companieshouse.gov.uk/serviceInformation.shtml|N/A

## Terraform ECS

### What does this code do?

The code present in this repository is used to define and deploy a dockerised container in AWS ECS.
This is done by calling a [module](https://github.com/companieshouse/terraform-modules/tree/main/aws/ecs) from terraform-modules. Application specific attributes are injected and the service is then deployed using Terraform via the CICD platform 'Concourse'.


Application specific attributes | Value                                | Description
:---------|:-----------------------------------------------------------------------------|:-----------
**ECS Cluster**        |public-data                                      | ECS cluster (stack) the service belongs to
**Load balancer**      |{env}-uri-web                                            | The load balancer that sits in front of the service
**Concourse pipeline**     |[Pipeline link](https://ci-platform.companieshouse.gov.uk/teams/team-development/pipelines/uri-web) <br> [Pipeline code](https://github.com/companieshouse/ci-pipelines/blob/master/pipelines/ssplatform/team-development/uri-web)                               | Concourse pipeline link in shared services


### Contributing
- Please refer to the [ECS Development and Infrastructure Documentation](https://companieshouse.atlassian.net/wiki/spaces/DEVOPS/pages/4390649858/Copy+of+ECS+Development+and+Infrastructure+Documentation+Updated) for detailed information on the infrastructure being deployed.

### Testing
- Ensure the terraform runner local plan executes without issues. For information on terraform runners please see the [Terraform Runner Quickstart guide](https://companieshouse.atlassian.net/wiki/spaces/DEVOPS/pages/1694236886/Terraform+Runner+Quickstart).
- If you encounter any issues or have questions, reach out to the team on the **#platform** slack channel.

### Vault Configuration Updates
- Any secrets required for this service will be stored in Vault. For any updates to the Vault configuration, please consult with the **#platform** team and submit a workflow request.

### Useful Links
- [ECS service config dev repository](https://github.com/companieshouse/ecs-service-configs-dev)
- [ECS service config production repository](https://github.com/companieshouse/ecs-service-configs-production)
