package org.shiftone.jrat.ui.help;



import java.awt.event.ActionEvent;

import org.shiftone.jrat.ui.util.BackgroundActionListener;
import org.shiftone.jrat.ui.util.BrowserFrame;
import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;


/**
 * Class ShowDocsAction
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.3 $
 */
public class ShowDocsAction extends BackgroundActionListener
{

    private static final Log   LOG      = LogFactory.getLogger(ShowDocsAction.class);
    public static final String TITLE    = "JRat Online Documentation";
    public static final String HOME_URL = "http://jrat.sourceforge.net";

    /**
     * Method actionPerformed
     */
    public void actionPerformedInBackground(ActionEvent e)
    {

        BrowserFrame browserFrame = new BrowserFrame(HOME_URL, TITLE);

        browserFrame.setVisible(true);
    }
}
