package org.shiftone.jrat.ui.viewer;

import org.shiftone.jrat.core.OutputViewerException;

import org.shiftone.jrat.core.spi.OutputViewerFactory;
import org.shiftone.jrat.core.spi.RuntimeOutput;

import org.shiftone.jrat.ui.UIConstants;

import org.shiftone.jrat.ui.util.Background;
import org.shiftone.jrat.ui.util.BackgroundActionListener;
import org.shiftone.jrat.ui.util.ExceptionDialog;

import org.shiftone.jrat.util.IOUtil;
import org.shiftone.jrat.util.ResourceUtil;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.awt.Frame;
import java.awt.event.ActionEvent;

import java.io.File;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;

import javax.swing.JFileChooser;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;


/**
 * Class OpenOutputFileAction
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.10 $
 */
public class OpenOutputFileAction extends BackgroundActionListener implements UIConstants {

    private static final Log   LOG           = LogFactory.getLogger(OpenOutputFileAction.class);
    public static final String VIEWER_STRING = "viewer=\"";
    private Frame              parent        = null;
    private JTabbedPane        tabbedPane    = null;
    private JProgressBar       progressBar   = null;
    private ActionEvent        actionEvent   = null;

    /**
     * Constructor OpenOutputFileAction
     *
     * @param parent
     * @param tabbedPane
     * @param progressBar
     */
    public OpenOutputFileAction(Frame parent, JTabbedPane tabbedPane, JProgressBar progressBar) {

        this.parent          = parent;
        this.tabbedPane      = tabbedPane;
        this.progressBar     = progressBar;

    }

    /**
     * Method actionPerformed
     *
     * @param e .
     */
    public void actionPerformedInBackground(ActionEvent e) {

        JFileChooser chooser  = new JFileChooser();
        File         lastFile = SETTINGS.getLastOpenedOutputFile();

        try {

            chooser.addChoosableFileFilter(OUTPUT_FILE_FILTER);

            if (lastFile != null) {

                chooser.setCurrentDirectory(IOUtil.getNearestExistingParent(lastFile));
                chooser.setSelectedFile(lastFile);

            }

            if (JFileChooser.APPROVE_OPTION == chooser.showOpenDialog(parent)) {

                openFile(chooser.getSelectedFile());

            }

        } catch (Exception ex) {

            new ExceptionDialog(parent, ex).setVisible(true);

        }

    }

    /**
     * Method openFile
     *
     * @param inputFile .
     *
     * @throws IOException
     * @throws OutputViewerException
     */
    private void openFile(File inputFile) throws IOException, OutputViewerException {

        LOG.info("openFile(" + inputFile + ")");
        SETTINGS.setLastOpenedOutputFile(inputFile);

        RuntimeOutputImpl   runtimeOutput = null;
        String              title         = inputFile.getName();
        OutputViewerFactory viewerFactory = null;
        Runnable            runnable      = null;

        runtimeOutput     = new RuntimeOutputImpl(inputFile, progressBar);
        viewerFactory     = getOutputViewerFactory(runtimeOutput);
        runnable          = new OpenOutputFileRunnable(runtimeOutput, viewerFactory, title, tabbedPane);

        // start the open output file runnable on a different thread
        Background.start(parent, runnable);

    }

    /**
     * method
     *
     * @param runtimeOutput .
     *
     * @return .
     *
     * @throws OutputViewerException .
     */
    private OutputViewerFactory getOutputViewerFactory(RuntimeOutput runtimeOutput)
        throws OutputViewerException {

        OutputViewerFactory factory   = null;
        String              klassName = null;

        try {

            klassName     = getOutputViewerFactoryClassName(runtimeOutput);
            factory       = (OutputViewerFactory) ResourceUtil.newInstance(klassName);

        } catch (Exception e) {

            throw new OutputViewerException("error creating output viewer", e);

        }

        return factory;

    }

    /**
     * method
     *
     * @param runtimeOutput .
     *
     * @return .
     *
     * @throws IOException .
     */
    private String getOutputViewerFactoryClassName(RuntimeOutput runtimeOutput)
        throws IOException {

        Reader           reader     = null;
        LineNumberReader lineReader = null;
        String           line       = null;
        String           klass      = null;
        int              a;
        int              b;
        int              c;

        try {

            reader         = runtimeOutput.openReader();
            lineReader     = new LineNumberReader(reader);

            while ((line = lineReader.readLine()) != null) {

                a = line.indexOf(VIEWER_STRING);

                if (a >= 0) {

                    b         = a + VIEWER_STRING.length();
                    c         = line.indexOf("\"", b);
                    klass     = line.substring(b, c);
                    LOG.info("getOutputViewerFactory = " + klass);

                    break;

                }

            }

            if (klass == null) {

                throw new IOException("unable to find viewer definition in file");

            }

        } catch (IOException e) {

            throw e;

        } finally {

            IOUtil.close(reader);

        }

        return klass;

    }

}
