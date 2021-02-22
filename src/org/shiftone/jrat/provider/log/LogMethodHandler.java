package org.shiftone.jrat.provider.log;

import org.shiftone.jrat.core.MethodKey;

import org.shiftone.jrat.core.spi.MethodHandler;
import org.shiftone.jrat.provider.log.io.*;

import java.io.IOException;


/**
 * Class LogMethodHandler
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.13 $
 */
public class LogMethodHandler implements MethodHandler {

    private MethodKey               methodKey = null;
    private LogMethodHandlerFactory factory = null;

    /**
     * Constructor LogMethodHandler
     *
     * @param methodKey
     * @param factory
     */
    public LogMethodHandler(MethodKey methodKey, LogMethodHandlerFactory factory) {

        this.methodKey     = methodKey;
        this.factory       = factory;

    }

    private final LogOutput getLogOutput() {

        return factory.getLogOutput();

    }

    /**
     * Method onMethodStart
     *
     * @param obj .
     * @param args .
     */
    public void onMethodStart(Object obj, Object[] args) {

        try {

            getLogOutput().writeMethodStart(methodKey);

        } catch (Exception e) {

        }

    }

    /**
     * Method onMethodFinish
     *
     * @param obj .
     * @param args .
     * @param ret .
     * @param duration .
     * @param success .
     */
    public void onMethodFinish(Object obj, Object[] args, Object ret,
        long duration, boolean success) {

        try {

            getLogOutput().writeMethodFinish(methodKey, duration, success);

        } catch (Exception e) {

        }

    }

    /**
     * Method onMethodError
     *
     * @param obj .
     * @param args .
     * @param e .
     */
    public void onMethodError(Object obj, Object[] args, Throwable e) {

        try {

            getLogOutput().writeMethodError(methodKey, e);

        } catch (IOException ex) {

        }

    }

}
