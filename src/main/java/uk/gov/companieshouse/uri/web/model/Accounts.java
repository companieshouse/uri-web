package uk.gov.companieshouse.uri.web.model;

public class Accounts {

    private String accountRefDay;
    
    private String accountRefMonth;
    
    private String nextDueDate;
    
    private String lastMadeUpDate;
    
    private String accountCategory;

    public String getAccountRefDay() {
        return accountRefDay;
    }

    public void setAccountRefDay(String accountRefDay) {
        this.accountRefDay = accountRefDay;
    }

    public String getAccountRefMonth() {
        return accountRefMonth;
    }

    public void setAccountRefMonth(String accountRefMonth) {
        this.accountRefMonth = accountRefMonth;
    }

    public String getNextDueDate() {
        return nextDueDate;
    }

    public void setNextDueDate(String nextDueDate) {
        this.nextDueDate = nextDueDate;
    }

    public String getLastMadeUpDate() {
        return lastMadeUpDate;
    }

    public void setLastMadeUpDate(String lastMadeUpDate) {
        this.lastMadeUpDate = lastMadeUpDate;
    }

    public String getAccountCategory() {
        return accountCategory;
    }

    public void setAccountCategory(String accountCategory) {
        this.accountCategory = accountCategory;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Accounts [accountRefDay=");
        builder.append(accountRefDay);
        builder.append(", accountRefMonth=");
        builder.append(accountRefMonth);
        builder.append(", nextDueDate=");
        builder.append(nextDueDate);
        builder.append(", lastMadeUpDate=");
        builder.append(lastMadeUpDate);
        builder.append(", accountCategory=");
        builder.append(accountCategory);
        builder.append("]");
        return builder.toString();
    }
}
