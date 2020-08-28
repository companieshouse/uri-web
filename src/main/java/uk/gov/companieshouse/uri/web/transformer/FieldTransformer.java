package uk.gov.companieshouse.uri.web.transformer;

import org.apache.commons.validator.GenericValidator;

public class FieldTransformer {
    
    /**
     * Converts a string date of format 'dd/MM/yyyy' to 'yyyy-MM-dd'
     * for use in XML template output
     * If the supplied date is null or is not the correct format, then
     * the supplied string is returned unaltered
     * @param displayDateString - date string of the format 'dd/MM/yyyy'
     */    
    public String xmlDate(String displayDateString) {
        if (GenericValidator.isDate(displayDateString, "dd/MM/yyyy", true)) {
            final StringBuilder builder = new StringBuilder();
            builder.append(displayDateString.substring(6));
            builder.append('-');
            builder.append(displayDateString.substring(3, 5));
            builder.append('-');
            builder.append(displayDateString.substring(0, 2));
            return builder.toString();
        }
        return displayDateString;
    }
    
    /**
     * Escapes a string so that it is suitable for csv output
     * This is used to handle fields that may contain one or more " characters
     * @param unescapedString - string that may have " present
     */    
    public String csvEscape(String unescapedString) {
        return unescapedString == null ? null : unescapedString.replaceAll("\"", "\"\"");
    }
}
