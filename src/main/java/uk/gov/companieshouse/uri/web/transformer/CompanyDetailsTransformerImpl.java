package uk.gov.companieshouse.uri.web.transformer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import uk.gov.companieshouse.api.model.charges.ChargesApi;
import uk.gov.companieshouse.api.model.company.AnnualReturnApi;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.api.model.company.ConfirmationStatementApi;
import uk.gov.companieshouse.api.model.company.PreviousCompanyNamesApi;
import uk.gov.companieshouse.api.model.company.RegisteredOfficeAddressApi;
import uk.gov.companieshouse.api.model.company.account.AccountingReferenceDateApi;
import uk.gov.companieshouse.api.model.company.account.CompanyAccountApi;
import uk.gov.companieshouse.api.model.company.account.LastAccountsApi;
import uk.gov.companieshouse.api.model.company.foreigncompany.ForeignCompanyDetailsApi;
import uk.gov.companieshouse.api.model.company.foreigncompany.OriginatingRegistryApi;
import uk.gov.companieshouse.uri.web.model.Accounts;
import uk.gov.companieshouse.uri.web.model.Address;
import uk.gov.companieshouse.uri.web.model.CompanyDetails;
import uk.gov.companieshouse.uri.web.model.MortgageTotals;
import uk.gov.companieshouse.uri.web.model.PreviousName;
import uk.gov.companieshouse.uri.web.model.Returns;
import uk.gov.companieshouse.uri.web.model.SicCodes;

@Component
public class CompanyDetailsTransformerImpl implements CompanyDetailsTransformer {

    private static final String COMPANY_TYPE_BUNDLE_PREFIX = "transform.type.";
    private static final String STATUS_BUNDLE_PREFIX = "transform.status.";
    private static final String JURISDICTION_BUNDLE_PREFIX = "transform.jurisdiction.";
    private static final String ACCOUNTS_TYPE_BUNDLE_PREFIX = "transform.accounts.";
    private static final String SIC_BUNDLE_PREFIX = "transform.sic.";
    private static final String NO_SIC_AVAILABLE = "None Supplied";
    private static final String PROFILE_LINKS_CHARGES = "charges";
    private static final String OVERSEA_COMPANY = "oversea-company";
    private static final String COMPANY_STATUS_DISSOLVED = "dissolved";
    private static final String COMPANY_STATUS_CLOSED = "converted-closed";
    
    private ResourceBundle bundle;
    
    
    public CompanyDetailsTransformerImpl(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    @Override
    public CompanyDetails profileApiToDetails(CompanyProfileApi companyProfileApi) {
        
        CompanyDetails companyDetails = new CompanyDetails();
        
        companyDetails.setCompanyName(companyProfileApi.getCompanyName());
        companyDetails.setCompanyNumber(companyProfileApi.getCompanyNumber());
        
        companyDetails.setRegisteredOfficeAddress(getRegisteredOfficeAddressDetails(companyProfileApi));
        
        companyDetails.setCompanyType(transformCompanyType(companyProfileApi.getType()));
        
        if (companyProfileApi.getCompanyStatusDetail() != null) {
            companyDetails.setCompanyStatus(transformCompanyStatus(companyProfileApi.getCompanyStatusDetail()));
        } else {
            companyDetails.setCompanyStatus(transformCompanyStatus(companyProfileApi.getCompanyStatus()));
        }
        
        String foreignCompanyRegistryCountry = getForeignCompanyRegistryCountry(companyProfileApi);
        if (foreignCompanyRegistryCountry != null) {
            companyDetails.setCountryOfOrigin(foreignCompanyRegistryCountry);
        } else {
            companyDetails.setCountryOfOrigin(transformCompanyJurisdiction(companyProfileApi.getJurisdiction()));
        }
        
        if (isCompanyDissolved(companyProfileApi)) {
            companyDetails.setDissolutionDate(transformDate(companyProfileApi.getDateOfCessation()));
        }

        if (useIncDate(companyProfileApi)) {
            companyDetails.setIncorporationDate(transformDate(companyProfileApi.getDateOfCreation()));
        } else {
            companyDetails.setRegistrationDate(transformDate(companyProfileApi.getDateOfCreation()));
        }
        
        companyDetails.setPreviousNames(transformPreviousNames(companyProfileApi.getPreviousCompanyNames()));
        
        companyDetails.setAccounts(getAccountsDetails(companyProfileApi));
        
        companyDetails.setReturns(getReturnsDetails(companyProfileApi));
        
        companyDetails.setSicCodes(transformSIC(companyProfileApi.getSicCodes()));
        
        if (companyProfileApi.getLinks() != null && 
                companyProfileApi.getLinks().containsKey(PROFILE_LINKS_CHARGES)) {
            companyDetails.setHasCharges(true);
        }
        
        return companyDetails;
    }
    
    @Override
    public MortgageTotals chargesApiToMortgageTotals(ChargesApi chargesApi) {
        MortgageTotals mortgageTotals = new MortgageTotals();
        
        mortgageTotals.setNumMortCharges(transformChargeCount(chargesApi.getTotalCount()));
        mortgageTotals.setNumMortSatisfied(transformChargeCount(chargesApi.getSatisfiedCount()));
        mortgageTotals.setNumMortPartSatisfied(transformChargeCount(chargesApi.getPartSatisfiedCount()));
        mortgageTotals.setNumMortOutstanding(mortgageTotals.getNumMortCharges()
                - mortgageTotals.getNumMortSatisfied() 
                - mortgageTotals.getNumMortPartSatisfied());
        
        return mortgageTotals;
    }
    
    private Address getRegisteredOfficeAddressDetails(CompanyProfileApi companyProfileApi) {
        Address detailsAddress = new Address();
        RegisteredOfficeAddressApi apiAddress = companyProfileApi.getRegisteredOfficeAddress();
        if (apiAddress != null) {
            detailsAddress.setCareOf(upper(apiAddress.getCareOf()));
            detailsAddress.setPoBox(upper(apiAddress.getPoBox()));
            detailsAddress.setPremises(upper(apiAddress.getPremises()));
            detailsAddress.setAddressLine1(upper(apiAddress.getAddressLine1()));
            detailsAddress.setAddressLine2(upper(apiAddress.getAddressLine2()));
            detailsAddress.setPostTown(upper(apiAddress.getLocality()));
            detailsAddress.setRegion(upper(apiAddress.getRegion()));
            detailsAddress.setCountry(upper(apiAddress.getCountry()));
            detailsAddress.setPostCode(upper(apiAddress.getPostalCode()));
        }
        return detailsAddress;
    }
    
    private Accounts getAccountsDetails(CompanyProfileApi companyProfileApi) {
        Accounts accounts = new Accounts();
        CompanyAccountApi apiAccounts = companyProfileApi.getAccounts();
        if (apiAccounts != null) {
            AccountingReferenceDateApi accountingRefernceDateApi = apiAccounts.getAccountingReferenceDate();
            if (accountingRefernceDateApi != null) {
                accounts.setAccountRefDay(accountingRefernceDateApi.getDay());
                accounts.setAccountRefMonth(accountingRefernceDateApi.getMonth());
            }
            accounts.setNextDueDate(transformDate(apiAccounts.getNextDue()));
            LastAccountsApi apiLastAccounts = apiAccounts.getLastAccounts();
            if (apiLastAccounts != null) {
                accounts.setLastMadeUpDate(transformDate(apiLastAccounts.getMadeUpTo()));
                accounts.setAccountCategory(transformAccountsType(apiLastAccounts.getType()));
            }
        }
        return accounts;
    }
    
    private Returns getReturnsDetails(CompanyProfileApi companyProfileApi) {
        Returns returns = new Returns();
        ConfirmationStatementApi apiConfirmationStatement =  companyProfileApi.getConfirmationStatement();
        if (apiConfirmationStatement != null) {
            returns.setNextDueDate(transformDate(apiConfirmationStatement.getNextDue()));
            returns.setLastMadeUpDate(transformDate(apiConfirmationStatement.getLastMadeUpTo()));
        } else {
            //Fall back to annual return dates
            AnnualReturnApi apiReturns = companyProfileApi.getAnnualReturn();
            if(apiReturns != null) {
                returns.setNextDueDate(transformDate(apiReturns.getNextDue()));
                returns.setLastMadeUpDate(transformDate(apiReturns.getLastMadeUpTo()));
            }
        }
        return returns;
    }
    
    private String upper(String value) {
        return value == null ? null : value.toUpperCase();
    }
    
    private String transformCompanyType(String type) {
        return getValueFromBundle(COMPANY_TYPE_BUNDLE_PREFIX + type);
    }
    
    private String transformCompanyStatus(String status) {
        return getValueFromBundle(STATUS_BUNDLE_PREFIX + status);
    }
    
    private String transformCompanyJurisdiction(String jurisdiction) {
        return getValueFromBundle(JURISDICTION_BUNDLE_PREFIX + jurisdiction);
    }
    
    private String transformAccountsType(String accountsType) {
        if (accountsType == null) {
            return "";
        }
        return getValueFromBundle(ACCOUNTS_TYPE_BUNDLE_PREFIX + accountsType);
    }
    
    private String getValueFromBundle(String key) {
        try {
            return bundle.getString(key);
        }
        catch (MissingResourceException | ClassCastException | NullPointerException e) {
            return key;
        }
    }
    
    private String transformDate(LocalDate date) {
        return date == null ? null : date.format(DateTimeFormatter.ofPattern("dd/MM/uuuu"));
    }

    private PreviousName[] transformPreviousNames(List<PreviousCompanyNamesApi> apiPreviousNames) { 
        if(apiPreviousNames == null) {
            return new PreviousName[0];
        }
        
        List<PreviousName> transformedPreviousNames = new ArrayList<>(); 
        for (PreviousCompanyNamesApi apiPreviousName : apiPreviousNames) {
            PreviousName previousName = new PreviousName();
            previousName.setCeasedOn(transformDate(apiPreviousName.getCeasedOn()));
            previousName.setName(apiPreviousName.getName());
            transformedPreviousNames.add(previousName);
        }
        return transformedPreviousNames.toArray(new PreviousName[0]);
    }
    
    private SicCodes transformSIC(String[] apiSICArray) {
        SicCodes sicCodes = new SicCodes();
        if(apiSICArray == null) {
            sicCodes.setSicCodes(new String[]{NO_SIC_AVAILABLE});
            return sicCodes;
        }
        
        List<String> transformedSICs = new ArrayList<>(); 
        for (String sic : apiSICArray) {
            transformedSICs.add(getValueFromBundle(SIC_BUNDLE_PREFIX + sic));
        }
        sicCodes.setSicCodes(transformedSICs.toArray(apiSICArray));
        return sicCodes;
    }
    
    private int transformChargeCount(Long count) {      
        return count == null ? 0 : count.intValue();   
    }
    
    private boolean isCompanyDissolved(CompanyProfileApi companyProfileApi) {
        return COMPANY_STATUS_DISSOLVED.equals(companyProfileApi.getCompanyStatus()) || 
                COMPANY_STATUS_CLOSED.equals(companyProfileApi.getCompanyStatus());
    }
    
    private String getForeignCompanyRegistryCountry(CompanyProfileApi companyProfileApi) {
        if (OVERSEA_COMPANY.equals(companyProfileApi.getType())) {
            return Optional.ofNullable(companyProfileApi.getForeignCompanyDetails())
                    .map(ForeignCompanyDetailsApi::getOriginatingRegistry)
                    .map(OriginatingRegistryApi::getCountry)
                    .orElse(null);
        }
        return null;
    }
    
    private boolean useIncDate(CompanyProfileApi companyProfileApi) {
        String companyNumber = companyProfileApi.getCompanyNumber();
        
        return companyNumber == null ||
                companyNumber.startsWith("SC") ||
                companyNumber.startsWith("OC") ||
                companyNumber.startsWith("SO") ||
                StringUtils.isNumeric(companyNumber);
    }
}
