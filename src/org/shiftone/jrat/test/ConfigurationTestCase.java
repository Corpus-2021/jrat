package org.shiftone.jrat.test;

import junit.framework.TestCase;

import org.shiftone.jrat.core.HandlerFactory;
import org.shiftone.jrat.core.RootFactory;

import org.shiftone.jrat.core.spi.MethodHandler;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;


/**
 * Class ConfigurationTestCase
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.13 $
 */
public class ConfigurationTestCase extends TestCase {

    private static final Log LOG = LogFactory.getLogger(ConfigurationTestCase.class);

    /**
     * Method testGetHandlerFactory
     */
    public void testGetHandlerFactory() {

        System.setProperty(RootFactory.SYS_PROP_CONFIGURATION, "jrat.xml");

        MethodHandler handler1 = HandlerFactory.getMethodHandler("com.test.Class", "getIt", "(I)I");
        MethodHandler handler2 = HandlerFactory.getMethodHandler("com.test.Class", "doIt", "(I)I");

    }

}
