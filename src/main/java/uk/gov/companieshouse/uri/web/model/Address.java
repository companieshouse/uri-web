package uk.gov.companieshouse.uri.web.model;

public class Address {

    private String addressLine1;

    private String addressLine2;

    private String careOf;

    private String country;

    private String postTown;

    private String poBox;

    private String postCode;

    private String premises;

    private String region;

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCareOf() {
        return careOf;
    }

    public void setCareOf(String careOf) {
        this.careOf = careOf;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostTown() {
        return postTown;
    }

    public void setPostTown(String postTown) {
        this.postTown = postTown;
    }

    public String getPoBox() {
        return poBox;
    }

    public void setPoBox(String poBox) {
        this.poBox = poBox;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getPremises() {
        return premises;
    }

    public void setPremises(String premises) {
        this.premises = premises;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Address [addressLine1=");
        builder.append(addressLine1);
        builder.append(", addressLine2=");
        builder.append(addressLine2);
        builder.append(", careOf=");
        builder.append(careOf);
        builder.append(", country=");
        builder.append(country);
        builder.append(", postTown=");
        builder.append(postTown);
        builder.append(", poBox=");
        builder.append(poBox);
        builder.append(", postCode=");
        builder.append(postCode);
        builder.append(", premises=");
        builder.append(premises);
        builder.append(", region=");
        builder.append(region);
        builder.append("]");
        return builder.toString();
    }
}

