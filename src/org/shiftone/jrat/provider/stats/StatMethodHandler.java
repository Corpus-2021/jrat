package org.shiftone.jrat.provider.stats;



import org.shiftone.jrat.core.Accumulator;
import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.core.spi.MethodHandler;
import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;


/**
 * Class StatMethodHandler
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.7 $
 */
public class StatMethodHandler extends Accumulator implements MethodHandler
{

    private static final Log LOG       = LogFactory.getLogger(StatMethodHandler.class);
    private MethodKey        methodKey = null;

    /**
     * Constructor StatMethodHandler
     *
     * @param methodKey
     */
    public StatMethodHandler(MethodKey methodKey)
    {
        this.methodKey = methodKey;
    }

    /**
     * Method onMethodStart
     */
    public void onMethodStart(Object obj, Object[] params)
    {
        onMethodStart();
    }

    /**
     * Method onMethodFinish
     */
    public void onMethodFinish(Object obj, Object[] params, Object ret, long duration, boolean success)
    {
        onMethodFinish(duration, success);
    }

    /**
     * Method onMethodError
     */
    public void onMethodError(Object obj, Object[] params, Throwable throwable) {}

    /**
     * Method getClassName
     */
    public String getClassName()
    {
        return methodKey.getClassName();
    }

    /**
     * Method getMethodName
     */
    public String getMethodName()
    {
        return methodKey.getMethodName();
    }

    /**
     * Method getSignature
     */
    public String getSignature()
    {
        return methodKey.getSignature();
    }
}
