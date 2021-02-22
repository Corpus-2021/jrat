package org.shiftone.jrat.ui.util;



import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.awt.Dialog;
import java.awt.Frame;


/**
 * Class Background
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.5 $
 */
public class Background implements Runnable
{

    private static final Log LOG         = LogFactory.getLogger(Background.class);
    private Runnable         runnable    = null;
    private Frame            parentFrame = null;
    private Dialog           waitDialog  = null;

    /**
     * Constructor Background
     *
     *
     * @param parentFrame
     * @param runnable
     * @param waitDialog
     */
    private Background(Frame parentFrame, Runnable runnable, Dialog waitDialog)
    {

        this.runnable    = runnable;
        this.parentFrame = parentFrame;
        this.waitDialog  = waitDialog;
    }

    /**
     * Method run
     */
    public void run()
    {

        parentFrame.setEnabled(false);

        try
        {
            LOG.info("calling run on : " + runnable);
            runnable.run();
            LOG.info("run done");
            waitDialog.setVisible(false);
            waitDialog.dispose();
        }
        catch (Throwable e)
        {
            LOG.warn("caught exception", e);
            waitDialog.setVisible(false);
            waitDialog.dispose();
            new ExceptionDialog(waitDialog, e).setVisible(true);
        }
        finally
        {
            LOG.info("run finally");
            parentFrame.setEnabled(true);
        }
    }

    /**
     * Method start
     */
    public static void start(Frame frame, Runnable runnable)
    {

        Dialog     waitDialog = new WaitDialog(frame);
        Background background = new Background(frame, runnable, waitDialog);
        Thread     thread     = new Thread(background);

        thread.start();
        waitDialog.setVisible(true);
    }
}
