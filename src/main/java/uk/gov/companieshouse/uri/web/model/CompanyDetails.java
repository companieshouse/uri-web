package uk.gov.companieshouse.uri.web.model;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CompanyDetails {

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
    @JsonProperty(value = "DissolutionDate")
    private String dissolutionDate;
    
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

    public String getCompanyStatus() {
        return companyStatus;
    }

    public void setCompanyStatus(String companyStatus) {
        this.companyStatus = companyStatus;
    }

    public String getIncorporationDate() {
        return incorporationDate;
    }

    public void setIncorporationDate(String incorporationDate) {
        this.incorporationDate = incorporationDate;
    }
    
    public String getDissolutionDate() {
        return dissolutionDate;
    }

    public void setDissolutionDate(String dissolutionDate) {
        this.dissolutionDate = dissolutionDate;
    }

    public PreviousName[] getPreviousNames() {
        return previousNames;
    }

    public void setPreviousNames(PreviousName[] previousNames) {
        this.previousNames = previousNames;
    }
    
    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public boolean hasCharges() {
        return hasCharges;
    }

    public void setHasCharges(boolean hasCharges) {
        this.hasCharges = hasCharges;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public Address getRegisteredOfficeAddress() {
        return registeredOfficeAddress;
    }

    public void setRegisteredOfficeAddress(Address registeredOfficeAddress) {
        this.registeredOfficeAddress = registeredOfficeAddress;
    }

    public SicCodes getSicCodes() {
        return sicCodes;
    }

    public void setSicCodes(SicCodes sicCodes) {
        this.sicCodes = sicCodes;
    }

    public MortgageTotals getMortgageTotals() {
        return mortgageTotals;
    }

    public void setMortgageTotals(MortgageTotals mortgageTotals) {
        this.mortgageTotals = mortgageTotals;
    }
    
    /**
     * Converts a string date of format 'DD/MM/YYYY' to 'YYYY-MM-DD'
     * for use in XML template output
     * If the supplied date is null or is not the correct format, then
     * the supplied string is returned unaltered
     * @param displayDateString - date string of the format 'DD/MM/YYYY'
     */    
    public String xmlDate(String displayDateString) {
        if (displayDateString != null && displayDateString.matches("^[0-9]{2}/[0-9]{2}/[0-9]{4}$")) {
            StringBuilder builder = new StringBuilder();
            builder.append(displayDateString.substring(6));
            builder.append('-');
            builder.append(displayDateString.substring(3, 5));
            builder.append('-');
            builder.append(displayDateString.substring(0, 2));
            return builder.toString();
        }
        return displayDateString;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("CompanyDetails [accounts=");
        builder.append(accounts);
        builder.append(", returns=");
        builder.append(returns);
        builder.append(", companyName=");
        builder.append(companyName);
        builder.append(", companyNumber=");
        builder.append(companyNumber);
        builder.append(", companyStatus=");
        builder.append(companyStatus);
        builder.append(", incorporationDate=");
        builder.append(incorporationDate);
        builder.append(", dissolutionDate=");
        builder.append(dissolutionDate);
        builder.append(", previousNames=");
        builder.append(Arrays.toString(previousNames));
        builder.append(", companyType=");
        builder.append(companyType);
        builder.append(", hasCharges=");
        builder.append(hasCharges);
        builder.append(", countryOfOrigin=");
        builder.append(countryOfOrigin);
        builder.append(", registeredOfficeAddress=");
        builder.append(registeredOfficeAddress);
        builder.append(", sicCodes=");
        builder.append(sicCodes);
        builder.append(", mortgageTotals=");
        builder.append(mortgageTotals);
        builder.append("]");
        return builder.toString();
    }
}