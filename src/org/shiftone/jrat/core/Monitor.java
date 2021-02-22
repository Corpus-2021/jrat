package org.shiftone.jrat.core;

import org.shiftone.jrat.core.spi.MethodHandler;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;


/**
 * A programmer's interface to create JRat events.  It is based on Steve Souza's JAMon API. While this class provices a necessary
 * function of making it simpler to add event generation to applicaton code, it's use is vunerable to programming mistakes.  Use
 * with care.
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.8 $
 *
 * @see <a href="http://jamonapi.com/">jamonapi.com</a>
 */
public class Monitor {

    private static final Log LOG       = LogFactory.getLogger(Monitor.class);
    private MethodKey        methodKey = null;
    private MethodHandler    handler   = null;
    private boolean          stopped   = false;
    private boolean          success   = true; // not crazy about assuming success
    private Object           instance  = null;
    private long             startTime = 0;

    /**
     * Constructor Monitor
     *
     * @param className
     * @param methodName
     * @param signature
     * @param instance
     */
    private Monitor(String className, String methodName, String signature, Object instance) {

        this.methodKey     = new MethodKey(className, methodName, signature);
        this.handler       = HandlerFactory.getMethodHandler(methodKey);
        this.instance      = instance;

    }

    private static Monitor doStart(Object instance, String methodName, String signature) {

        String className = null;

        if (instance != null) {

            className = instance.getClass().getName();

        }

        return new Monitor(className, methodName, signature, instance).start();

    }

    /**
     * Method start
     *
     * @param instance .
     * @param methodName .
     * @param signature .
     *
     * @return .
     */
    public static Monitor start(Object instance, String methodName, String signature) {

        return doStart(instance, methodName, signature);

    }

    /**
     * Method start
     *
     * @param instance .
     * @param methodName .
     *
     * @return .
     */
    public static Monitor start(Object instance, String methodName) {

        return doStart(instance, methodName, "(?)");

    }

    /**
     * Method start
     *
     * @param args .
     *
     * @return .
     */
    private Monitor start() {

        handler.onMethodStart(instance, null);

        startTime = System.currentTimeMillis();

        return this;

    }

    /**
     * Method error
     *
     * @param args .
     * @param throwable .
     */
    public void error(Object[] args, Throwable throwable) {

        handler.onMethodError(instance, args, throwable);

        success = false;

    }

    /**
     * Method error
     *
     * @param throwable .
     */
    public void error(Throwable throwable) {

        error(null, throwable);

    }

    /**
     * Method stop
     *
     * @param args .
     * @param ret .
     */
    public void stop() {

        if (stopped) {

            throw new RuntimeException("stop() called more than once on monitor for : " + methodKey);

        }

        stopped = true;

        handler.onMethodFinish(instance, null, null, System.currentTimeMillis() - startTime, success);

    }

    /**
     * Method finalize
     *
     * @throws Throwable
     */
    protected void finalize() throws Throwable {

        final Throwable throwable = new MonitorNotStoppedException();

        if (!stopped) {

            error(throwable);
            stop();

        }

    }

    /**
     * Class MonitorNotStoppedException
     *
     * @author Jeff Drost
     */
    private class MonitorNotStoppedException extends JRatException {

        /**
         * Constructor MonitorNotStoppedException
         */
        private MonitorNotStoppedException() {
            super("finalize() method needed to stop() monitor");

        }

    }

}
