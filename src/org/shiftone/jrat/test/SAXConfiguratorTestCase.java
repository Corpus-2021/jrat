package org.shiftone.jrat.test;

import junit.framework.TestCase;

import org.shiftone.jrat.core.ConfigurationException;
import org.shiftone.jrat.core.MethodKey;

import org.shiftone.jrat.core.spi.MethodHandler;
import org.shiftone.jrat.core.spi.MethodHandlerFactory;

import org.shiftone.jrat.provider.chain.ChainMethodHandler;
import org.shiftone.jrat.provider.chain.ChainMethodHandlerFactory;
import org.shiftone.jrat.provider.chain.FactoryMatcher;
import org.shiftone.jrat.provider.chain.SAXConfigurator;

import org.shiftone.jrat.provider.silent.SilentMethodHandler;

import org.shiftone.jrat.util.ResourceUtil;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;


/**
 * Class SAXConfiguratorTestCase
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.5 $
 */
public class SAXConfiguratorTestCase extends TestCase {

    private static final Log LOG               = LogFactory.getLogger(SAXConfiguratorTestCase.class);
    private static String    EXAMPLE_BASE_PATH = "test/";
    private ClassLoader      loader            = getClass().getClassLoader();

    /**
     * .
     *
     * @param name .
     *
     * @return .
     *
     * @throws ConfigurationException when resource not found
     */
    private InputStream getExampleInputStream(String name)
        throws ConfigurationException {

        return ResourceUtil.loadResourceAsStream(EXAMPLE_BASE_PATH + name);

    }

    public void testBadXmlConfig() throws Exception {

        InputStream          inputStream    = null;
        MethodHandlerFactory handlerFactory = null;

        inputStream        = new ByteArrayInputStream("bad xml config".getBytes());
        handlerFactory     = SAXConfigurator.buildFactory(inputStream);

    }

    public void testGoodXmlBadConfig() throws Exception {

        InputStream          inputStream    = null;
        MethodHandlerFactory handlerFactory = null;
        MethodHandler        methodHandler  = null;

        inputStream        = new ByteArrayInputStream("<bad-config/>".getBytes());
        handlerFactory     = SAXConfigurator.buildFactory(inputStream);

    }

    /**
     * .
     *
     * @throws Exception if test failed
     */
    public void testOne() throws Exception {

        InputStream               inputStream    = null;
        MethodHandlerFactory      handlerFactory = null;
        MethodHandler[]           methodHandlers = null;
        ChainMethodHandlerFactory chainFactory   = null;
        ChainMethodHandler        chainHandler   = null;
        FactoryMatcher[]          matchers       = null;
        MethodHandler             methodHandler  = null;

        inputStream        = getExampleInputStream("test1.xml");
        handlerFactory     = SAXConfigurator.buildFactory(inputStream);

        assertTrue(handlerFactory instanceof ChainMethodHandlerFactory);

        chainFactory     = (ChainMethodHandlerFactory) handlerFactory;
        matchers         = chainFactory.getFactoryMatchers();

        assertEquals(matchers.length, 2);

        methodHandler = chainFactory.createMethodHandler(new MethodKey("a.c.b", "getX", "()I"));
        assertEquals(SilentMethodHandler.class, methodHandler.getClass());

        methodHandler = chainFactory.createMethodHandler(new MethodKey("a.c.b", "doIt", "()V"));
        assertEquals(ChainMethodHandler.class, methodHandler.getClass());

        chainHandler       = (ChainMethodHandler) methodHandler;
        methodHandlers     = chainHandler.getChildHandlers();

        assertEquals(2, methodHandlers.length);

    }

}
