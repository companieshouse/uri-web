package uk.gov.companieshouse.uri.web.model;

public class MortgageTotals {
    
    private int numMortCharges;
    
    private int numMortOutstanding;
    
    private int numMortPartSatisfied;
    
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