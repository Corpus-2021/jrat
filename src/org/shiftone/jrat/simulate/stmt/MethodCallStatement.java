package org.shiftone.jrat.simulate.stmt;

import org.shiftone.jrat.core.InternalHandler;
import org.shiftone.jrat.core.MethodKey;

import org.shiftone.jrat.core.spi.MethodHandler;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * Class SimMethodCall
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.1 $
 */
public class MethodCallStatement extends ListStatement
{
    private static final Log LOG = LogFactory.getLogger(MethodCallStatement.class);
    private static Object[]  ARGS = new Object[] {  };
    private MethodKey        methodKey = null;
    private String           klass = null;
    private String           method = null;
    private String           signature = null;
    private List             children = new ArrayList();

    /**
     * method
     *
     * @return .
     */
    private MethodKey getMethodKey()
    {
        if (methodKey == null)
        {
            methodKey = new MethodKey(klass, method, signature);
            LOG.info("new MethodKey " + methodKey);
        }

        return methodKey;
    }

    /**
     * method
     *
     * @param internalHandler .
     */
    public void execute(InternalHandler internalHandler)
    {
        MethodHandler handler;

        long          finish;
        long          start;
        boolean       success = false;

        start       = System.currentTimeMillis();
        handler     = internalHandler.getMethodHandler(getMethodKey());

        try
        {
            handler.onMethodStart(this, ARGS);

            super.execute(internalHandler);

            success = true;
        }
        catch (Throwable e)
        {
            handler.onMethodError(null, ARGS, e);
        }
        finally
        {
            finish = System.currentTimeMillis();
            handler.onMethodFinish(this, ARGS, null, (finish - start), success);
        }
    }

    /**
     * method
     *
     * @param string .
     */
    public void setClass(String string)
    {
        klass = string;
    }

    /**
     * method
     *
     * @param string .
     */
    public void setMethod(String string)
    {
        method = string;
    }

    /**
     * method
     *
     * @param string .
     */
    public void setSignature(String string)
    {
        signature = string;
    }
}
