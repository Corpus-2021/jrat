package org.shiftone.jrat.provider.log;

import org.shiftone.jrat.core.MethodKey;

import org.shiftone.jrat.core.spi.AbstractMethodHandlerFactory;
import org.shiftone.jrat.core.spi.MethodHandler;
import org.shiftone.jrat.core.spi.RuntimeContext;

import org.shiftone.jrat.provider.log.io.*;

import org.shiftone.jrat.util.Sequence;
import org.shiftone.jrat.util.StringUtil;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.io.OutputStream;


/**
 * Class LogMethodHandlerFactory
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.21 $
 */
public class LogMethodHandlerFactory extends AbstractMethodHandlerFactory {

    private static final Log   LOG                      = LogFactory.getLogger(LogMethodHandlerFactory.class);
    public static final String DEFAULT_OUTPUT_FILE_NAME = "Log";
    private Sequence           threadSeq                = null;
    private Sequence           messageSeq               = null;
    private ThreadLocal        threadLocal              = null;
    private RuntimeContext     context                  = null;

    /**
     * Constructor for LogMethodHandlerFactory
     */
    public LogMethodHandlerFactory() {

        threadLocal     = new ThreadLocal();
        threadSeq       = new Sequence();
        messageSeq      = new Sequence();

    }

    /**
     * method
     *
     * @param methodKey .
     *
     * @return .
     */
    public MethodHandler createMethodHandler(MethodKey methodKey) {

        return new LogMethodHandler(methodKey, this);

    }

    private static String threadName(Thread thread) {

        return StringUtil.hex(thread.hashCode()) + //
        "-" + //
        StringUtil.removeNonLetterOrDigit(thread.getThreadGroup().getName()) + //
        "-" + //
        StringUtil.removeNonLetterOrDigit(thread.getName());

    }

    /**
     * method
     *
     * @return .
     */
    private LogOutput newLogOutput() {

        LogOutput    logOutput    = null;
        OutputStream outputStream = null;
        String       threadName   = threadName(Thread.currentThread());

        int          threadId = (int) threadSeq.getNextValue();

        try {

            outputStream     = context.newOutputStream(getOutputFile() + "-" + threadName + ".jrat", isCompressOutput());
            logOutput        = new LogOutput(outputStream, messageSeq);

            context.addShutdownListener(logOutput);

        } catch (Exception e) {

            //logOutput.close();
        }

        return logOutput;

    }

    public synchronized LogOutput getLogOutput() {

        LogOutput logOutput = (LogOutput) threadLocal.get();

        if (logOutput == null) {

            logOutput = newLogOutput();

            threadLocal.set(logOutput);

        }

        return logOutput;

    }

    public String getDefaultOutputFile() {

        return DEFAULT_OUTPUT_FILE_NAME;

    }

    /**
     * method
     *
     * @param context .
     */
    public void startup(RuntimeContext context) {

        this.context = context;

        context.addShutdownListener(this);

    }

    public String toString() {

        return "Log Handler Factory";

    }

}
