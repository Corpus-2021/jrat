package org.shiftone.jrat.provider.stats;



import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.util.Comparator;


/**
 * Class StatComparator
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.6 $
 */
public class StatComparator implements Comparator
{

    private static final Log       LOG      = LogFactory.getLogger(StatComparator.class);
    public static final Comparator INSTANCE = new StatComparator();

    /**
     * Method compare
     */
    public int compare(Object a, Object b)
    {

        StatMethodHandler handlerA = (StatMethodHandler) a;
        StatMethodHandler handlerB = (StatMethodHandler) b;
        int               ret;

        ret = convert(handlerA.getTotalDuration() - handlerB.getTotalDuration());

        if (ret == 0)
        {
            ret = convert(handlerA.getTotalExits() - handlerB.getTotalExits());
        }

        return ret;
    }

    /**
     * converts a long to the nearest int value
     */
    private static int convert(long input)
    {

        int result;

        if (input > Integer.MAX_VALUE)
        {
            result = Integer.MAX_VALUE;
        }
        else if (input < Integer.MIN_VALUE)
        {
            result = Integer.MIN_VALUE;
        }
        else
        {
            result = (int) input;
        }

        return result;
    }
}
