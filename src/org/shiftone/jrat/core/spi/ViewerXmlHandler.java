package org.shiftone.jrat.core.spi;

import org.shiftone.jrat.core.OutputViewerException;

import org.shiftone.jrat.util.Assert;
import org.shiftone.jrat.util.ResourceUtil;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import org.xml.sax.helpers.DefaultHandler;

import java.awt.Component;
import java.util.Properties;


/**
 * Class MasterXmlViewerFactory
 *
 * @author $Author: jeffdrost $
 * @version $Revision: 1.4 $
 */
class ViewerXmlHandler extends DefaultHandler
{
    private static final Log               LOG              = LogFactory.getLogger(ViewerXmlHandler.class);
    private AbstractOutputXmlViewerFactory xmlViewerFactory = null;
    private Component                      component        = null;
    private int                            depth            = 0;
    private long                           startTimeMs      = 0;

    /**
     * Constructor for ViewerXmlHandler
     *
     * @param xmlViewerFactory .
     */
    public ViewerXmlHandler(AbstractOutputXmlViewerFactory xmlViewerFactory)
    {
		Assert.assertNotNull("AbstractOutputXmlViewerFactory", xmlViewerFactory);
        this.xmlViewerFactory = xmlViewerFactory;
    }

    /**
     * method
     *
     * @return .
     */
    public Component getComponent()
    {
        return component;
    }

    /**
     * Method characters
     *
     * @param ch .
     * @param start .
     * @param length .
     *
     * @throws SAXException
     */
    public void characters(char[] ch, int start, int length)
        throws SAXException
    {
        try
        {
            xmlViewerFactory.textElement(new String(ch, start, length));
        }
        catch (OutputViewerException e)
        {
            throw new SAXException("characters/textElement failed");
        }
    }

    /**
     * method
     *
     * @throws SAXException . 
     */
    public void startDocument() throws SAXException
    {
        startTimeMs = System.currentTimeMillis();
    }

    /**
     * Method endDocument
     *
     * @throws SAXException
     */
    public void endDocument() throws SAXException
    {
        try
        {
            LOG.info("parse took " + (System.currentTimeMillis() - startTimeMs) + "ms");
            component = xmlViewerFactory.endDocumentCreateViewer();
            LOG.info("got " + component + " from " + xmlViewerFactory);
        }
        catch (OutputViewerException e)
        {
            throw new SAXException("endDocument/endDocumentCreateViewer failed");
        }
    }

    /**
     * Method getOutputXMLViewerFactory
     *
     * @param props .
     *
     * @return .
     *
     * @throws SAXException
     */
    private AbstractOutputXmlViewerFactory getOutputXMLViewerFactory(Properties props)
        throws SAXException
    {
        AbstractOutputXmlViewerFactory factory     = null;
        String                         viewerClass = props.getProperty("viewer");
        Object                         object      = null;

        if (viewerClass == null)
        {
            throw new SAXException("attribute not correctly defined on root XML element");
        }

        try
        {
            object      = ResourceUtil.newInstance(viewerClass);
            factory     = (AbstractOutputXmlViewerFactory) object;
        }
        catch (Exception e)
        {
            throw new SAXException("error creating instance of XML viewer factory", e);
        }

        return factory;
    }

    /**
     * Method startElement
     *
     * @param uri .
     * @param localName .
     * @param qName .
     * @param attributes .
     *
     * @throws SAXException
     */
    public void startElement(String uri, String localName, String qName, Attributes attributes)
        throws SAXException
    {
        Properties props = new Properties();

        depth++;

        for (int i = 0; i < attributes.getLength(); i++)
        {
            props.put(attributes.getQName(i), attributes.getValue(i));
        }

        try
        {
            if (depth == 1)
            {
                xmlViewerFactory = getOutputXMLViewerFactory(props);

                xmlViewerFactory.startDocument();
            }

            xmlViewerFactory.startElement(qName, props);
        }
        catch (OutputViewerException e)
        {
            throw new SAXException(e);
        }
    }

    /**
     * Method endElement
     *
     * @param uri .
     * @param localName .
     * @param qName .
     *
     * @throws SAXException
     */
    public void endElement(String uri, String localName, String qName)
        throws SAXException
    {
        try
        {
            xmlViewerFactory.endElement(qName);

            depth--;
        }
        catch (OutputViewerException e)
        {
            throw new SAXException(e);
        }
    }
}
