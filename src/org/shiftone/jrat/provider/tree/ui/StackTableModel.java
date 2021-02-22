package org.shiftone.jrat.provider.tree.ui;

import org.shiftone.jrat.core.MethodKey;

import org.shiftone.jrat.util.Percent;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;


/**
 * Class StackTableModel
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.8 $
 */
public class StackTableModel implements TableModel {

    private static final Log LOG          = LogFactory.getLogger(StackTableModel.class);
    private StackTreeNode    node         = null;
    private List             stack        = null;
    private String[]         COLUMN_NAMES = {
        "Class", "Method", "Signature", //
        "Enters", "Exits", "Errors", "Total ms", //
        "Avg ms", "Std Dev", //
        "Min ms", "Max ms", //
        "%Parent", "%Root"
    };
    private Class[] COLUMN_TYPES = {
        String.class, String.class, String.class, //
        Long.class, Long.class, Long.class, Long.class, //
        Float.class, Double.class, //
        Long.class, Long.class, //
        Percent.class, Percent.class
    };

    /**
     * Constructor StackTableModel
     */
    public StackTableModel() {

        stack = new ArrayList();

    }

    /**
     * Constructor StackTableModel
     *
     * @param node
     */
    public StackTableModel(StackTreeNode node) {

        this.node     = node;
        stack         = new ArrayList();

        //
        StackTreeNode currNode = node;

        while (currNode.getParent() != null) {

            stack.add(currNode);

            currNode = (StackTreeNode) currNode.getParentNode();

        }

    }

    /**
     * Method getRowCount
     *
     * @return .
     */
    public int getRowCount() {

        return stack.size();

    }

    /**
     * Method getColumnCount
     *
     * @return .
     */
    public int getColumnCount() {

        return COLUMN_NAMES.length;

    }

    /**
     * Method getColumnName
     *
     * @param columnIndex .
     *
     * @return .
     */
    public String getColumnName(int columnIndex) {

        return COLUMN_NAMES[columnIndex];

    }

    /**
     * Method getColumnClass
     *
     * @param columnIndex .
     *
     * @return .
     */
    public Class getColumnClass(int columnIndex) {

        return COLUMN_TYPES[columnIndex];

    }

    /**
     * Method isCellEditable
     *
     * @param rowIndex .
     * @param columnIndex .
     *
     * @return .
     */
    public boolean isCellEditable(int rowIndex, int columnIndex) {

        return false;

    }

    /**
     * Method getValueAt
     *
     * @param rowIndex .
     * @param columnIndex .
     *
     * @return .
     */
    public Object getValueAt(int rowIndex, int columnIndex) {

        StackTreeNode node      = (StackTreeNode) stack.get(rowIndex);
        MethodKey     methodKey = node.getMethodKey();

        if (methodKey == null) {

            return "?";

        }

        switch (columnIndex) {

        case 0:
            return methodKey.getClassName();

        case 1:
            return methodKey.getMethodName();

        case 2:
            return methodKey.getSignature();

        case 3:
            return new Long(node.getTotalEnters());

        case 4:
            return new Long(node.getTotalExits());

        case 5:
            return new Long(node.getTotalErrors());

        case 6:
            return new Long(node.getTotalDuration());

        case 7:
            return node.getAverageDuration();

        case 8:
            return node.getStdDeviation();

        case 9:
            return node.getMinDuration();

        case 10:
            return node.getMaxDuration();

        case 11:
            return new Percent(node.getPctOfAvgParentDuration());

        case 12:
            return new Percent(node.getPctOfAvgRootDuration());

        }

        return null;

    }

    /**
     * Method setValueAt
     *
     * @param aValue .
     * @param rowIndex .
     * @param columnIndex .
     */
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

    }

    /**
     * Method addTableModelListener
     *
     * @param l .
     */
    public void addTableModelListener(TableModelListener l) {

    }

    /**
     * Method removeTableModelListener
     *
     * @param l .
     */
    public void removeTableModelListener(TableModelListener l) {

    }

}
