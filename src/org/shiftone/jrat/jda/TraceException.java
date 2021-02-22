package org.shiftone.jrat.jda;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;


/**
 * Class TraceException
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 */
public class TraceException extends Exception {

    private static final Log LOG       = LogFactory.getLogger(TraceException.class);
    private Throwable        rootCause = null;

    /**
     * Constructor TraceException
     *
     * @param message
     */
    public TraceException(String message) {
        super(message);

    }

    /**
     * Constructor TraceException
     *
     * @param message
     * @param rootCause
     */
    public TraceException(String message, Throwable rootCause) {
        super(message);

        this.rootCause = rootCause;

    }

}
