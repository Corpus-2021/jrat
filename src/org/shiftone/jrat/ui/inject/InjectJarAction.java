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
 * @version $Revision: 1.5 $
 */
public class InjectJarAction extends BackgroundActionListener implements UIConstants
{

    private static final Log LOG   = LogFactory.getLogger(InjectJarAction.class);
    private InjectionManager im    = null;
    private Frame            frame = null;

    /**
     * Constructor InjectJarAction
     *
     *
     * @param injectionManager
     * @param frame
     */
    public InjectJarAction(InjectionManager injectionManager, Frame frame)
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
            JFileChooser chooser  = new JFileChooser();
            File         lastFile = SETTINGS.getLastInjectedJar();
            File[]       targets  = null;

            chooser.setDialogTitle(CHOOSE_INJECT_JAR_TITLE);
            chooser.addChoosableFileFilter(INJECT_FILE_FILTER);
            chooser.setMultiSelectionEnabled(true);

            if (lastFile != null)
            {
                chooser.setCurrentDirectory(IOUtil.getNearestExistingParent(lastFile));
                chooser.setSelectedFile(lastFile);
            }

            if (JFileChooser.APPROVE_OPTION == chooser.showOpenDialog(null))
            {
                targets = chooser.getSelectedFiles();

                SETTINGS.setLastInjectedJar(targets[0]);
                Background.start(frame, new InjectRunnable(im, targets));
            }
        }
    }
}
