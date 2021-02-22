package org.shiftone.jrat.ui.util;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * The purpose of this ActionListener base class is to cause actions to be   processed on another thread.  The UI can often
 * freexe while a action is  being performed, even it is simply opening a dialig.  Showing a file   dialog may take more time
 * because dirs need to be scanned.
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.2 $
 */
public abstract class BackgroundActionListener implements ActionListener, Runnable
{
    private static final Log LOG = LogFactory.getLogger(BackgroundActionListener.class);
    private ActionEvent      actionEvent = null;

    /**
     * method
     *
     * @param e .
     */
    public final void actionPerformed(ActionEvent e)
    {
        if (actionEvent == null)
        {
            this.actionEvent = e;
            new Thread(this).start();
        }
        else
        {
            LOG.info("action in progress.. ignoring event : " + e);
        }
    }

    /**
     * method
     *
     * @param e .
     */
    protected abstract void actionPerformedInBackground(ActionEvent e);

    /**
     * method
     */
    public final void run()
    {
        try
        {
            actionPerformedInBackground(actionEvent);
        }
        catch (Exception e)
        {
            LOG.error("uncaught error : ", e);
        }
        finally
        {
            actionEvent = null;
        }
    }
}
