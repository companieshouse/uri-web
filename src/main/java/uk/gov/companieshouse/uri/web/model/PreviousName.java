package uk.gov.companieshouse.uri.web.model;

public class PreviousName {

    String ceasedOn;
    
    String name;

    public String getCeasedOn() {
        return ceasedOn;
    }

    public void setCeasedOn(String ceasedOn) {
        this.ceasedOn = ceasedOn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("PreviousName [ceasedOn=");
        builder.append(ceasedOn);
        builder.append(", name=");
        builder.append(name);
        builder.append("]");
        return builder.toString();
    }
}
