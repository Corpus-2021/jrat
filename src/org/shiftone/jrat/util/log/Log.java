package org.shiftone.jrat.util.log;

/**
 * Interface Log
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.5 $
 */
public interface Log
{
    int      LEVEL_TRACE = 0;
    int      LEVEL_DEBUG = 1;
    int      LEVEL_INFO  = 2;
    int      LEVEL_WARN  = 3;
    int      LEVEL_ERROR = 4;
    int      LEVEL_FATAL = 5;
    String[] LEVEL_NAMES = { "[trace]", "[debug]", "[info ]", "[warn ]", "[ERROR]", "[FATAL]" };

    /**
     * Method trace
     */
    void trace(Object obj);

    /**
     * Method debug
     */
    void debug(Object obj);

    /**
     * Method debug
     */
    void debug(Object obj, Throwable t);

    /**
     * Method info
     */
    void info(Object obj);

    /**
     * Method info
     */
    void info(Object obj, Throwable t);

    /**
     * Method warn
     */
    void warn(Object obj);

    /**
     * Method warn
     */
    void warn(Object obj, Throwable t);

    /**
     * Method error
     */
    void error(Object obj);

    /**
     * Method error
     */
    void error(Object obj, Throwable t);

    /**
     * Method fatal
     */
    void fatal(Object obj);

    /**
     * Method fatal
     */
    void fatal(Object obj, Throwable t);
}
