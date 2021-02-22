package org.shiftone.jrat.ui.viewer;

import org.shiftone.jrat.core.spi.RuntimeOutput;

import org.shiftone.jrat.util.IOUtil;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.zip.GZIPInputStream;

import javax.swing.JProgressBar;


/**
 * class
 *
 * @author $author$
 * @version $Revision: 1.4 $
 */
public class RuntimeOutputImpl implements RuntimeOutput
{
    private static final Log   LOG         = LogFactory.getLogger(RuntimeOutputImpl.class);
    private final File         file;
    private final JProgressBar progressBar;

    /**
     * Constructor for RuntimeOutputImpl
     *
     * @param file .
     * @param progressBar .
     *
     * @throws IOException .
     */
    RuntimeOutputImpl(File file, JProgressBar progressBar)
        throws IOException
    {
        this.file            = file;
        this.progressBar     = progressBar;

        if (file.exists() == false)
        {
            throw new IOException("file does not exist : " + file);
        }
    }

    /**
     * method
     *
     * @return .
     */
    public File getInputFile()
    {
        return file;
    }

    /**
     * method
     *
     * @return .
     *
     * @throws IOException .
     */
    public InputStream openInputStream() throws IOException
    {
        ProgressInputStream progressInputStream = null;
        InputStream         intputStream = null;

        LOG.info("openInputStream");

        progressInputStream = new ProgressInputStream(file);
        progressBar.setModel(progressInputStream.getBoundedRangeModel());

        intputStream = isZipped() ? (InputStream) new GZIPInputStream(progressInputStream) : progressInputStream;

        return intputStream;
    }

    /**
     * method
     *
     * @return .
     *
     * @throws IOException .
     */
    public Reader openReader() throws IOException
    {
        return new InputStreamReader(openInputStream());
    }

    /**
     * method
     *
     * @return .
     */
    private boolean isZipped()
    {
        return "gz".equalsIgnoreCase(IOUtil.getExtention(file));
    }

    /**
     * method
     */
    public void close()
    {
        LOG.info("close");
    }
}
