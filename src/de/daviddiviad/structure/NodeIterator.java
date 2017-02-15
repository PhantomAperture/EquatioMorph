package de.daviddiviad.structure;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * Created by David on 13.02.2017.
 */
public class NodeIterator implements Iterator<Node> {

    private Node root;
    private Stack<Node> fringe;

    public NodeIterator(Node root) {
        this.root = root;
        this.fringe = new Stack<>();

        if (root != null) {
            fringe.push(root);
        }
    }

    @Override
    public boolean hasNext() {
        return !fringe.isEmpty();
    }

    @Override
    public Node next() {
        if (!hasNext ())
            throw new NoSuchElementException();

        Node node = fringe.pop();
        for (int i = 0; i < node.getBranches().size(); i++) {
            fringe.add((Node) node.getBranches().get(node.getBranches().size() - i - 1));
        }

        return node;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    public Node getRoot() {
        return root;
    }
}
