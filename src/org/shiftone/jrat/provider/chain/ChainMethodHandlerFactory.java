package org.shiftone.jrat.provider.chain;

import org.shiftone.jrat.core.MethodKey;

import org.shiftone.jrat.core.spi.MethodHandler;
import org.shiftone.jrat.core.spi.MethodHandlerFactory;
import org.shiftone.jrat.core.spi.RuntimeContext;
import org.shiftone.jrat.core.spi.ShutdownListener;

import org.shiftone.jrat.provider.silent.SilentMethodHandler;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * Class ChainMethodHandlerFactory
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.14 $
 */
public class ChainMethodHandlerFactory implements MethodHandlerFactory, ShutdownListener {

    private static final Log LOG             = LogFactory.getLogger(ChainMethodHandlerFactory.class);
    private List             factoryMatchers = new ArrayList();

    /**
     * Method addFactoryMatcher
     *
     * @param factoryMatcher .
     */
    public void addFactoryMatcher(FactoryMatcher factoryMatcher) {

        factoryMatchers.add(factoryMatcher);

    }

    /**
     * Method createMethodHandler
     *
     * @param methodKey .
     *
     * @return .
     */
    public MethodHandler createMethodHandler(MethodKey methodKey) {

        MethodHandlerFactory factory          = null;
        FactoryMatcher       factoryMatcher   = null;
        MethodHandler        methodHandler    = null;
        List                 matchedFactories = new ArrayList();

        for (int i = 0; i < factoryMatchers.size(); i++) {

            factoryMatcher = (FactoryMatcher) factoryMatchers.get(i);

            if (factoryMatcher.matches(methodKey)) {

                matchedFactories.addAll(factoryMatcher.getFactories());

            }

        }

        if (matchedFactories.size() == 0) {

            methodHandler = SilentMethodHandler.METHOD_HANDLER;

            LOG.debug("no handlers matched " + methodKey);

        } else if (matchedFactories.size() == 1) {

            factory           = (MethodHandlerFactory) matchedFactories.get(0);
            methodHandler     = factory.createMethodHandler(methodKey);

            LOG.debug("one handler matched " + matchedFactories);

        } else {

            // I expect that an array is slightly faster at runtime than an ArrayList
            List            handlerList = new ArrayList(matchedFactories.size());
            MethodHandler[] handlers = null;

            for (int h = 0; h < matchedFactories.size(); h++) {

                factory           = (MethodHandlerFactory) matchedFactories.get(h);
                methodHandler     = factory.createMethodHandler(methodKey);

                if (methodHandler != null) {

                    // this is why I don't know the size of the array and need to use an
                    // arraylist.  The factory can return null
                    handlerList.add(methodHandler);

                }

            }

            handlers = new MethodHandler[handlerList.size()];

            for (int h = 0; h < handlers.length; h++) {

                handlers[h] = (MethodHandler) handlerList.get(h);

            }

            methodHandler = new ChainMethodHandler(handlers);

            LOG.debug(matchedFactories.size() + " handlers matched " + matchedFactories);

        }

        return methodHandler;

    }

    /**
     * Method startup
     *
     * @param context .
     */
    public void startup(RuntimeContext context) throws Exception {

        FactoryMatcher       factoryMatcher = null;
        MethodHandlerFactory factory   = null;
        List                 factories = null;

        context.addShutdownListener(this);

        for (int fm = 0; fm < factoryMatchers.size(); fm++) {

            factoryMatcher     = (FactoryMatcher) factoryMatchers.get(fm);
            factories          = factoryMatcher.getFactories();

            for (int i = 0; i < factories.size(); i++) {

                factory = (MethodHandlerFactory) factories.get(i);
			
                factory.startup(context);
			
            }
        }

    }

    /**
     * Method shutdown goes through each matcher's factories and shuts them down.
     */
    public void shutdown() {

        LOG.info("shutdown()");

        /*
           FactoryMatcher       factoryMatcher = null;
           List                 factories      = null;
           MethodHandlerFactory factory        = null;
           for (int i = 0; i < factoryMatchers.size(); i++)
           {
               factoryMatcher = (FactoryMatcher) factoryMatchers.get(i);
               factories      = factoryMatcher.getFactories();
               for (int f = 0; f < factories.size(); f++)
               {
                   factory = (MethodHandlerFactory) factories.get(f);
                   try
                   {
                       LOG.debug("shutdown : " + factory);
                       factory.shutdown();
                   }
                   catch (Exception e)
                   {
                       LOG.error("Error shutting down : " + factory.getClass().getName(), e);
                   }
               }
           }
         */
    }

    /**
     * array of matchers that the chain handler will search for  appropriate handlers
     *
     * @return the current list of matchers
     */
    public FactoryMatcher[] getFactoryMatchers() {

        FactoryMatcher[] fm = new FactoryMatcher[factoryMatchers.size()];

        for (int i = 0; i < fm.length; i++) {

            fm[i] = (FactoryMatcher) factoryMatchers.get(i);

        }

        return fm;

    }

    /**
     * count of delegate matchers
     *
     * @return matcher count
     */
    public int getMatcherCount() {

        return factoryMatchers.size();

    }

    public String toString() {

        return "Chain Handler Factory";

    }

}
