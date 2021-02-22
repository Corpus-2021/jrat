package org.shiftone.jrat.core.spi;



/**
 * Interface MethodHandler
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.3 $
 */
public interface MethodHandler
{

    /**
     * Method onMethodStart
     */
    void onMethodStart(Object obj, Object[] params);

    /**
     * Method onMethodFinish
     */
    void onMethodFinish(Object obj, Object[] params, Object ret, long duration, boolean success);

    /**
     * Method onMethodError
     */
    void onMethodError(Object obj, Object[] params, Throwable throwable);
}
