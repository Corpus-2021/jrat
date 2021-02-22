package org.shiftone.jrat.proxy;

import org.shiftone.jrat.core.HandlerFactory;

import org.shiftone.jrat.core.spi.MethodHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import java.util.Hashtable;
import java.util.Map;


/**
 * Class MethInvocationHandler
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.2 $
 */
public class MethInvocationHandler implements InvocationHandler {

    private static long handlers  = 0;
    private final long  instance;
    private boolean     infect    = true;
    private Object      target    = null;
    private Class       iface     = null;
    private String      className = null;

    /**
     * Constructor MethInvocationHandler
     *
     * @param target
     * @param iface
     * @param infect
     */
    public MethInvocationHandler(Object target, Class iface, boolean infect) {

        Class targetClass = target.getClass();

        this.infect     = infect;
        this.iface      = iface;
        this.target     = target;

        if (Proxy.isProxyClass(targetClass)) {

            className = iface.getName(); // log to iface rather than $Proxy

        } else {

            className = targetClass.getName(); // non-jdbc class

        }

        synchronized (MethInvocationHandler.class) {

            handlers++;

            instance = handlers;

        }

    }

    /**
     * Method returnsInterface
     *
     * @param method .
     *
     * @return .
     */
    private static final boolean returnsInterface(Method method) {

        return (method.getReturnType().isInterface());

    }

    /**
     * Method returnsVoid
     *
     * @param method .
     *
     * @return .
     */
    private static final boolean returnsVoid(Method method) {

        return (method.getReturnType().equals(Void.TYPE));

    }

    /**
     * Method invoke
     *
     * @param proxy .
     * @param method .
     * @param args .
     *
     * @return .
     *
     * @throws Throwable
     */
    public Object invoke(Object proxy, Method method, Object[] args)
        throws Throwable {

        MethodHandler methodHandler = null;
        Object        result  = null;
        long          start   = 0;
        boolean       success = false;

        methodHandler = HandlerFactory.getMethodHandler(className, method);

        try {

            methodHandler.onMethodStart(target, args);

            start       = System.currentTimeMillis();
            result      = doInvoke(proxy, method, args);
            success     = true;

            return result;

        } catch (Throwable throwable) {

            methodHandler.onMethodError(target, args, throwable);

            throw throwable;

        } finally {

            methodHandler.onMethodFinish(target, args, result, System.currentTimeMillis() - start, success);

        }

    }

    /**
     * handle invocations of the jdbc object, and forward them to the target object, logging along the way.
     *
     * @param proxy .
     * @param method .
     * @param args .
     *
     * @return .
     *
     * @throws Throwable
     */
    public Object doInvoke(Object proxy, Method method, Object[] args)
        throws Throwable {

        Object    result    = null;
        Throwable toRethrow = null;

        try {

            result = method.invoke(target, args);

        } catch (InvocationTargetException e) {

            toRethrow = e.getTargetException();

            if (toRethrow == null) // unlikely
             {

                toRethrow = e;

            }

        } catch (Throwable e) {

            toRethrow = e;

        }

        if (toRethrow != null) {

            throw toRethrow;

        }

        // -------------------------------------------------------------
        // the result is not null, and the method that was called returns
        // interface type, then wrap the returned object in a proxy also
        if ((result != null) && (infect)) {

            if (returnsInterface(method)) {

                result = MethInvocationHandler.safeGetTracedProxy(result, method.getReturnType());

            }

        }

        return result;

    }

    /**
     * Method safeGetTracedProxy
     *
     * @param target .
     * @param iface .
     *
     * @return .
     */
    public static Object safeGetTracedProxy(Object target, Class iface) {

        Object proxy = target;

        try {

            proxy = getTracedProxy(target, iface);

        } catch (Exception e) {

            //     LOG.error("error creating jdbc", e);
        }

        return proxy;

    }

    /**
     * Method getTracedProxy
     *
     * @param target .
     * @param iface .
     *
     * @return .
     *
     * @throws NullPointerException .
     * @throws IllegalArgumentException .
     */
    public static Object getTracedProxy(Object target, Class iface) {

        if (target == null) {

            throw new NullPointerException("target Object is null");

        }

        if (iface == null) {

            throw new NullPointerException("iface Class is null");

        }

        if (!iface.isInterface()) {

            throw new IllegalArgumentException("iface Class is not an interface");

        }

        if (!iface.isAssignableFrom(target.getClass())) {

            throw new IllegalArgumentException("target does not implement supplied interface : " + iface.getName());

        }

        InvocationHandler handler     = null;
        Object            proxy       = null;
        ClassLoader       classLoader = null;

        classLoader     = target.getClass().getClassLoader();
        handler         = new MethInvocationHandler(target, iface, true);
        proxy           = Proxy.newProxyInstance(classLoader, new Class[] { iface }, handler);

        return proxy;

    }

    /**
     * Method main
     *
     * @param args .
     */
    public static void main(String[] args) {

        Map   m  = new Hashtable();
        Map   m2 = (Map) MethInvocationHandler.getTracedProxy(m, Map.class);
        Class mc = Map.class;

        for (int i = 0; i < 500000; i++) {

            m.put("n=" + i, "tste");

        }

        m2.containsValue("novalue");
        m2.put(null, null);

        if (mc.isAssignableFrom(m.getClass())) {

            System.out.println("Map.class isAssignableFrom HashMap.class");

        }

        if (m.getClass().isAssignableFrom(mc)) {

            System.out.println("HashMap.class isAssignableFrom Map.class");

        }

        m2.put("test", "123");

        Object z = new String[5];

        System.out.println(z.getClass().getName());

        z = new Double(1);

        System.out.println(z.getClass().getName());

    }

}
