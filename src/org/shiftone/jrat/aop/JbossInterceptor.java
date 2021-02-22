package org.shiftone.jrat.aop;



import org.jboss.aop.ConstructorInvocation;
import org.jboss.aop.Interceptor;
import org.jboss.aop.Invocation;
import org.jboss.aop.InvocationType;
import org.jboss.aop.MethodInvocation;

import org.shiftone.jrat.core.HandlerFactory;
import org.shiftone.jrat.core.spi.MethodHandler;
import org.shiftone.jrat.provider.silent.SilentMethodHandler;
import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;


/**
 * Class JbossInterceptor
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.5 $
 */
public class JbossInterceptor implements Interceptor {

    private static final Log LOG = LogFactory.getLogger(JbossInterceptor.class);

    private final Object execute(Invocation inv, Object[] params, MethodHandler handler) throws Throwable {

        long    startTime = 0;
        boolean success   = false;
        Object  result    = null;

        try {
            handler.onMethodStart(inv.targetObject, params);

            startTime = System.currentTimeMillis();
            result    = inv.invokeNext();
            success   = true;
        } catch(Throwable t) {
            handler.onMethodError(inv.targetObject, params, t);

            throw t;
        } finally {
            handler.onMethodFinish(inv.targetObject, params, result, System.currentTimeMillis() - startTime, success);
        }

        return result;
    }

    /**
     * method getName
     *
     * @return "JRat JBossAOP Hook"
     */
    public String getName() {
        return "JRat JBossAOP Hook";
    }

    /**
     * method invoke
     *
     * @return .
     */
    public Object invoke(Invocation inv) throws Throwable {

        MethodHandler handler = SilentMethodHandler.METHOD_HANDLER;
        Object[]      params  = null;

        if(inv.getType() == InvocationType.METHOD) {
            MethodInvocation mi = (MethodInvocation) inv;

            params  = mi.arguments;
            handler = HandlerFactory.getMethodHandler(mi.method);

            return execute(inv, params, handler);
        } else if(inv.getType() == InvocationType.CONSTRUCTOR) {
            ConstructorInvocation ci = (ConstructorInvocation) inv;

            params  = ci.arguments;
            handler = HandlerFactory.getMethodHandler(ci.constructor);

            return execute(inv, params, handler);
        } else {

            // Do nothing for fields
            return inv.invokeNext();
        }
    }
}
