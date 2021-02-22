package org.shiftone.jrat.jda.config;



import org.shiftone.jrat.jda.TraceException;


/**
 * Class TraceConfigException
 *
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 */
public class TraceConfigException extends TraceException
{

    /**
     * Constructor TraceConfigException
     *
     *
     * @param message
     */
    public TraceConfigException(String message)
    {
        super(message);
    }

    /**
     * Constructor TraceConfigException
     *
     *
     * @param message
     * @param rootCause
     */
    public TraceConfigException(String message, Throwable rootCause)
    {
        super(message, rootCause);
    }
}
