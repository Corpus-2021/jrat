package org.shiftone.jrat.ui.util;



import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.Frame;


/**
 * Class WaitDialog
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.8 $
 */
public class WaitDialog extends JDialog
{

    private static final Log LOG     = LogFactory.getLogger(WaitDialog.class);
    private JLabel           label   = new JLabel("Working..");
    private int              counter = 0;

    /**
     * Constructor WaitDialog
     *
     *
     * @param frame
     */
    public WaitDialog(Frame frame)
    {

        super(frame, false);

        setUndecorated(true);
        setResizable(false);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        //
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBorder(BorderFactory.createRaisedBevelBorder());

        //
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(label, BorderLayout.CENTER);
        setDimension(frame);
        startAnimation();
    }

    /**
     * Method trySetUndecorated
     */
    public void setUndecorated(boolean value)
    {

        try
        {
            super.setUndecorated(value);
        }
        catch (NoSuchMethodError e)
        {

            // oh well, somebody is using java 1.3
        }
    }

    /**
     * Method setDimension
     */
    private void setDimension(Frame frame)
    {

        int width  = 200;
        int height = 50;
        int x      = (int) ((frame.getWidth() / 2) - (width / 2));
        int y      = (int) ((frame.getHeight() / 2) - (height / 2));

        setSize(width, height);
        setLocation(frame.getX() + x, frame.getY() + y);
    }

    /**
     * Method animate
     */
    boolean animate()
    {

        counter++;

        int    sleep;
        String message;

        if (counter % 2 == 0)
        {
            message = "Please wait...";
            sleep   = 500;
        }
        else
        {
            message = "";
            sleep   = 300;
        }

        label.setText(message);

        try
        {
            Thread.sleep(sleep);
        }
        catch (Exception e) {}

        return isVisible();
    }

    /**
     * Method startAnimation
     */
    private void startAnimation()
    {

        Runnable r = new Runnable()
        {

            public void run()
            {
                while (animate());
            }
        };

        new Thread(r).start();
    }
}
