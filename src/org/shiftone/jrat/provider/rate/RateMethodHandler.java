package org.shiftone.jrat.provider.rate;



import org.shiftone.jrat.core.Accumulator;
import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.core.spi.MethodHandler;
import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;


/**
 * Class RateMethodHandler
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.1 $
 */
public class RateMethodHandler implements MethodHandler
{

    private static final Log LOG                = LogFactory.getLogger(RateMethodHandler.class);
    private MethodKey        methodKey          = null;
    private Accumulator      currentAccumulator = new Accumulator();

    /**
     * Constructor RateMethodHandler
     *
     *
     * @param methodKey
     */
    RateMethodHandler(MethodKey methodKey)
    {
        this.methodKey = methodKey;
    }

    /**
     * Method onMethodFinish
     */
    public synchronized void onMethodFinish(Object obj, Object[] params, Object ret, long duration, boolean success)
    {
        currentAccumulator.onMethodFinish(duration, success);
    }

    /**
     * Method onMethodStart
     */
    public synchronized void onMethodStart(Object obj, Object[] params)
    {
        currentAccumulator.onMethodStart();
    }

    /**
     * Method getAndReplaceAccumulator
     */
    public synchronized Accumulator getAndReplaceAccumulator()
    {

        Accumulator newAccumulator = new Accumulator();
        Accumulator oldAccumulator = currentAccumulator;

        currentAccumulator = newAccumulator;

        return oldAccumulator;
    }

    /**
     * Method getMethodKey
     */
    public MethodKey getMethodKey()
    {
        return methodKey;
    }

    /**
     * Method onMethodError
     */
    public void onMethodError(Object obj, Object[] params, Throwable throwable)
    {

        // ya ya, whatever
    }
}
