package org.shiftone.jrat.provider.tree.ui;

import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.core.OutputViewerException;

import org.shiftone.jrat.core.spi.AbstractOutputXmlViewerFactory;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.awt.Component;
import java.util.Properties;


/**
 * Class TreeOutputXmlViewerFactory
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.8 $
 */
public class TreeOutputXmlViewerFactory extends AbstractOutputXmlViewerFactory
{
    private static final Log LOG         = LogFactory.getLogger(TreeOutputXmlViewerFactory.class);
    private StackTreeNode    root        = new StackTreeNode();
    private StackTreeNode    currentNode = root;

    /**
     * Method startElement
     *
     * @param qName .
     * @param atts .
     *
     * @throws OutputViewerException
     */
    public void startElement(String qName, Properties atts)
        throws OutputViewerException
    {
        if ("call".equals(qName))
        {
            MethodKey methodKey = new MethodKey(atts.getProperty("c"), atts.getProperty("m"), atts.getProperty("s"));

            currentNode = (StackTreeNode) currentNode.getChild(methodKey);

            String minString = atts.getProperty("min");
            String maxString = atts.getProperty("max");
            long   min       = (minString != null) ? Long.parseLong(minString) : 0;
            long max = (maxString != null) ? Long.parseLong(maxString) : 0;

            // todo : remove the sos default - this just allows old XML files to be read
            currentNode.setStatistics(
                Long.parseLong(atts.getProperty("ent")),        //
                Long.parseLong(atts.getProperty("xit")),        //
                Long.parseLong(atts.getProperty("err")),        //
                Long.parseLong(atts.getProperty("dur")),        //
                Long.parseLong(atts.getProperty("sos", "0")),        //
                min,        //
                max,        //
                0);        // <- todo
        }
    }

    /**
     * Method endElement
     *
     * @param qName .
     *
     * @throws OutputViewerException
     */
    public void endElement(String qName) throws OutputViewerException
    {
        if ("call".equals(qName))
        {
            currentNode.completeStats();

            currentNode = (StackTreeNode) currentNode.getParent();
        }
    }

    /**
     * Method endDocumentCreateViewer
     *
     * @return .
     *
     * @throws OutputViewerException
     */
    public Component endDocumentCreateViewer() throws OutputViewerException
    {
        LOG.info("endDocumentCreateViewer");

        return new TreeViewerPanel(root);
    }

    /**
     * Method startDocument
     *
     * @throws OutputViewerException
     */
    public void startDocument() throws OutputViewerException
    {
    }

    /**
     * Method textElement
     *
     * @param text .
     *
     * @throws OutputViewerException
     */
    public void textElement(String text) throws OutputViewerException
    {
    }
}
