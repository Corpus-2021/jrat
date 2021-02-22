package org.shiftone.jrat.util.table;



import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;


/**
 * Class ArrayTableRow
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.4 $
 */
public class ArrayTableRow implements TableRow
{

    private static final Log LOG   = LogFactory.getLogger(ArrayTableRow.class);
    Object[]                 array = null;

    /**
     * Constructor ArrayTableRow
     *
     *
     * @param array
     */
    public ArrayTableRow(Object[] array)
    {
        this.array = array;
    }

    /**
     * Method getValueAt
     */
    public Object getValueAt(int index)
    {
        return array[index];
    }

    /**
     * Method getValueCount
     */
    public int getValueCount()
    {
        return array.length;
    }
}
