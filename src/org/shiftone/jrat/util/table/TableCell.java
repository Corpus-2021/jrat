package org.shiftone.jrat.util.table;



import org.shiftone.jrat.util.StringUtil;
import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.text.DecimalFormat;
import java.text.Format;


/**
 * Class TableCell
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.4 $
 */
public class TableCell
{

    private static final Log    LOG            = LogFactory.getLogger(TableCell.class);
    private static final Format DECIMAL_FORMAT = new DecimalFormat("#########0.00");
    private static final Format INTEGER_FORMAT = new DecimalFormat("#,###,###,###,###,###");
    String                      value          = null;
    boolean                     leftJustify    = true;

    /**
     * Method TableCell
     *
     * @param obj
     */
    public TableCell(Object obj)
    {

        if (obj == null)
        {
            obj = "";
        }

        value       = toString(obj);
        leftJustify = (obj instanceof Number);
    }

    /**
     * Method toString
     */
    private static synchronized String toString(Object obj)
    {

        if (obj instanceof Number)
        {
            if ((obj instanceof Integer) || (obj instanceof Long))
            {
                return INTEGER_FORMAT.format(obj);
            }
            else
            {
                return DECIMAL_FORMAT.format(obj);
            }
        }
        else
        {
            return String.valueOf(obj);
        }
    }

    /**
     * Method getMinWidth
     */
    public int getMinWidth()
    {
        return value.length();
    }

    /**
     * Method toString
     */
    public String toString(int width)
    {

        return (leftJustify)
               ? StringUtil.rightPad(value, width)
               : StringUtil.leftPad(value, width);
    }
}
