package org.shiftone.jrat.provider.silent;



import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.core.spi.MethodHandler;
import org.shiftone.jrat.core.spi.MethodHandlerFactory;
import org.shiftone.jrat.core.spi.RuntimeContext;


/**
 * Class SilentMethodHandler
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.11 $
 */
public class SilentMethodHandler implements MethodHandler, MethodHandlerFactory
{

    private static final SilentMethodHandler INSTANCE        = new SilentMethodHandler();
    public static final MethodHandlerFactory HANDLER_FACTORY = INSTANCE;
    public static final MethodHandler        METHOD_HANDLER  = INSTANCE;

    /**
     * Method onMethodError
     *
     * @param obj
     * @param args
     * @param throwable
     */
    public void onMethodError(Object obj, Object[] args, Throwable throwable) {}

    /**
     * Method onMethodFinish
     *
     * @param obj
     * @param args
     * @param ret
     * @param duration
     * @param success
     */
    public void onMethodFinish(Object obj, Object[] args, Object ret, long duration, boolean success) {}

    /**
     * Method onMethodStart
     *
     * @param obj
     * @param args
     */
    public void onMethodStart(Object obj, Object[] args) {}

    /**
     * Method createMethodHandler
     */
    public MethodHandler createMethodHandler(MethodKey methodKey)
    {
        return this;
    }

    /**
     * Method startup
     */
    public void startup(RuntimeContext context) {}

    /**
     * Method shutdown
     */
    public void shutdown() {}
}
