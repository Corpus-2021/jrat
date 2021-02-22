package org.shiftone.jrat.util.log;



import java.io.PrintStream;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;


/**
 * Class SimpleLog
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.8 $
 */
public class SimpleLog implements Log
{

    private static final Log    LOG           = LogFactory.getLogger(SimpleLog.class);
    private static final String DATE_FORMAT   = " MM.dd HH:mm:ss,SSS - ";
    private static final int    CURRENT_LEVEL = Integer.getInteger("jrat.log.level", LEVEL_INFO).intValue();
    private static PrintStream  OUT           = System.out;
    private DateFormat          dateFormat    = new SimpleDateFormat(DATE_FORMAT);
    private String              className     = null;

    /**
     * Constructor SimpleLog
     *
     *
     * @param className
     */
    SimpleLog(String className)
    {
        this.className = className;
    }

    /**
     * Method setPrintStream
     */
    public static void setPrintStream(PrintStream out)
    {

        synchronized (OUT)
        {
            OUT.flush();

            OUT = out;
        }
    }

    /**
     * Method close
     */
    public static void close()
    {

        LOG.info("shutting down log");

        synchronized (OUT)
        {
            OUT.flush();
            OUT.close();
        }
    }

    /**
     * Method log
     */
    private void log(int level, Object obj, Throwable t)
    {

        if (level >= CURRENT_LEVEL)
        {
            synchronized (OUT)
            {
                OUT.print(LEVEL_NAMES[level]);
                OUT.print(" ");
                OUT.print(Thread.currentThread().getName());
                OUT.print(dateFormat.format(new Date()));
                OUT.print(className);
                OUT.print(" - ");
                OUT.println(String.valueOf(obj));

                if (t != null)
                {
                    t.printStackTrace(OUT);
                }
            }
        }
    }

    /**
     * Method trace
     */
    public void trace(Object obj)
    {
        log(LEVEL_TRACE, obj, null);
    }

    /**
     * Method debug
     */
    public void debug(Object obj)
    {
        log(LEVEL_DEBUG, obj, null);
    }

    /**
     * Method debug
     */
    public void debug(Object obj, Throwable t)
    {
        log(LEVEL_DEBUG, obj, t);
    }

    /**
     * Method info
     */
    public void info(Object obj)
    {
        log(LEVEL_INFO, obj, null);
    }

    /**
     * Method info
     */
    public void info(Object obj, Throwable t)
    {
        log(LEVEL_INFO, obj, t);
    }

    /**
     * Method warn
     */
    public void warn(Object obj)
    {
        log(LEVEL_WARN, obj, null);
    }

    /**
     * Method warn
     */
    public void warn(Object obj, Throwable t)
    {
        log(LEVEL_WARN, obj, t);
    }

    /**
     * Method error
     */
    public void error(Object obj)
    {
        log(LEVEL_ERROR, obj, null);
    }

    /**
     * Method error
     */
    public void error(Object obj, Throwable t)
    {
        log(LEVEL_ERROR, obj, t);
    }

    /**
     * Method fatal
     */
    public void fatal(Object obj)
    {
        log(LEVEL_FATAL, obj, null);
    }

    /**
     * Method fatal
     */
    public void fatal(Object obj, Throwable t)
    {
        log(LEVEL_FATAL, obj, t);
    }
}
