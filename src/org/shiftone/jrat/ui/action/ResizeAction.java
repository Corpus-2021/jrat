package org.shiftone.jrat.ui.action;



import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Class ResizeAction
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.1 $
 */
public class ResizeAction implements ActionListener
{

    private static final Log LOG = LogFactory.getLogger(ResizeAction.class);
    private Frame            frame;
    private int              width;
    private int              height;

    /**
     * Constructor ResizeAction
     *
     *
     * @param frame
     * @param width
     * @param height
     */
    public ResizeAction(Frame frame, int width, int height)
    {

        this.frame  = frame;
        this.width  = width;
        this.height = height;
    }

    /**
     * Method actionPerformed
     */
    public void actionPerformed(ActionEvent e)
    {
        frame.setSize(width, height);
        frame.validate();
    }
}
