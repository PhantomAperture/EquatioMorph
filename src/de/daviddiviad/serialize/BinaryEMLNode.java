package de.daviddiviad.serialize;

/**
 * Created by David on 10.02.2017.
 */

public class BinaryEMLNode extends AbstractEMLNode {

    public BinaryEMLNode(EMLOperation operation) throws EMLOperation.UnsupportedEMLOperationException {
        setEMLOperation(operation);
    }

    @Override
    public void setEMLOperation(EMLOperation operation) throws EMLOperation.UnsupportedEMLOperationException {
        if (operation.isBinary())
             useEMLOperation(operation);
        else throw new IllegalArgumentException("Non-binary operation in binary node");
    }

    @Override
    public void addNode(AbstractEMLNode node) throws EMLOperation.DimensionOverflowException {
        if (this.getBranchCount() >= 2) throw new UnsupportedOperationException("Attempting to add more than two nodes to binary operation");
        else {
            includeNode(node);
            node.setParentNode(this);
        }
    }
}
