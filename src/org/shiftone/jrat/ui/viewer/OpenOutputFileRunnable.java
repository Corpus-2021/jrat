package org.shiftone.jrat.ui.viewer;

import java.awt.Component;

import javax.swing.JTabbedPane;

import org.shiftone.jrat.core.spi.OutputViewerFactory;
import org.shiftone.jrat.util.Assert;
import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;


/**
 * Class OpenOutputFileRunnable
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.6 $
 */
public class OpenOutputFileRunnable implements Runnable
{
    private static final Log    LOG           = LogFactory.getLogger(OpenOutputFileRunnable.class);
    private String              title         = null;
    private RuntimeOutputImpl   runtimeOutput = null;
    private JTabbedPane         tabbedPane    = null;
    private OutputViewerFactory viewerFactory = null;

    /**
     * Constructor OpenOutputFileRunnable
     *
     * @param runtimeOutput
     * @param viewerFactory
     * @param title
     * @param tabbedPane
     */
    public OpenOutputFileRunnable(
        RuntimeOutputImpl runtimeOutput, OutputViewerFactory viewerFactory, String title, JTabbedPane tabbedPane)
    {
        this.runtimeOutput     = runtimeOutput;
        this.viewerFactory     = viewerFactory;
        this.tabbedPane        = tabbedPane;
        this.title             = title;
    }

    /**
     * Method run
     *
     * @throws RuntimeException .
     */
    public void run()
    {
        Component component = null;

        try
        {
            component = viewerFactory.createViewerComponent(runtimeOutput);

			Assert.assertNotNull("component", component);
			
            tabbedPane.add(title, component);
            tabbedPane.setSelectedComponent(component);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Error opening viewer for : " + title, e);
        }
        finally
        {
            runtimeOutput.close();
            tabbedPane.validate();
        }
    }
}
