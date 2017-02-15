package de.daviddiviad.serialize;

/**
 * Created by David on 10.02.2017.
 */

public class ArbitraryEMLNode extends AbstractEMLNode {

    public ArbitraryEMLNode(EMLOperation operation) throws EMLOperation.UnsupportedEMLOperationException {
        setEMLOperation(operation);
    }

    @Override
    public void setEMLOperation(EMLOperation operation) throws EMLOperation.UnsupportedEMLOperationException {
        if (operation.isArbitrary())
            this.useEMLOperation(operation);
        else throw new IllegalArgumentException("Non-arbitrary operation in arbitrary node");
    }

    @Override
    public void addNode(AbstractEMLNode node) throws EMLOperation.DimensionOverflowException {
        includeNode(node);
    }
}
