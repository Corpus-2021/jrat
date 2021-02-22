package org.shiftone.jrat.ui.help;



import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.util.Properties;

import javax.swing.JOptionPane;

import org.shiftone.jrat.core.ConfigurationException;
import org.shiftone.jrat.ui.UIConstants;
import org.shiftone.jrat.ui.util.BackgroundActionListener;
import org.shiftone.jrat.util.ResourceUtil;
import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;


/**
 * Class AboutAction
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.4 $
 */
public class AboutAction extends BackgroundActionListener implements UIConstants
{

    private static final Log LOG     = LogFactory.getLogger(AboutAction.class);
    private Frame            parent  = null;
    private String           message = null;

    /**
     * Constructor AboutAction
     *
     *
     * @param parent
     */
    public AboutAction(Frame parent)
    {

        Properties   properties = null;
        StringBuffer sb         = new StringBuffer(UI_TITLE);

        try
        {
            properties = ResourceUtil.getResourceAsProperties("jrat-version.properties");
        }
        catch (ConfigurationException ex)
        {
            LOG.info("unable to load version properties", ex);

            properties = new Properties();
        }

        String version = properties.getProperty("version");
        String buildOn = properties.getProperty("built_on");
        String builtBy = properties.getProperty("built_by");

        if (version != null)
        {
            sb.append(" v" + version);
        }

        sb.append("\nBy Jeff Drost, Released under the LGPL\n");

        if ((buildOn != null) && (builtBy != null))
        {
            sb.append("Built On " + buildOn + " by " + builtBy + "\n\n");
        }

        sb.append(WEBSITE + "\n");
        sb.append(EMAIL + "\n");

        message     = sb.toString();
        this.parent = parent;
    }

    /**
     * Method actionPerformed
     */
    public void actionPerformedInBackground(ActionEvent e)
    {

        JOptionPane.showMessageDialog(parent, message, ABOUT_TITLE, JOptionPane.INFORMATION_MESSAGE);

        //JOptionPane.showMessageDialog();
        //JDialog dialog = new ExceptionDialog(parent, "error", "message", new IOException());
        //dialog.setVisible(true);
    }
}
