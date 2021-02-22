package org.shiftone.jrat.provider.record;

import java.io.IOException;

import org.shiftone.jrat.core.MethodKey;

import org.shiftone.jrat.core.spi.MethodHandler;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;


/**
 * Class RecordMethodHandler
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.4 $
 */
public class RecordMethodHandler implements MethodHandler {

    private static final Log LOG       = LogFactory.getLogger(RecordMethodHandler.class);
    private RecordOutput     output    = null;
    private MethodKey        methodKey = null;
    private int              methodId  = 0;

    /**
     * Constructor for RecordMethodHandler
     *
     * @param methodKey .
     * @param output .
     */
    public RecordMethodHandler(MethodKey methodKey, RecordOutput output) throws IOException {

        this.output        = output;
        this.methodKey     = methodKey;
        this.methodId      = output.writeMethodDef(methodKey);
    }

    /**
     * method
     *
     * @param obj .
     * @param params .
     * @param throwable .
     */
    public void onMethodError(Object obj, Object[] params, Throwable throwable) {

        try {

            output.writeMethodError(methodId, throwable);
        } catch (Exception e) {

        }
    }

    /**
     * method
     *
     * @param obj .
     * @param params .
     * @param ret .
     * @param duration .
     * @param success .
     */
    public void onMethodFinish(Object obj, Object[] params, Object ret,
        long duration, boolean success) {

        try {

            output.writeMethodFinish(methodId, duration, success);
        } catch (Exception e) {

        }
    }

    /**
     * method
     *
     * @param obj .
     * @param params .
     */
    public void onMethodStart(Object obj, Object[] params) {

        try {

            output.writeMethodStart(methodId);
        } catch (Exception e) {

        }
    }
}
