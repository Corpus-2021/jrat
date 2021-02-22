package org.shiftone.jrat.ui.action;



import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import javax.swing.JTabbedPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Class RemoveAllTabsAction
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.3 $
 */
public class RemoveAllTabsAction implements ActionListener
{

    private static final Log LOG        = LogFactory.getLogger(RemoveAllTabsAction.class);
    private JTabbedPane      tabbedPane = null;

    /**
     * Constructor RemoveAllTabsAction
     *
     *
     * @param tabbedPane
     */
    public RemoveAllTabsAction(JTabbedPane tabbedPane)
    {
        this.tabbedPane = tabbedPane;
    }

    /**
     * Method actionPerformed
     */
    public void actionPerformed(ActionEvent e)
    {
        tabbedPane.removeAll();
    }
}
