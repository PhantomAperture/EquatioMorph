package de.daviddiviad.structure;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 13.02.2017.
 *
 * @param <C> The type of the content of this node
 * @param <T> The type of appendable nodes
 */
public class Node<C, T> {

    private Node parent;
    private List<Node<?, T>> branches;
    private int layer;
    private C content;

    public Node() {
        this.parent = null;
        this.branches = new ArrayList<>();
    }

    public Node(C content) {
        this();
        this.content = content;
    }

    public Node(Node<?, T> parent) {
        this();
        setParent(parent);

        if (parent != null)
            this.layer = parent.getLayer() + 1;
        else
            this.layer = 0;
    }

    public Node(Node<?, T> parent, C content) {
        this(parent);
        this.content = content;
    }

    public Node<?, T> getParent() {
        return parent;
    }

    public void setParent(Node<?, T> parent) {
        if (this.parent != parent) {
            this.parent = parent;
            parent.addBranch(this);
            this.layer = parent.getLayer() + 1;
        }
    }

    public List<Node<?, T>> getBranches() {
        return branches;
    }

    public void addBranch (Node<?, T> node) {
        node.setParent(this);
        this.branches.add(node);
    }

    public void removeBranch(Node<?, T> node) {
        this.branches.remove(node);
    }

    public int size () {
        return this.branches.size();
    }

    public boolean isTrail () {
        return this.branches.isEmpty();
    }

    public int getLayer() {
        return layer;
    }

    public C getContent() {
        return content;
    }

    public void setContent(C content) {
        this.content = content;
    }

    public boolean isRoot() {
        return parent == null;
    }

    public Node<?, T> getRootNode () {
        if (isRoot()) return this;
        else {
            Node root = this;
            while (root.getParent() != null)
                root = root.getParent();
            return root;
        }
    }

    public NodeIterator iterator() {
        return new NodeIterator(this);
    }

    @Override
    public String toString() {
        return (getContent() != null ? "Node[" + getContent().toString() + "]": "Node[null]");
    }

    public String toTreeString() {
        StringBuilder builder = new StringBuilder();
        printTree("", true, builder);
        return builder.toString();
    }

    private void printTree(String prefix, boolean isTail, StringBuilder builder) {
        builder.append(prefix + (isTail ? "└── " : "├── ") + (getContent() != null ? getContent().toString() : "null") + "\n");

        for (int i = 0; i < branches.size() - 1; i++) {
            branches.get(i).printTree(prefix + (isTail ? "    " : "│   "), false, builder);
        }

        if (branches.size() > 0) {
            branches.get(branches.size() - 1).printTree(prefix + (isTail ?"    " : "│   "), true, builder);
        }
    }
}
