package org.shiftone.jrat.ui.viewer.tsv;



import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


/**
 * Class TsvMouseAdapter
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.1 $
 */
public class TsvMouseAdapter extends MouseAdapter
{

    private static final Log LOG           = LogFactory.getLogger(TsvMouseAdapter.class);
    private JTable           table         = null;
    private TsvTableModel    model         = null;
    private boolean[]        sortAscending = null;

    /**
     * Method TsvMouseAdapter
     *
     * @param table
     */
    public TsvMouseAdapter(JTable table)
    {

        this.table    = table;
        this.model    = (TsvTableModel) table.getModel();
        sortAscending = new boolean[this.model.getColumnCount()];
    }

    /**
     * Method mouseClicked
     */
    public void mouseClicked(MouseEvent e)
    {

        TableColumnModel columnModel = table.getColumnModel();
        int              viewColumn  = columnModel.getColumnIndexAtX(e.getX());
        int              column      = table.convertColumnIndexToModel(viewColumn);

        model.sortByColumn(column, sortAscending[column]);

        sortAscending[column] = !sortAscending[column];
    }
}
