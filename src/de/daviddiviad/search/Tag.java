package de.daviddiviad.search;

/**
 * Created by David on 09.02.2017.
 */

public class Tag {

    private final String value;

    public Tag(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
