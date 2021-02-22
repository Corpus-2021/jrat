package org.shiftone.jrat.ui;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import javax.swing.UIManager;


/**
 * Class Desktop
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.9 $
 */
public class Desktop {

    private static final Log LOG         = LogFactory.getLogger(Desktop.class);
    private DesktopFrame     viewerFrame = null;

    /**
     * Constructor Desktop
     */
    public Desktop() {

        viewerFrame = new DesktopFrame();

    }

    /**
     * Method begin
     */
    public void begin() {

        LOG.debug("begin");
        viewerFrame.setVisible(true);

    }

    /**
     * Method main
     *
     * @param args .
     */
    public static void main(String[] args) {

        try {

            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

            // UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            // UIManager.setLookAndFeel("javax.swing.plaf.basic.BasicLookAndFeel");
            // javax.swing.plaf.basic.BasicLookAndFeel
        } catch (Exception e) {

        }

        Desktop viewer = new Desktop();

        viewer.begin();

    }

}
