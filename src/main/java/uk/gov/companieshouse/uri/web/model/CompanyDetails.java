package uk.gov.companieshouse.uri.web.model;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import uk.gov.companieshouse.uri.web.transformer.FieldTransformer;

public class CompanyDetails extends FieldTransformer {

    @JsonProperty(value = "CompanyName")
    private String companyName;

    @JsonProperty(value = "CompanyNumber")
    private String companyNumber;
    
    @JsonProperty(value = "RegAddress")
    private Address registeredOfficeAddress;

    @JsonProperty(value = "CompanyCategory")
    private String companyType;
    
    @JsonProperty(value = "CompanyStatus")
    private String companyStatus;
    
    @JsonProperty(value = "CountryOfOrigin")
    private String countryOfOrigin;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty(value = "RegistrationDate")
    private String registrationDate;
    
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty(value = "DissolutionDate")
    private String dissolutionDate;
    
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty(value = "IncorporationDate")
    private String incorporationDate;
    
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty(value = "PreviousNames")
    private PreviousName[] previousNames;
    
    @JsonProperty(value = "Accounts")
    private Accounts accounts;

    @JsonProperty(value = "Returns")
    private Returns returns;
    
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty(value = "Mortgages")
    private MortgageTotals mortgageTotals;
    
    @JsonIgnore
    private boolean hasCharges;

    @JsonProperty(value = "SICCodes")
    private SicCodes sicCodes;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyNumber() {
        return companyNumber;
    }

    public void setCompanyNumber(String companyNumber) {
        this.companyNumber = companyNumber;
    }

    public Address getRegisteredOfficeAddress() {
        return registeredOfficeAddress;
    }

    public void setRegisteredOfficeAddress(Address registeredOfficeAddress) {
        this.registeredOfficeAddress = registeredOfficeAddress;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getCompanyStatus() {
        return companyStatus;
    }

    public void setCompanyStatus(String companyStatus) {
        this.companyStatus = companyStatus;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getDissolutionDate() {
        return dissolutionDate;
    }

    public void setDissolutionDate(String dissolutionDate) {
        this.dissolutionDate = dissolutionDate;
    }

    public String getIncorporationDate() {
        return incorporationDate;
    }

    public void setIncorporationDate(String incorporationDate) {
        this.incorporationDate = incorporationDate;
    }

    public PreviousName[] getPreviousNames() {
        return previousNames;
    }

    public void setPreviousNames(PreviousName[] previousNames) {
        this.previousNames = previousNames;
    }

    public Accounts getAccounts() {
        return accounts;
    }

    public void setAccounts(Accounts accounts) {
        this.accounts = accounts;
    }

    public Returns getReturns() {
        return returns;
    }

    public void setReturns(Returns returns) {
        this.returns = returns;
    }

    public MortgageTotals getMortgageTotals() {
        return mortgageTotals;
    }

    public void setMortgageTotals(MortgageTotals mortgageTotals) {
        this.mortgageTotals = mortgageTotals;
    }

    public boolean hasCharges() {
        return hasCharges;
    }

    public void setHasCharges(boolean hasCharges) {
        this.hasCharges = hasCharges;
    }

    public SicCodes getSicCodes() {
        return sicCodes;
    }

    public void setSicCodes(SicCodes sicCodes) {
        this.sicCodes = sicCodes;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("CompanyDetails [companyName=");
        builder.append(companyName);
        builder.append(", companyNumber=");
        builder.append(companyNumber);
        builder.append(", registeredOfficeAddress=");
        builder.append(registeredOfficeAddress);
        builder.append(", companyType=");
        builder.append(companyType);
        builder.append(", companyStatus=");
        builder.append(companyStatus);
        builder.append(", countryOfOrigin=");
        builder.append(countryOfOrigin);
        builder.append(", registrationDate=");
        builder.append(registrationDate);
        builder.append(", dissolutionDate=");
        builder.append(dissolutionDate);
        builder.append(", incorporationDate=");
        builder.append(incorporationDate);
        builder.append(", previousNames=");
        builder.append(Arrays.toString(previousNames));
        builder.append(", accounts=");
        builder.append(accounts);
        builder.append(", returns=");
        builder.append(returns);
        builder.append(", mortgageTotals=");
        builder.append(mortgageTotals);
        builder.append(", hasCharges=");
        builder.append(hasCharges);
        builder.append(", sicCodes=");
        builder.append(sicCodes);
        builder.append("]");
        return builder.toString();
    }
}