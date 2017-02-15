package de.daviddiviad.serialize;

/**
 * Created by David on 10.02.2017.
 */
public enum EMLOperation {

    ABSOLUTE("abs", EMLOperation.UNARY),
    ADDITION("sum"),
    BRACKET("b"),
    CONSTANT("cons", EMLOperation.UNARY),
    DIFFERENTIAL("diff", EMLOperation.UNARY),
    DIVISION("div", EMLOperation.BINARY),
    FUNCTION("func", EMLOperation.UNARY),
    MULTIPLICATION("mult"),
    NEGATION("neg", EMLOperation.UNARY),
    POWER("pow", EMLOperation.BINARY),
    VARIABLE("var");
    //SUBTRACTION("sub", Operation.BINARY); Replaced by sum with negative entities

    public static final int IDENTITY = 0;
    public static final int UNARY = 1;
    public static final int BINARY = 3;
    public static final int ARBITRARY = -1;

    private final int dimension;
    private final String xmlTag;

    EMLOperation(String xmlTag, int dimension) {
        if (xmlTag.matches("[A-Za-z0-9_]*"))
            this.xmlTag = xmlTag;
        else throw new IllegalArgumentException();

        if (dimension >= ARBITRARY && dimension <= BINARY)
            this.dimension = dimension;
        else throw new IllegalArgumentException();
    }

    EMLOperation(String stringValue) {
        this(stringValue, ARBITRARY);
    }

    public boolean isBinary() {
        return dimension == BINARY;
    }

    public boolean isUnary () {
        return dimension == UNARY;
    }

    public boolean isIdentity () {
        return dimension == IDENTITY;
    }

    public boolean isArbitrary() {
        return dimension == ARBITRARY;
    }

    public int getDimension() {
        return dimension;
    }

    public String getXmlTag() {
        return xmlTag;
    }

    //TODO Implement language support
    public String getName () {
        return "";
    }

    public static EMLOperation fromStringValue(String xmlTag) {
        for (EMLOperation tag : EMLOperation.values()) {
            if (tag.getXmlTag().equalsIgnoreCase(xmlTag)) return tag;
        }
        return null;
    }

    public static class UnsupportedEMLOperationException extends Exception {

    }

    public static class DimensionOverflowException extends Exception {

    }
}
