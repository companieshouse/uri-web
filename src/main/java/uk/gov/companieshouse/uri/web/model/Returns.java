package uk.gov.companieshouse.uri.web.model;

public class Returns {
    
    private String nextDueDate;
    
    private String lastMadeUpDate;

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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Returns [nextDueDate=");
        builder.append(nextDueDate);
        builder.append(", lastMadeUpDate=");
        builder.append(lastMadeUpDate);
        builder.append("]");
        return builder.toString();
    }
}
