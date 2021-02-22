package org.shiftone.jrat.core.spi;

import org.shiftone.jrat.core.OutputViewerException;

import org.shiftone.jrat.util.Assert;
import org.shiftone.jrat.util.XMLUtil;

import org.shiftone.jrat.util.log.LoggingSAXErrorHandler;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.awt.Component;
import java.io.InputStream;
import java.util.Properties;


/**
 * Interface AbstractOutputXmlViewerFactory
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.3 $
 */
public abstract class AbstractOutputXmlViewerFactory implements OutputViewerFactory
{
    /**
     * method
     *
     * @param runtimeOutput .
     *
     * @return .
     *
     * @throws OutputViewerException .
     */
    public Component createViewerComponent(RuntimeOutput runtimeOutput)
        throws OutputViewerException
    {
        Component        component        = null;
        InputStream      inputStream      = null;
        XMLReader        xmlReader        = null;
        ViewerXmlHandler viewerXmlHandler = null;

        try
        {
            inputStream          = runtimeOutput.openInputStream();
            viewerXmlHandler     = new ViewerXmlHandler(this);
            xmlReader            = XMLUtil.getXMLReader();

            xmlReader.setContentHandler(viewerXmlHandler);
            xmlReader.setErrorHandler(new LoggingSAXErrorHandler());
            xmlReader.parse(new InputSource(inputStream));

            component = viewerXmlHandler.getComponent();
            
			Assert.assertNotNull("component", component);
        }
        catch (Throwable e)
        {
            throw new OutputViewerException("error processing XML JRat output file", e);
        }

        return component;
    }

    /**
     * method
     *
     * @return .
     *
     * @throws OutputViewerException .
     */
    protected abstract Component endDocumentCreateViewer()
        throws OutputViewerException;

    /**
     * method
     *
     * @throws OutputViewerException .
     */
    protected abstract void startDocument() throws OutputViewerException;

    /**
     * method
     *
     * @param qName .
     *
     * @throws OutputViewerException .
     */
    protected abstract void endElement(String qName) throws OutputViewerException;

    /**
     * method
     *
     * @param qName .
     * @param atts .
     *
     * @throws OutputViewerException .
     */
    protected abstract void startElement(String qName, Properties atts)
        throws OutputViewerException;

    /**
     * method
     *
     * @param text .
     *
     * @throws OutputViewerException .
     */
    protected abstract void textElement(String text) throws OutputViewerException;
}
