package de.daviddiviad.serialize;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by David on 10.02.2017.
 */

public class EMLParser extends DefaultHandler {

    private AbstractEMLNode currNode;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        EMLOperation currOperation = EMLOperation.fromStringValue(qName);
        if (currOperation == null) throw new IllegalArgumentException("Unsupported operation used in eml file: " + qName);

        AbstractEMLNode node = null;
        try {
            switch (currOperation.getDimension()) {
                case EMLOperation.UNARY:
                    node = new UnaryEMLNode(currOperation);
                    break;
                case EMLOperation.BINARY:
                    node = new BinaryEMLNode(currOperation);
                    break;
                default:
                    node = new ArbitraryEMLNode(currOperation);
                    break;
            }

            if (currNode == null)
                currNode = node;
            else {
                currNode.addNode(node);
                currNode = node;
            }
        } catch (EMLOperation.UnsupportedEMLOperationException e) {
            e.printStackTrace();
        } catch (EMLOperation.DimensionOverflowException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (currNode.getParentNode() != null)
            currNode = currNode.getParentNode();
    }

    public AbstractEMLNode pull() throws UnsupportedOperationException {
        if (currNode == null) throw new UnsupportedOperationException();
        else {
            AbstractEMLNode currNode = this.currNode;
            this.currNode = null;
            return currNode;
        }
    }
}