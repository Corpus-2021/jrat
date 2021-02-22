package org.shiftone.jrat.ui.viewer;

import org.shiftone.jrat.core.OutputViewerException;

import org.shiftone.jrat.core.spi.OutputViewerFactory;
import org.shiftone.jrat.core.spi.RuntimeOutput;

import org.shiftone.jrat.util.IOUtil;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.awt.Color;
import java.awt.Component;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


/**
 * Class SimpleTextOutputViewerFactory
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.3 $
 */
public class SimpleTextOutputViewerFactory implements OutputViewerFactory {

    private static final Log LOG = LogFactory.getLogger(SimpleTextOutputViewerFactory.class);

    /**
     * Method createViewerComponent
     *
     * @throws org.shiftone.jrat.core.OutputViewerException
     *
     * @throws OutputViewerException
     */
    public Component createViewerComponent(RuntimeOutput runtimeOutput)
        throws OutputViewerException {

        InputStream           inputStream  = null;
        JTextArea             textArea     = new JTextArea();
        JScrollPane           scrollPane   = new JScrollPane(textArea);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        LOG.debug("createViewerComponent");

        try {

            inputStream = runtimeOutput.openInputStream();
            IOUtil.copy(inputStream, outputStream);
            outputStream.flush();

            //
            textArea.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            textArea.setEditable(false);
            textArea.setBackground(Color.white);
            textArea.setText(new String(outputStream.toString()));

        } catch (Exception e) {

            throw new OutputViewerException("unable to create viewer", e);

        } finally {
		
            IOUtil.close(inputStream);
			IOUtil.close(outputStream);

        }

        return scrollPane;

    }

}
