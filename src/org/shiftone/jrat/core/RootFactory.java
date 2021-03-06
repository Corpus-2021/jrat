package org.shiftone.jrat.core;

import org.shiftone.jrat.core.spi.MethodHandlerFactory;

import org.shiftone.jrat.provider.chain.ChainFactoryBuilder;

import org.shiftone.jrat.util.ResourceUtil;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;


/**
 * Factory creates the root level handler factory.
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.3 $
 */
public class RootFactory {

    private static final Log   LOG                    = LogFactory.getLogger(RootFactory.class);
    public static final String SYS_PROP_CONFIGURATION = "jrat.configuration";
    public static final String SYS_PROP_FACTORY       = "jrat.factory";

    /**
     * method
     *
     * @return .
     */
    public MethodHandlerFactory getHandlerFactory() {

        long                 start           = System.currentTimeMillis();
        MethodHandlerFactory factory         = null;
        String               jratFactoryName = System.getProperty(SYS_PROP_FACTORY);
        String               jratConfigName  = System.getProperty(SYS_PROP_CONFIGURATION);

        LOG.info("JRat creating root handler factory...");
        LOG.info(SYS_PROP_FACTORY + " = " + jratFactoryName);
        LOG.info(SYS_PROP_CONFIGURATION + " = " + jratConfigName);

        try {

            if (jratFactoryName != null) {

                LOG.debug(SYS_PROP_FACTORY + " = " + jratFactoryName);

                if (jratConfigName != null) {

                    LOG.warn("system property '" + SYS_PROP_CONFIGURATION + "' will be ignored, property '' takes precedence");

                }

                factory = (MethodHandlerFactory) ResourceUtil.newInstance(jratFactoryName);

            } else if (jratConfigName != null) {

                factory = ChainFactoryBuilder.buildFactory(jratConfigName);

            }

            LOG.info("JRat handler(s) created in " + (System.currentTimeMillis() - start) + "ms");

        } catch (Exception e) {

            LOG.error("error configuring JRat", e);

        }

        if (factory == null) {

            LOG.info("reverting to silent handler");

            return InternalHandler.DEFAULT_HANDLER_FACTORY;

        } else {

            return factory;

        }

    }

}
