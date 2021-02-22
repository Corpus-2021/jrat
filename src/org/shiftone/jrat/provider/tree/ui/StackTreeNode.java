package org.shiftone.jrat.provider.tree.ui;

import org.shiftone.jrat.core.MethodKey;

import org.shiftone.jrat.provider.tree.StackNode;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;


/**
 * Class StackTreeNode
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.7 $
 */
public class StackTreeNode extends StackNode implements TreeNode
{
    private static final Log    LOG                    = LogFactory.getLogger(StackTreeNode.class);
    private static final Double ZERO                   = new Double(0);
    private boolean             isChildOfRoot          = false;
    private List                childList              = new ArrayList();
    protected double            averageDuration;
    protected double            pctOfAvgParentDuration;
    protected double            rootAverageDuration;
    protected double            rootTotalDuration;
    protected double            pctOfAvgRootDuration;
    protected double            pctOfRootTotalDuration;

    /**
     * Constructor StackTreeNode
     */
    public StackTreeNode()
    {
    }

    /**
     * Constructor StackTreeNode
     *
     * @param methodKey
     * @param parentStackTreeNode
     */
    public StackTreeNode(MethodKey methodKey, StackTreeNode parentStackTreeNode)
    {
        super(methodKey, parentStackTreeNode);
    }

    /**
     * Method setStatistics
     *
     * @param totalEnters .
     * @param totalExits .
     * @param totalErrors .
     * @param totalDuration .
     * @param totalOfSquares .
     * @param maxDuration .
     * @param minDuration .
     * @param maxConcurThreads .
     */
    public void setStatistics(
        long totalEnters, long totalExits, long totalErrors, long totalDuration, long totalOfSquares, long maxDuration,
        long minDuration, int maxConcurThreads)
    {
        super.setStatistics(
            totalEnters, totalExits, totalErrors, totalDuration,        //
            totalOfSquares, maxDuration, minDuration, maxConcurThreads);

        StackTreeNode p = (StackTreeNode) parent;

        this.isChildOfRoot = p.isRootNode();

        if (totalExits > 0)
        {
            averageDuration = (double) getTotalDuration() / (double) getTotalExits();

            if (parent != null)
            {
                long parentTotalDuration = p.getTotalDuration();

                if (parentTotalDuration > 0)
                {
                    pctOfAvgParentDuration = (100.0 * getTotalDuration()) / parentTotalDuration;
                }
            }
        }

        this.rootAverageDuration     = (isChildOfRoot) ? this.averageDuration : p.rootAverageDuration;

        //
        this.rootTotalDuration = (isChildOfRoot) ? getTotalDuration() : p.rootTotalDuration;

        if (rootAverageDuration > 0)
        {
            pctOfAvgRootDuration = (100.0 * averageDuration) / rootAverageDuration;
        }

        if (rootTotalDuration > 0)
        {
            pctOfRootTotalDuration = ((100.0 * getTotalDuration()) / rootTotalDuration);
        }
    }

    /**
     * Method completeStats
     */
    public void completeStats()
    {
    }

    /**
     * Method gets <b>AND CREATES IF NEEDED</b> the requested tree node.  This method does the <i>exact</i> same thing as that of
     * the parent class, <i>except</i> new child nodes are also added to a list, which is used to support the TreeNode
     * interface.
     *
     * @param methodKey .
     *
     * @return .
     */
    public StackNode getChild(MethodKey methodKey)
    {
        StackNode treeNode = null;

        synchronized (children)
        {
            treeNode = (StackNode) children.get(methodKey);

            if (treeNode == null)
            {
                treeNode = new StackTreeNode(methodKey, this);

                childList.add(treeNode);
                children.put(methodKey, treeNode);
            }
        }

        return treeNode;
    }

    // -------------------------------------------------------------

    /**
     * Method getAvgPctOfParent
     *
     * @return .
     */
    public double getPctOfAvgParentDuration()
    {
        return pctOfAvgParentDuration;
    }

    /**
     * Method getPctOfAvgRootDuration
     *
     * @return .
     */
    public double getPctOfAvgRootDuration()
    {
        return pctOfRootTotalDuration;

        //return pctOfAvgRootDuration;
    }

    /**
     * Method getRootAverageDuration
     *
     * @return .
     */
    public double getRootAverageDuration()
    {
        return rootAverageDuration;
    }

    /**
     * Method getPctOfRootTotalDuration
     *
     * @return .
     */
    public double getPctOfRootTotalDuration()
    {
        return pctOfRootTotalDuration;
    }

    /**
     * Method getRootTotalDuration
     *
     * @return .
     */
    public double getRootTotalDuration()
    {
        return rootTotalDuration;
    }

    /**
     * Method isChildOfRootNode
     *
     * @return .
     */
    public boolean isChildOfRootNode()
    {
        return isChildOfRoot;
    }

    // -------------------------------------------------------------

    /**
     * Method toString
     *
     * @return .
     */
    public String toString()
    {
        return (isRootNode()) ? "Root" : methodKey.getMethodName();
    }

    /**
     * Method children
     *
     * @return .
     */
    public Enumeration children()
    {
        return Collections.enumeration(childList);
    }

    /**
     * Method getAllowsChildren
     *
     * @return .
     */
    public boolean getAllowsChildren()
    {
        return true;
    }

    /**
     * Method getChildAt
     *
     * @param childIndex .
     *
     * @return .
     */
    public TreeNode getChildAt(int childIndex)
    {
        return (TreeNode) childList.get(childIndex);
    }

    /**
     * Method getChildCount
     *
     * @return .
     */
    public int getChildCount()
    {
        return childList.size();
    }

    /**
     * Method getParent
     *
     * @return .
     */
    public TreeNode getParent()
    {
        return (TreeNode) getParentNode();
    }

    /**
     * Method getIndex
     *
     * @param node .
     *
     * @return .
     */
    public int getIndex(TreeNode node)
    {
        return childList.indexOf(node);
    }

    /**
     * Method isLeaf
     *
     * @return .
     */
    public boolean isLeaf()
    {
        return (getChildCount() == 0);
    }
}
