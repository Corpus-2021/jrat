package org.shiftone.jrat.util.table;



/**
 * Interface TableRow
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.3 $
 */
public interface TableRow
{

    /**
     * Method getValueCount
     */
    int getValueCount();

    /**
     * Method getValueAt
     */
    Object getValueAt(int index);
}
