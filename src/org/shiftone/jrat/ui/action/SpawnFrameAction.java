package org.shiftone.jrat.ui.action;



import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Class SpawnFrameAction
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.3 $
 */
public class SpawnFrameAction implements ActionListener
{

    private static final Log LOG        = LogFactory.getLogger(SpawnFrameAction.class);
    private JTabbedPane      tabbedPane = null;

    /**
     * Constructor RemoveCurrentTabAction
     *
     *
     * @param tabbedPane
     */
    public SpawnFrameAction(JTabbedPane tabbedPane)
    {
        this.tabbedPane = tabbedPane;
    }

    /**
     * Method actionPerformed
     */
    public void actionPerformed(ActionEvent e)
    {

        int       selectedIndex = tabbedPane.getSelectedIndex();
        Component component     = null;
        Dimension size          = null;
        String    title         = null;
        JFrame    frame         = null;

        if (selectedIndex != -1)
        {
            frame     = new JFrame();
            title     = tabbedPane.getTitleAt(selectedIndex);
            component = tabbedPane.getComponentAt(selectedIndex);
            size      = component.getSize();

            tabbedPane.getComponentAt(selectedIndex);
            tabbedPane.remove(selectedIndex);
            frame.setTitle(title);
            frame.getContentPane().add(component);
            frame.setSize(size);
            frame.setVisible(true);
        }
    }
}
