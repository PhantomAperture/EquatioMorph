package de.daviddiviad.structure;

/**
 * Created by David on 11.02.2017.
 */

public interface Operation {

    int IDENTITY = 0;
    int UNARY = 1;
    int BINARY = 3;
    int ARBITRARY = -1;

    int getDimension();

    default boolean isIdentity () {
        return getDimension() == IDENTITY;
    }

    default boolean isUnary () {
        return getDimension() == UNARY;
    }

    default boolean isBinary () {
        return getDimension() == BINARY;
    }

    default boolean isArbitrary () {
        return getDimension() == ARBITRARY;
    }
}
