package org.shiftone.jrat.provider.chain;



import org.shiftone.jrat.core.spi.MethodHandler;


/**
 * Class ChainMethodHandler
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.7 $
 */
public class ChainMethodHandler implements MethodHandler
{

    private MethodHandler[] handlers = null;

    /**
     * Constructor ChainMethodHandler
     *
     *
     * @param methodHandlers
     */
    public ChainMethodHandler(MethodHandler[] methodHandlers)
    {
        this.handlers = methodHandlers;
    }

    /**
     * Method onMethodStart
     */
    public void onMethodStart(Object obj, Object[] params)
    {

        for (int i = 0; i < handlers.length; i++)
        {
            handlers[i].onMethodStart(obj, params);
        }
    }

    /**
     * Method onMethodFinish
     */
    public void onMethodFinish(Object obj, Object[] params, Object ret, long duration, boolean success)
    {

        for (int i = 0; i < handlers.length; i++)
        {
            handlers[i].onMethodFinish(obj, params, ret, duration, success);
        }
    }

    /**
     * Method onMethodError
     */
    public void onMethodError(Object obj, Object[] params, Throwable throwable)
    {

        for (int i = 0; i < handlers.length; i++)
        {
            handlers[i].onMethodError(obj, params, throwable);
        }
    }
    
    
	/**
	 * @return array of children for use by unit tests
	 */
	public MethodHandler[] getChildHandlers()
	{
		return handlers;
	}

}
