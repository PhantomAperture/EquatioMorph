package de.daviddiviad.serialize;

import java.util.ArrayList;

/**
 * Created by David on 10.02.2017.
 */
public abstract class AbstractEMLNode {

    private EMLOperation operation;
    private AbstractEMLNode parentNode;
    private final ArrayList<AbstractEMLNode> branches;

    private int layer;

    protected AbstractEMLNode() {
        branches = new ArrayList<>();
        this.layer = 0;
    }

    public abstract void setEMLOperation (EMLOperation operation) throws EMLOperation.UnsupportedEMLOperationException;

    protected void useEMLOperation (EMLOperation operation) {
        this.operation = operation;
    }

    public EMLOperation getEMLOperation () {
        return operation;
    }

    public abstract void addNode(AbstractEMLNode node) throws EMLOperation.DimensionOverflowException;

    protected void includeNode (AbstractEMLNode node) {
        this.branches.add(node);
        node.setParentNode(this);
    }

    public void removeNode (AbstractEMLNode node) {
        this.branches.remove(node);
    }

    public int getBranchCount() {
        return this.branches.size();
    }

    public boolean isEmpty () {
        return this.branches.isEmpty();
    }

    public void setParentNode(AbstractEMLNode parentNode) {
        this.parentNode = parentNode;
        this.layer = parentNode.getLayer() + 1;
    }

    public AbstractEMLNode getParentNode () {
        return parentNode;
    }

    public int getLayer() {
        return layer;
    }

    public AbstractEMLNode getRootNode() {
        AbstractEMLNode root = this;
        while (root.getParentNode() != null)
            root = root.getParentNode();
        return root;
    }

    @Override
    public String toString() {
        return operation.toString();
    }

    public String getTreeString () {
        StringBuilder builder = new StringBuilder();
        printTree("", true, builder);
        return builder.toString();
    }

    private void printTree(String prefix, boolean isTail, StringBuilder builder) {
        builder.append(prefix + (isTail ? "└── " : "├── ") + toString() + "\n");

        for (int i = 0; i < branches.size() - 1; i++) {
            branches.get(i).printTree(prefix + (isTail ? "    " : "│   "), false, builder);
        }

        if (branches.size() > 0) {
            branches.get(branches.size() - 1).printTree(prefix + (isTail ?"    " : "│   "), true, builder);
        }
    }
}
