package org.shiftone.jrat.provider.tree;



import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.core.spi.MethodHandler;


/**
 * Class TreeMethodHandler
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.6 $
 */
public class TreeMethodHandler implements MethodHandler
{

    // private static final Log               LOG = LogFactory.getLogger(TreeMethodHandler.class);
    private final TreeMethodHandlerFactory factory;
    private final MethodKey                methodKey;

    /**
     * Constructor TreeMethodHandler
     *
     *
     * @param factory
     * @param methodKey
     */
    public TreeMethodHandler(TreeMethodHandlerFactory factory, MethodKey methodKey)
    {

        if (factory == null)
        {
            throw new NullPointerException("factory is null");
        }

        this.factory   = factory;
        this.methodKey = methodKey;
    }

    /**
     * Method onMethodStart
     */
    public void onMethodStart(Object obj, Object[] params)
    {

        Delegate delegate = factory.getDelegate();

        delegate.onMethodStart(methodKey);
    }

    /**
     * Method onMethodFinish
     */
    public void onMethodFinish(Object obj, Object[] params, Object ret, long duration, boolean success)
    {

        Delegate delegate = factory.getDelegate();

        delegate.onMethodFinish(methodKey, duration, success);
    }

    /**
     * Method onMethodError
     */
    public void onMethodError(Object obj, Object[] params, Throwable throwable)
    {

        // do nothing
    }
}
