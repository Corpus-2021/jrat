package org.shiftone.jrat.core.spi;



import org.shiftone.jrat.core.MethodKey;


/**
 * Interface MethodHandlerFactory
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.8 $
 */
public interface MethodHandlerFactory
{

    /**
     * Method createMethodHandler
     * <li>this method on a handler will never be called more than once with the same methodKey
     * (caching need only be done at one layer)</li>
     * <li>if there is an error, or some reason not to create a handler - this method should log a
     * message and return null (this allows chain handler to not log to several silent handlers)</li>
     *
     */
    MethodHandler createMethodHandler(MethodKey methodKey);

    /**
     * It is recommended that handler factories that require shutdown notification implement
     * the ShutdownListener interface and call context.addShutdownListener(this) in their
     * startup method.
     *
     * @see ShutdownListener
     */
    void startup(RuntimeContext context) throws Exception;
}
