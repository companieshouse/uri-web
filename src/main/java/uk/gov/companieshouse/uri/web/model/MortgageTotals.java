package uk.gov.companieshouse.uri.web.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

public class MortgageTotals {
    
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty(value = "NumMortCharges")
    private int numMortCharges;
    
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty(value = "NumMortOutstanding")
    private int numMortOutstanding;
    
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty(value = "NumMortPartSatisfied")
    private int numMortPartSatisfied;
    
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty(value = "NumMortSatisfied")
    private int numMortSatisfied;

    public int getNumMortCharges() {
        return numMortCharges;
    }

    public void setNumMortCharges(int numMortCharges) {
        this.numMortCharges = numMortCharges;
    }

    public int getNumMortOutstanding() {
        return numMortOutstanding;
    }

    public void setNumMortOutstanding(int numMortOutstanding) {
        this.numMortOutstanding = numMortOutstanding;
    }

    public int getNumMortPartSatisfied() {
        return numMortPartSatisfied;
    }

    public void setNumMortPartSatisfied(int numMortPartSatisfied) {
        this.numMortPartSatisfied = numMortPartSatisfied;
    }

    public int getNumMortSatisfied() {
        return numMortSatisfied;
    }

    public void setNumMortSatisfied(int numMortSatisfied) {
        this.numMortSatisfied = numMortSatisfied;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Mortgages [numMortCharges=");
        builder.append(numMortCharges);
        builder.append(", numMortOutstanding=");
        builder.append(numMortOutstanding);
        builder.append(", numMortPartSatisfied=");
        builder.append(numMortPartSatisfied);
        builder.append(", numMortSatisfied=");
        builder.append(numMortSatisfied);
        builder.append("]");
        return builder.toString();
    }
}