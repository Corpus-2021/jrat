package org.shiftone.jrat.ui.action;



import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Class ExitAction
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.2 $
 */
public class ExitAction implements ActionListener
{

    private static final Log LOG = LogFactory.getLogger(ExitAction.class);

    /**
     * Method actionPerformed
     */
    public void actionPerformed(ActionEvent e)
    {
        System.exit(1);
    }
}
