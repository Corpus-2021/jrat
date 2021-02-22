package org.shiftone.jrat.ui.viewer.xml;



import org.shiftone.jrat.core.OutputViewerException;
import org.shiftone.jrat.core.spi.AbstractOutputXmlViewerFactory;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import javax.swing.JScrollPane;
import javax.swing.JTree;

import java.awt.Component;

import java.util.Properties;


/**
 * Class SimpleXmlOutputViewerFactory
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.1 $
 */
public class SimpleXmlOutputViewerFactory extends AbstractOutputXmlViewerFactory
{

    private static final Log  LOG         = LogFactory.getLogger(SimpleXmlOutputViewerFactory.class);
    private SimpleXmlTreeNode root        = null;
    private SimpleXmlTreeNode currentNode = null;

    /**
     * Method endDocumentCreateViewer
     *
     * @throws OutputViewerException
     */
    public Component endDocumentCreateViewer() throws OutputViewerException
    {

        JTree       tree       = new JTree(root);
        JScrollPane scrollPane = new JScrollPane(tree);

        return scrollPane;
    }

    /**
     * Method endElement
     *
     * @throws OutputViewerException
     */
    public void endElement(String qName) throws OutputViewerException
    {
        currentNode = (SimpleXmlTreeNode) currentNode.getParent();
    }

    /**
     * Method startDocument
     *
     * @throws OutputViewerException
     */
    public void startDocument() throws OutputViewerException
    {
        root        = new SimpleXmlTreeNode();
        currentNode = root;
    }

    /**
     * Method startElement
     *
     * @throws OutputViewerException
     */
    public void startElement(String qName, Properties atts) throws OutputViewerException
    {

        SimpleXmlTreeNode node = new SimpleXmlTreeNode(currentNode, qName, atts);

        currentNode.addChild(node);

        currentNode = node;
    }

    /**
     * Method textElement
     *
     * @throws OutputViewerException
     */
    public void textElement(String text) throws OutputViewerException {}
}
