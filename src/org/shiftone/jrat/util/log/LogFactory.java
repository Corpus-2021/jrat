package org.shiftone.jrat.util.log;



/**
 * Class LogFactory
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.2 $
 */
public class LogFactory
{

    /**
     * Method getLog
     */
    public static Log getLogger(Class klass)
    {
        return getLogger(klass.getName());
    }

    /**
     * Method getLog
     */
    public static Log getLogger(String className)
    {
        return new SimpleLog(className);
    }
}
