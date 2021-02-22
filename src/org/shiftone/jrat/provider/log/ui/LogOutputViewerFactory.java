package org.shiftone.jrat.provider.log.ui;

import java.awt.Component;

import org.shiftone.jrat.core.OutputViewerException;
import org.shiftone.jrat.core.spi.OutputViewerFactory;
import org.shiftone.jrat.core.spi.RuntimeOutput;
import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;


/**
 * class
 *
 * @author $author$
 * @version $Revision: 1.6 $
 */
public class LogOutputViewerFactory implements OutputViewerFactory {

    private static final Log LOG = LogFactory.getLogger(LogOutputViewerFactory.class);

    /**
     * method
     *
     * @param runtimeOutput .
     *
     * @return .
     *
     * @throws OutputViewerException .
     */
    public Component createViewerComponent(RuntimeOutput runtimeOutput)
        throws OutputViewerException {

        LOG.info("createViewerComponent ");

		Component component = null;

        try {

			component = new LogViewerPanel(runtimeOutput);

        } catch (Exception e) {

            throw new OutputViewerException("Error creating logfile viewer", e);

        }

        return component;

    }

}
