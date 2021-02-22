package org.shiftone.jrat.core;

import org.shiftone.jrat.core.spi.MethodHandler;

import org.shiftone.jrat.util.SignatureUtil;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;


/**
 * Is the main entry point for all JRat "hoods".
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.26 $
 */
public class HandlerFactory
{
    private static final Log       LOG             = LogFactory.getLogger(HandlerFactory.class);
    private static InternalHandler internalHandler = null;

    /**
     * method
     *
     * @return .
     */
    private static final synchronized InternalHandler getInternalHandler()
    {
        if (internalHandler == null)
        {
            internalHandler = new InternalHandler();
        }

        return internalHandler;
    }

    /**
     * A main runtime entry point.
     *
     * @param methodKey .
     *
     * @return .
     */
    public static final synchronized MethodHandler getMethodHandler(MethodKey methodKey)
    {
        return getInternalHandler().getMethodHandler(methodKey);
    }

    /**
     * A main runtime entry point.
     *
     * @param className
     * @param methodName
     * @param signature
     *
     * @return .
     */
    public static final synchronized MethodHandler getMethodHandler(String className, String methodName, String signature)
    {
        return getMethodHandler(new MethodKey(className, methodName, signature));
    }

    /**
     * A main runtime entry point.
     *
     * @param method
     *
     * @return .
     */
    public static final MethodHandler getMethodHandler(Method method)
    {
		return getInternalHandler().getMethodHandler(method);		
    }

    /**
     * method
     *
     * @param constructor .
     *
     * @return .
     */
    public static final MethodHandler getMethodHandler(Constructor constructor)
    {
		return getInternalHandler().getMethodHandler(constructor);
    }

    /**
     * A main runtime entry point.
     *
     * @param className
     * @param method
     *
     * @return .
     */
    public static final MethodHandler getMethodHandler(String className, Method method)
    {
    	
        String methodName = method.getName();
        String signature = SignatureUtil.getSignature(method);

        return getMethodHandler(className, methodName, signature);
    }
}
