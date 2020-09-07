package uk.gov.companieshouse.uri.web.transformer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ListResourceBundle;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import uk.gov.companieshouse.api.model.charges.ChargesApi;
import uk.gov.companieshouse.api.model.company.AnnualReturnApi;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.api.model.company.ConfirmationStatementApi;
import uk.gov.companieshouse.api.model.company.PreviousCompanyNamesApi;
import uk.gov.companieshouse.api.model.company.RegisteredOfficeAddressApi;
import uk.gov.companieshouse.api.model.company.account.AccountingReferenceDateApi;
import uk.gov.companieshouse.api.model.company.account.CompanyAccountApi;
import uk.gov.companieshouse.api.model.company.account.LastAccountsApi;
import uk.gov.companieshouse.uri.web.model.CompanyDetails;
import uk.gov.companieshouse.uri.web.model.MortgageTotals;
import uk.gov.companieshouse.uri.web.model.SicCodes;
import uk.gov.companieshouse.uri.web.transformer.CompanyDetailsTransformer;

@ExtendWith(MockitoExtension.class)
class CompanyDetailsTransformerImplTest {

    private CompanyDetailsTransformer testCompanyDetailsTransformer;

    @BeforeEach
    protected void setUp() {
        testCompanyDetailsTransformer = new CompanyDetailsTransformerImpl(null);
    }

    @Test
    void profileApiToDetailsEmptyCompanyProfileApi() {
        CompanyDetails companyDetails = testCompanyDetailsTransformer.profileApiToDetails(new CompanyProfileApi());
        
        assertNull(companyDetails.getCompanyNumber());
        assertNull(companyDetails.getCompanyName());
        assertNull(companyDetails.getIncorporationDate());
        assertNull(companyDetails.getDissolutionDate());
        assertEquals("transform.status.null", companyDetails.getCompanyStatus());
        assertEquals("transform.type.null", companyDetails.getCompanyType());
        assertEquals("transform.jurisdiction.null", companyDetails.getCountryOfOrigin());
        assertNull(companyDetails.getAccounts().getAccountCategory());
        assertNull(companyDetails.getAccounts().getAccountRefDay());
        assertNull(companyDetails.getAccounts().getAccountRefMonth());
        assertNull(companyDetails.getAccounts().getLastMadeUpDate());
        assertNull(companyDetails.getAccounts().getNextDueDate());
        assertNull(companyDetails.getReturns().getLastMadeUpDate());
        assertNull(companyDetails.getReturns().getNextDueDate());
        assertEquals(0, companyDetails.getPreviousNames().length);
        assertNull(companyDetails.getRegisteredOfficeAddress().getCareOf());
        assertNull(companyDetails.getRegisteredOfficeAddress().getPoBox());
        assertNull(companyDetails.getRegisteredOfficeAddress().getPremises());
        assertNull(companyDetails.getRegisteredOfficeAddress().getAddressLine1());
        assertNull(companyDetails.getRegisteredOfficeAddress().getAddressLine2());
        assertNull(companyDetails.getRegisteredOfficeAddress().getPostTown());
        assertNull(companyDetails.getRegisteredOfficeAddress().getRegion());
        assertNull(companyDetails.getRegisteredOfficeAddress().getCountry());
        assertNull(companyDetails.getRegisteredOfficeAddress().getPostCode());
        assertEquals(1, companyDetails.getSicCodes().getSicText().length);
        assertEquals("None Supplied", companyDetails.getSicCodes().getSicText()[0]);
        assertFalse(companyDetails.hasCharges());
    }
    
    @Test
    void profileApiToDetailsPopulatedCompanyProfileApi() {
        CompanyDetails companyDetails = testCompanyDetailsTransformer.profileApiToDetails(populatedCompanyProfileApi());
        
        assertEquals("05448736", companyDetails.getCompanyNumber());
        assertEquals("Test Company Name Limited", companyDetails.getCompanyName());
        assertEquals("01/01/2017", companyDetails.getIncorporationDate());
        assertEquals("03/02/2020", companyDetails.getDissolutionDate());
        assertEquals("transform.status.status-detail", companyDetails.getCompanyStatus());
        assertEquals("transform.type.ltd", companyDetails.getCompanyType());
        assertEquals("transform.jurisdiction.england-wales", companyDetails.getCountryOfOrigin());
        assertEquals("transform.accounts.micro", companyDetails.getAccounts().getAccountCategory());
        assertEquals("21", companyDetails.getAccounts().getAccountRefDay());
        assertEquals("2", companyDetails.getAccounts().getAccountRefMonth());
        assertEquals("31/07/2019", companyDetails.getAccounts().getLastMadeUpDate());
        assertEquals("31/07/2020", companyDetails.getAccounts().getNextDueDate());
        assertEquals("30/06/2019", companyDetails.getReturns().getLastMadeUpDate());
        assertEquals("28/07/2020", companyDetails.getReturns().getNextDueDate());
        assertEquals(2, companyDetails.getPreviousNames().length);
        assertEquals("31/10/2018", companyDetails.getPreviousNames()[0].getCeasedOn());
        assertEquals("AN OLD NAME LIMITED", companyDetails.getPreviousNames()[0].getName());
        assertEquals("30/04/2018", companyDetails.getPreviousNames()[1].getCeasedOn());
        assertEquals("AN EVEN OLDER NAME LIMITED", companyDetails.getPreviousNames()[1].getName());
        assertEquals("CARE OF", companyDetails.getRegisteredOfficeAddress().getCareOf());
        assertEquals("PO BOX", companyDetails.getRegisteredOfficeAddress().getPoBox());
        assertEquals("BIG HOUSE", companyDetails.getRegisteredOfficeAddress().getPremises());
        assertEquals("ADDRESS LINE 1", companyDetails.getRegisteredOfficeAddress().getAddressLine1());
        assertEquals("ADDRESS LINE 2", companyDetails.getRegisteredOfficeAddress().getAddressLine2());
        assertEquals("DURSLEY", companyDetails.getRegisteredOfficeAddress().getPostTown());
        assertEquals("GLOUCESTERSHIRE", companyDetails.getRegisteredOfficeAddress().getRegion());
        assertEquals("ENGLAND", companyDetails.getRegisteredOfficeAddress().getCountry());
        assertEquals("GL11 6HY", companyDetails.getRegisteredOfficeAddress().getPostCode());
        SicCodes sicCodes = companyDetails.getSicCodes();
        assertEquals(2, sicCodes.getSicText().length);
        assertEquals("transform.sic.51110", sicCodes.getSicText()[0]);
        assertEquals("transform.sic.23423", sicCodes.getSicText()[1]);
        assertTrue(companyDetails.hasCharges());
    }
    
    @Test
    void profileApiToDetailsMissingConfirmationStatementDetails() {
        CompanyProfileApi companyProfileApi = new CompanyProfileApi();
        
        AnnualReturnApi annualReturnApi = new AnnualReturnApi();
        annualReturnApi.setLastMadeUpTo(LocalDate.of(2019, 6, 30));
        annualReturnApi.setNextDue(LocalDate.of(2020, 7, 28));
        companyProfileApi.setAnnualReturn(annualReturnApi);
        
        CompanyDetails companyDetails = testCompanyDetailsTransformer.profileApiToDetails(companyProfileApi);
        
        assertNull(companyProfileApi.getConfirmationStatement());
        assertEquals("30/06/2019", companyDetails.getReturns().getLastMadeUpDate());
        assertEquals("28/07/2020", companyDetails.getReturns().getNextDueDate());
    }
    
    @Test
    void profileApiToDetailsBundleKeyFound() {
        CompanyProfileApi companyProfileApi = new CompanyProfileApi();
        companyProfileApi.setType("find-me");
        
        testCompanyDetailsTransformer = new CompanyDetailsTransformerImpl(new MsgResourceBundle());
        
        CompanyDetails companyDetails = testCompanyDetailsTransformer.profileApiToDetails(companyProfileApi);
        
        assertEquals("The key was found", companyDetails.getCompanyType());
    }
    
    @Test
    void profileApiToDetailsNullAddressField() {
        CompanyProfileApi companyProfileApi = populatedCompanyProfileApi();
        companyProfileApi.getRegisteredOfficeAddress().setCountry(null);

        CompanyDetails companyDetails = testCompanyDetailsTransformer.profileApiToDetails(companyProfileApi);
        
        assertNull(companyDetails.getRegisteredOfficeAddress().getCountry());
    }
    
    @Test
    void profileApiToDetailsNullAccountsType() {
        CompanyProfileApi companyProfileApi = populatedCompanyProfileApi();
        companyProfileApi.getAccounts().getLastAccounts().setType(null);

        CompanyDetails companyDetails = testCompanyDetailsTransformer.profileApiToDetails(companyProfileApi);
        
        assertEquals("", companyDetails.getAccounts().getAccountCategory());
    }
    
    @Test
    void profileApiToDetailsNullAccountingReferenceDateApi() {
        CompanyProfileApi companyProfileApi = populatedCompanyProfileApi();
        companyProfileApi.getAccounts().setAccountingReferenceDate(null);

        CompanyDetails companyDetails = testCompanyDetailsTransformer.profileApiToDetails(companyProfileApi);
        
        assertNull(companyDetails.getAccounts().getAccountRefDay());
        assertNull(companyDetails.getAccounts().getAccountRefMonth());
    }
    
    @Test
    void profileApiToDetailsMissingLastAccounts() {
        CompanyProfileApi companyProfileApi = populatedCompanyProfileApi();
        companyProfileApi.getAccounts().setLastAccounts(null);;

        CompanyDetails companyDetails = testCompanyDetailsTransformer.profileApiToDetails(companyProfileApi);
        
        assertNull(companyDetails.getAccounts().getLastMadeUpDate());
        assertNull(companyDetails.getAccounts().getAccountCategory());
    }
    
    @Test
    void profileApiToDetailsMissingChargesLink() {
        CompanyProfileApi companyProfileApi = populatedCompanyProfileApi();
        companyProfileApi.getLinks().clear();

        CompanyDetails companyDetails = testCompanyDetailsTransformer.profileApiToDetails(companyProfileApi);
        
        assertFalse(companyDetails.hasCharges());
    }
    
    private CompanyProfileApi populatedCompanyProfileApi() {
        CompanyProfileApi companyProfileApi = new CompanyProfileApi();
        
        companyProfileApi.setCompanyNumber("05448736");
        companyProfileApi.setCompanyName("Test Company Name Limited");
        companyProfileApi.setDateOfCreation(LocalDate.of(2017, 1, 1));
        companyProfileApi.setDateOfCessation(LocalDate.of(2020, 2, 3));
        companyProfileApi.setCompanyStatus("status");
        companyProfileApi.setCompanyStatusDetail("status-detail");
        companyProfileApi.setType("ltd");
        companyProfileApi.setJurisdiction("england-wales");
        
        CompanyAccountApi companyAccountApi = new CompanyAccountApi();
        AccountingReferenceDateApi accountingReferenceDateApi = new AccountingReferenceDateApi();
        accountingReferenceDateApi.setDay("21");
        accountingReferenceDateApi.setMonth("2");
        companyAccountApi.setAccountingReferenceDate(accountingReferenceDateApi);
        companyAccountApi.setNextDue(LocalDate.of(2020, 7, 31));
        LastAccountsApi lastAccountsApi = new LastAccountsApi();
        lastAccountsApi.setMadeUpTo(LocalDate.of(2019, 7, 31));
        lastAccountsApi.setType("micro");
        companyAccountApi.setLastAccounts(lastAccountsApi);
        companyProfileApi.setAccounts(companyAccountApi);
        
        ConfirmationStatementApi confirmationStatementApi  = new ConfirmationStatementApi();
        confirmationStatementApi.setLastMadeUpTo(LocalDate.of(2019, 6, 30));
        confirmationStatementApi.setNextDue(LocalDate.of(2020, 7, 28));
        companyProfileApi.setConfirmationStatement(confirmationStatementApi);
        
        RegisteredOfficeAddressApi registeredOfficeAddressApi = new RegisteredOfficeAddressApi();
        registeredOfficeAddressApi.setCareOf("Care Of");
        registeredOfficeAddressApi.setPoBox("Po Box");
        registeredOfficeAddressApi.setPremises("Big House");
        registeredOfficeAddressApi.setAddressLine1("Address Line 1");
        registeredOfficeAddressApi.setAddressLine2("Address Line 2");
        registeredOfficeAddressApi.setLocality("Dursley");
        registeredOfficeAddressApi.setRegion("Gloucestershire");
        registeredOfficeAddressApi.setCountry("England");
        registeredOfficeAddressApi.setPostalCode("Gl11 6hy");
        companyProfileApi.setRegisteredOfficeAddress(registeredOfficeAddressApi);
        
        List<PreviousCompanyNamesApi> previousCompanyNames = new ArrayList<>();
        PreviousCompanyNamesApi previousCompanyNamesApi = new PreviousCompanyNamesApi();
        previousCompanyNamesApi.setName("AN OLD NAME LIMITED");
        previousCompanyNamesApi.setCeasedOn(LocalDate.of(2018,  10, 31));
        previousCompanyNames.add(previousCompanyNamesApi);
        previousCompanyNamesApi = new PreviousCompanyNamesApi();
        previousCompanyNamesApi.setName("AN EVEN OLDER NAME LIMITED");
        previousCompanyNamesApi.setCeasedOn(LocalDate.of(2018,  04, 30));
        previousCompanyNames.add(previousCompanyNamesApi);
        companyProfileApi.setPreviousCompanyNames(previousCompanyNames);
        
        String[] sicCodes = {"51110", "23423"};
        companyProfileApi.setSicCodes(sicCodes);
        
        Map<String,String> links = new TreeMap<>();
        links.put("charges", "/company/05448736/charges");
        companyProfileApi.setLinks(links);
        
        return companyProfileApi;
    }
    
    @Test
    void chargesApiToMortgageTotalsEmptyChargesApi() {
        MortgageTotals mortgageTotals = testCompanyDetailsTransformer.chargesApiToMortgageTotals(new ChargesApi());
        
        assertEquals(0, mortgageTotals.getNumMortCharges());
        assertEquals(0, mortgageTotals.getNumMortSatisfied());
        assertEquals(0, mortgageTotals.getNumMortPartSatisfied());
        assertEquals(0, mortgageTotals.getNumMortOutstanding());
    }
    
    @Test
    void chargesApiToMortgageTotalsPopulatedChargesApi() {
        ChargesApi chargesApi = new ChargesApi();
        chargesApi.setTotalCount(21l);
        chargesApi.setSatisfiedCount(5l);
        chargesApi.setPartSatisfiedCount(2l);
        
        MortgageTotals mortgageTotals = testCompanyDetailsTransformer.chargesApiToMortgageTotals(chargesApi);
        
        assertEquals(21, mortgageTotals.getNumMortCharges());
        assertEquals(5, mortgageTotals.getNumMortSatisfied());
        assertEquals(2, mortgageTotals.getNumMortPartSatisfied());
        assertEquals(14, mortgageTotals.getNumMortOutstanding());
    }
    
    class MsgResourceBundle extends ListResourceBundle {
        private Object[][] contents = new Object[][]{
            {"transform.type.find-me","The key was found"}
        };
        protected Object[][] getContents() {
            return contents;
        }
   };
}