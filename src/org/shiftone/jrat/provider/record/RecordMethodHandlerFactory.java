package org.shiftone.jrat.provider.record;

import org.shiftone.jrat.core.MethodKey;

import org.shiftone.jrat.core.spi.AbstractMethodHandlerFactory;
import org.shiftone.jrat.core.spi.MethodHandler;
import org.shiftone.jrat.core.spi.RuntimeContext;

import org.shiftone.jrat.util.IOUtil;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.io.OutputStream;


/**
 * Class RecordMethodHandlerFactory
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.6 $
 */
public class RecordMethodHandlerFactory extends AbstractMethodHandlerFactory {

    private static final Log LOG    = LogFactory.getLogger(RecordMethodHandlerFactory.class);
    private RecordOutput     output = null;

    /**
     * method
     *
     * @param methodKey .
     *
     * @return .
     */
    public MethodHandler createMethodHandler(MethodKey methodKey) {

        MethodHandler handler = null;

        try {

            handler = new RecordMethodHandler(methodKey, output);

        } catch (Exception e) {

            LOG.error("error creating RecordMethodHandler", e);

        }

        return handler;

    }

    /**
     * method
     *
     * @param context .
     */
    public void startup(RuntimeContext context) throws Exception {

        super.startup(context);

        OutputStream outputStream = null;

        try {

            outputStream     = context.newOutputStream(getOutputFile() +
                    ".jrat", isCompressOutput());
            output = new RecordOutput(outputStream);

            context.addShutdownListener(this);

        } catch (Exception e) {

            IOUtil.close(output);

        }

    }

    /**
     * method
     */
    public void shutdown() {

        IOUtil.close(output);

    }

}
