package de.daviddiviad.serialize;

/**
 * Created by David on 10.02.2017.
 */

public class UnaryEMLNode extends AbstractEMLNode {

    public UnaryEMLNode(EMLOperation operation) throws EMLOperation.UnsupportedEMLOperationException {
        setEMLOperation(operation);
    }

    @Override
    public void setEMLOperation(EMLOperation operation) throws EMLOperation.UnsupportedEMLOperationException {
        if (operation.isUnary())
            useEMLOperation(operation);
        else throw new EMLOperation.UnsupportedEMLOperationException();
    }

    @Override
    public void addNode(AbstractEMLNode node) throws EMLOperation.DimensionOverflowException {
        if (getBranchCount() >= 1) throw new EMLOperation.DimensionOverflowException();
        else {
            node.setParentNode(this);
            includeNode(node);
        }
    }
}
