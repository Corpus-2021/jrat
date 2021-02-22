package org.shiftone.jrat.ui.inject;



import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;

import org.shiftone.jrat.ui.UIConstants;
import org.shiftone.jrat.ui.util.Background;
import org.shiftone.jrat.ui.util.BackgroundActionListener;
import org.shiftone.jrat.util.IOUtil;
import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;


/**
 * Class InjectJarAction
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.4 $
 */
public class InjectDirAction extends BackgroundActionListener implements UIConstants
{

    private static final Log LOG   = LogFactory.getLogger(InjectDirAction.class);
    private Frame            frame = null;
    private InjectionManager im    = null;

    /**
     * Constructor InjectJarAction
     *
     *
     * @param injectionManager
     * @param frame
     */
    public InjectDirAction(InjectionManager injectionManager, Frame frame)
    {
        this.im    = injectionManager;
        this.frame = frame;
    }

    /**
     * Method actionPerformed
     */
    public void actionPerformedInBackground(ActionEvent e)
    {

        if (im.canProceed())
        {
            JFileChooser chooser = new JFileChooser();
            File         lastDir = SETTINGS.getLastInjectedDir();
            File[]       target  = null;

            chooser.setDialogTitle(CHOOSE_INJECT_DIR_TITLE);
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setMultiSelectionEnabled(true);

            if (lastDir != null)
            {
                chooser.setCurrentDirectory(IOUtil.getNearestExistingParent(lastDir));
                chooser.setSelectedFile(lastDir);
            }

            if (JFileChooser.APPROVE_OPTION == chooser.showOpenDialog(null))
            {
                target = chooser.getSelectedFiles();

                SETTINGS.setLastInjectedDir(target[0]);
                Background.start(frame, new InjectRunnable(im, target));
            }
        }
    }
}
