package uk.gov.companieshouse.uri.web.model;

import java.util.Arrays;

public class CompanyDetails {

    private Accounts accounts;

    private Returns returns;

    private String companyName;

    private String companyNumber;

    private String companyStatus;

    private String incorporationDate;
    
    private String dissolutionDate;
    
    private PreviousName[] previousNames;
    
    private String companyType;

    private boolean hasCharges;

    private String countryOfOrigin;

    private Address registeredOfficeAddress;

    private String[] sicCodes;
    
    private MortgageTotals mortgageTotals;

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

    public boolean isHasCharges() {
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

    public String[] getSicCodes() {
        return sicCodes;
    }

    public void setSicCodes(String[] sicCodes) {
        this.sicCodes = sicCodes;
    }

    public MortgageTotals getMortgageTotals() {
        return mortgageTotals;
    }

    public void setMortgageTotals(MortgageTotals mortgageTotals) {
        this.mortgageTotals = mortgageTotals;
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
        builder.append(Arrays.toString(sicCodes));
        builder.append(", mortgageTotals=");
        builder.append(mortgageTotals);
        builder.append("]");
        return builder.toString();
    }
}