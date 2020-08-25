package uk.gov.companieshouse.uri.web.model;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SicCodes {

    @JsonProperty(value = "SicText")
    private String[] sicText;

    public String[] getSicText() {
        return sicText;
    }

    public void setSicCodes(String[] sicText) {
        this.sicText = sicText;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SicText [sicText=");
        builder.append(Arrays.toString(sicText));
        builder.append("]");
        return builder.toString();
    }
}
