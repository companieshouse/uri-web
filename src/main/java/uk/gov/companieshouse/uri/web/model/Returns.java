package uk.gov.companieshouse.uri.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Returns {
    
    @JsonProperty(value = "NextDueDate")
    private String nextDueDate;
    
    @JsonProperty(value = "LastMadeUpDate")
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
