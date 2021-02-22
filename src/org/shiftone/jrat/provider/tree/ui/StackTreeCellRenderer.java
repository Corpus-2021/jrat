package org.shiftone.jrat.provider.tree.ui;

import org.shiftone.jrat.ui.util.DotIcon;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.awt.Color;
import java.awt.Component;
import java.text.DecimalFormat;

import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;


/**
 * Class StackTreeCellRenderer
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.6 $
 */
public class StackTreeCellRenderer extends DefaultTreeCellRenderer implements TreeCellRenderer
{
    private static final Log LOG = LogFactory.getLogger(StackTreeCellRenderer.class);
    private static Icon      ICON_ROOT = new DotIcon(8, Color.lightGray);
    private static Icon      ICON_COLD = new DotIcon(10, new Color(0x00ff00));
    private static Icon      ICON_COOL = new DotIcon(10, new Color(0x88ff00));
    private static Icon      ICON_WARM = new DotIcon(10, new Color(0xffff00));
    private static Icon      ICON_WARMER = new DotIcon(10, new Color(0xff8800));
    private static Icon      ICON_HOT = new DotIcon(10, new Color(0xff0000));
    private StackTreeNode    treeNode = null;
    private DecimalFormat    pctDecimalFormat = new DecimalFormat("#,###.#'%'");
    private DecimalFormat    msDecimalFormat = new DecimalFormat("#,###,###.##'ms'");

    /**
     * method
     *
     * @param tree .
     * @param value .
     * @param sel .
     * @param expanded .
     * @param leaf .
     * @param row .
     * @param hasFocus .
     *
     * @return .
     */
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus)
    {
        this.treeNode     = (StackTreeNode) value;
        this.hasFocus     = hasFocus;
        this.selected     = sel;

        double w = treeNode.getPctOfAvgParentDuration();
        Icon   icon = null;

        if (treeNode.isRootNode() || treeNode.isChildOfRootNode())
        {
            icon = ICON_ROOT;
        }
        else if (w >= 70)
        {
            icon = ICON_HOT;
        }
        else if (w >= 50)
        {
            icon = ICON_WARMER;
        }
        else if (w >= 30)
        {
            icon = ICON_WARM;
        }
        else if (w >= 10)
        {
            icon = ICON_COOL;
        }
        else
        {
            icon = ICON_COLD;
        }

        setText(nodeText());

        if (selected)
        {
            setForeground(Color.white);
        }
        else
        {
            setForeground(Color.black);
        }

        if (!tree.isEnabled())
        {
            setEnabled(false);
            setDisabledIcon(icon);
        }
        else
        {
            setEnabled(true);
            setIcon(icon);
        }

        setComponentOrientation(tree.getComponentOrientation());

        return this;
    }

    /**
     * Method convertValueToText
     *
     * @return .
     */
    public String nodeText()
    {
        if (treeNode.isRootNode())
        {
            return "Root";
        }
        else
        {
            String methodName = treeNode.getMethodKey().getMethodName();

            if (treeNode.isChildOfRootNode())
            {
                Float avg = treeNode.getAverageDuration();

                return methodName + ((avg == null) ? " - never exited" : (" - " + msDecimalFormat.format(treeNode.getAverageDuration())));
            }
            else
            {
                double pct = treeNode.getPctOfAvgRootDuration();

                if (pct > 0.09)
                {
                    return methodName + " - " + pctDecimalFormat.format(pct);
                }
                else
                {
                    return methodName;
                }
            }
        }
    }
}
