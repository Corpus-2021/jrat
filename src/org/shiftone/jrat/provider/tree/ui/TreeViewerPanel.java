package org.shiftone.jrat.provider.tree.ui;

import org.shiftone.jrat.ui.util.PercentTableCellRenderer;
import org.shiftone.jrat.util.Percent;
import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.tree.TreePath;


/**
 * Class RateViewerPanel
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.1 $
 */
public class TreeViewerPanel extends JPanel implements TreeSelectionListener
{
    private static final Log   LOG                 = LogFactory.getLogger(TreeViewerPanel.class);
    private static final int[] TABLE_COLUMN_WIDTHS = { 250, 150, 100, 50, 50, 50 };
    private JSplitPane  splitPane      = null;
    private JScrollPane treeSrollPane  = null;
    private JTree       tree           = null;
    private JScrollPane tableSrollPane = null;
    private JTable      table          = null;

    /**
     * Constructor RateViewerPanel
     *
     * @param nodeModel
     */
    public TreeViewerPanel(StackTreeNode nodeModel)
    {
        tree               = new JTree(nodeModel);
        tableSrollPane     = new JScrollPane(tree);
        table              = new JTable();
        treeSrollPane      = new JScrollPane(table);
        splitPane          = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

        splitPane.setDividerLocation(0.75);
        splitPane.setResizeWeight(0.75);
        splitPane.setOneTouchExpandable(true);
        splitPane.add(treeSrollPane, JSplitPane.BOTTOM);
        splitPane.add(tableSrollPane, JSplitPane.TOP);

        //
        setLayout(new BorderLayout());
        add(splitPane, BorderLayout.CENTER);

        //
        tree.addTreeSelectionListener(this);
        tree.setCellRenderer(new StackTreeCellRenderer());

        //DefaultTableCellRenderer
        table.setModel(new StackTableModel());
        table.setDefaultRenderer(Percent.class, new PercentTableCellRenderer());
        table.setDefaultRenderer(Long.class, new PercentTableCellRenderer());
        table.setDefaultRenderer(Double.class, new PercentTableCellRenderer());
        table.setDefaultRenderer(Float.class, new PercentTableCellRenderer());
    }

    /**
     * Method valueChanged
     *
     * @param e .
     */
    public void valueChanged(TreeSelectionEvent e)
    {
        TreePath treePath = e.getNewLeadSelectionPath();

        if (treePath != null)
        {
            StackTreeNode nodeModel  = (StackTreeNode) treePath.getLastPathComponent();
            TableModel    tableModel = new StackTableModel(nodeModel);

            table.setModel(tableModel);

            TableColumnModel columnModel = table.getColumnModel();

            for (int i = 0; i < TABLE_COLUMN_WIDTHS.length; i++)
            {
                TableColumn column = columnModel.getColumn(i);

                column.setPreferredWidth(TABLE_COLUMN_WIDTHS[i]);
            }
        }
    }
}
