package org.shiftone.jrat.util.log;


import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;


/**
 * Class LoggingSAXErrorHandler
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.1 $
 */
public class LoggingSAXErrorHandler implements ErrorHandler
{
    private static final Log LOG = LogFactory.getLogger(LoggingSAXErrorHandler.class);

    /**
     * method
     *
     * @param e .
     *
     * @throws SAXException .
     */
    public void error(SAXParseException e) throws SAXException
    {
        LOG.warn("parse error " + message(e));
    }

    /**
     * method
     *
     * @param e .
     *
     * @throws SAXException .
     */
    public void fatalError(SAXParseException e) throws SAXException
    {
        LOG.warn("fatal parse error " + message(e));
    }

    /**
     * method
     *
     * @param e .
     *
     * @throws SAXException .
     */
    public void warning(SAXParseException e) throws SAXException
    {
        LOG.warn("parse warning " + message(e));
    }

    /**
     * method
     *
     * @param e .
     *
     * @return .
     */
    private String message(SAXParseException e)
    {
        return "on line " + e.getLineNumber() + ", column " + e.getColumnNumber() + " : " + e.getMessage();
    }
}
