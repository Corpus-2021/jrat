package org.shiftone.jrat.test;

import junit.framework.TestCase;

import org.shiftone.jrat.util.XMLUtil;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import org.xml.sax.SAXException;


/**
 * Class XmlTestCase
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.6 $
 */
public class XmlTestCase extends TestCase {

    private static final Log LOG = LogFactory.getLogger(XmlTestCase.class);

    /**
     * Method testXMLReader
     *
     * @throws Exception
     */
    public void testXMLReader() throws Exception {

        XMLUtil.getXMLReader();

    }

    /**
     * Method testTest
     *
     * @throws Exception
     */
    public void testParserFactory() throws Exception {

        try {

            XMLUtil.getXMLReaderFromParserFactory();

        } catch (SAXException e) {

            LOG.warn("getXMLReaderFromParserFactory failed", e);

        }

    }

    /**
     * Method testReaderFactory
     *
     * @throws Exception
     */
    public void testReaderFactory() throws Exception {

        try {

            XMLUtil.getXMLReaderFromReaderFactory();

        } catch (SAXException e) {

            LOG.warn("getXMLReaderFromReaderFactory failed", e);

        }

    }

}
