package org.shiftone.jrat.provider.chain;

import org.shiftone.jrat.core.ConfigurationException;

import org.shiftone.jrat.core.spi.MethodHandlerFactory;

import org.shiftone.jrat.util.BeanWrapper;
import org.shiftone.jrat.util.ResourceUtil;
import org.shiftone.jrat.util.XMLUtil;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;
import org.shiftone.jrat.util.log.LoggingSAXErrorHandler;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import org.xml.sax.helpers.DefaultHandler;

import java.io.InputStream;

import java.util.Stack;


/**
 * Class SAXConfigurator
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.11 $
 */
public class SAXConfigurator extends DefaultHandler {

    private static final Log    LOG              = LogFactory.getLogger(SAXConfigurator.class);
    private static final String ELEMENT_ROOT     = "jrat";
    private static final String ELEMENT_HANDLER  = "handler";
    private static final String ELEMENT_INCLUDE  = "include";
    private static final String ELEMENT_EXCLUDE  = "exclude";
    private static final String ELEMENT_FACTORY  = "factory";
    private static final String ELEMENT_PROPERTY = "property";

    //
    private Stack                     tagStack              = new Stack();
    private ChainMethodHandlerFactory chainFactory          = null;
    private FactoryMatcher            currentMatcher        = null;
    private MethodHandlerFactory      currentFactory        = null;
    private BeanWrapper               currentFactoryWrapper = null; // wraps currentMethodHandlerFactory

    /**
     * Constructor SAXConfigurator
     *
     * @param chainFactory
     */
    public SAXConfigurator(ChainMethodHandlerFactory chainFactory) {

        this.chainFactory = chainFactory;

    }

    /**
     * Method startHandler
     */
    private void startHandler() {

        currentMatcher = new FactoryMatcher();

    }

    /**
     * Method startFactory
     *
     * @param className .
     *
     * @throws ConfigurationException
     */
    private void startFactory(String className) throws ConfigurationException {

        if ((className == null) || (className.length() == 0)) {

            throw new ConfigurationException("<factory> has null or zero lenght 'class' attribute");

        }

        currentFactory            = (MethodHandlerFactory) ResourceUtil.newInstance(className);
        currentFactoryWrapper     = new BeanWrapper(currentFactory);

    }

    /**
     * Method addMatchCriteria
     *
     * @param include .
     * @param classNamePattern .
     * @param methodNamePattern .
     * @param signaturePattern .
     */
    private void addMatchCriteria(boolean include, String classNamePattern, String methodNamePattern, String signaturePattern) {

        MatchCriteria criteria = new MatchCriteria(classNamePattern, methodNamePattern, signaturePattern);

        if (include) {

            currentMatcher.addPositiveMatchCriteria(criteria);

        } else {

            currentMatcher.addNegativeMatchCriteria(criteria);

        }

    }

    /**
     * Method addProperty
     *
     * @param name .
     * @param value .
     *
     * @throws ConfigurationException
     */
    private void addProperty(String name, String value)
        throws ConfigurationException {

        if ((name == null) || (name.length() == 0)) {

            throw new ConfigurationException("<property> has null or zero lenght 'name' attribute");

        }

        currentFactoryWrapper.setProperty(name, value);

    }

    /**
     * Method endHandler
     */
    private void endHandler() {

        // add after done being configured
        chainFactory.addFactoryMatcher(currentMatcher);

        currentMatcher = null;

    }

    /**
     * Method endFactory
     */
    private void endFactory() {

        // add after done being configured
        currentMatcher.addFactory(currentFactory);

        currentFactory            = null;
        currentFactoryWrapper     = null;

    }

    /**
     * Method startElement
     *
     * @param uri .
     * @param localName .
     * @param qName .
     * @param attribs .
     *
     * @throws SAXException
     */
    public void startElement(String uri, String localName, String qName, Attributes attribs)
        throws SAXException {

        tagStack.push(qName);

        try {

            if (ELEMENT_HANDLER.equals(qName)) {

                startHandler();

            } else if (ELEMENT_FACTORY.equals(qName)) {

                startFactory(attribs.getValue("class"));

            } else if (ELEMENT_INCLUDE.equals(qName) || ELEMENT_EXCLUDE.equals(qName)) {

                addMatchCriteria(ELEMENT_INCLUDE.equals(qName), //
                    attribs.getValue("className"), //
                    attribs.getValue("methodName"), //
                    attribs.getValue("signature"));

            } else if (ELEMENT_PROPERTY.equals(qName)) {

                addProperty(attribs.getValue("name"), attribs.getValue("value"));

            }

        } catch (ConfigurationException e) {

            LOG.error("tag stack when error occured : " + tagStack);

            throw new SAXException("error processing XML configuration", e);

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
        throws SAXException {

        if (ELEMENT_HANDLER.equals(qName)) {

            endHandler();

        } else if (ELEMENT_FACTORY.equals(qName)) {

            endFactory();

        }

        tagStack.pop();

    }

    /**
     * Method buildFactory
     *
     * @param inputStream .
     *
     * @return .
     *
     * @throws ConfigurationException
     */
    public static MethodHandlerFactory buildFactory(InputStream inputStream)
        throws ConfigurationException {

        InputSource               inputSource   = null;
        MethodHandlerFactory      factory       = null;
        ChainMethodHandlerFactory chainFactory  = null;
        XMLReader                 xmlReader     = null;
        SAXConfigurator           configHandler = null;

        try {

            inputSource       = new InputSource(inputStream);
            xmlReader         = XMLUtil.getXMLReader();
            chainFactory      = new ChainMethodHandlerFactory();
            configHandler     = new SAXConfigurator(chainFactory);

            xmlReader.setContentHandler(configHandler);
            xmlReader.setErrorHandler(new LoggingSAXErrorHandler());
            xmlReader.parse(inputSource);

            if (chainFactory.getMatcherCount() == 0) {

                LOG.warn("the XML configuration did not create any handlers");

            } else {

                factory = chainFactory;

            }

        } catch (Exception e) {

            LOG.error("error building XML configured factory", e);

        }

        if (factory == null) {

            LOG.info("returning NULL handler");

        }

        return factory;

    }

}
