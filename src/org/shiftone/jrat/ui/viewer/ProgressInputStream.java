package org.shiftone.jrat.ui.viewer;



import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import javax.swing.BoundedRangeModel;
import javax.swing.DefaultBoundedRangeModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * Class ProgressInputStream
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.3 $
 */
public class ProgressInputStream extends InputStream
{

    private static final Log         LOG         = LogFactory.getLogger(ProgressInputStream.class);
    private InputStream              inputStream = null;
    private DefaultBoundedRangeModel rangeModel  = null;
    private long                     maxBytes    = 0;
    private long                     bytesRead   = 0;

    /**
     * Constructor ProgressInputStream
     *
     *
     * @param inputStream
     * @param maxBytes
     */
    public ProgressInputStream(InputStream inputStream, long maxBytes)
    {

        this.inputStream = inputStream;
        this.maxBytes    = maxBytes;
        this.rangeModel  = new DefaultBoundedRangeModel(0, 1, 0, (int) maxBytes);
    }

    /**
     * Constructor ProgressInputStream
     *
     *
     * @param file
     *
     * @throws IOException
     */
    public ProgressInputStream(File file) throws IOException
    {
        this(new FileInputStream(file), file.length());
    }

    /**
     * Method close
     *
     * @throws IOException
     */
    public void close() throws IOException
    {

        LOG.debug("bytesRead = " + bytesRead);
        LOG.debug("maxBytes  = " + maxBytes);
		LOG.info("close");
        rangeModel.setValue(0);
        super.close();
    }

    /**
     * Method getBoundedRangeModel
     */
    public BoundedRangeModel getBoundedRangeModel()
    {
        return rangeModel;
    }

    /**
     * Method updateModel
     */
    private void updateModel()
    {
        rangeModel.setValue((int) bytesRead);
    }

    /**
     * Method read
     *
     * @throws IOException
     */
    public int read() throws IOException
    {

        int i = inputStream.read();

        if ((i != -1) && (bytesRead < maxBytes))
        {
            bytesRead++;

            updateModel();
        }

        return i;
    }

    /**
     * Method markSupported
     */
    public boolean markSupported()
    {
        return false;
    }

    /**
     * Method read
     *
     * @throws IOException
     */
    public int read(byte b[]) throws IOException
    {

        int i = super.read(b);

        if ((i != -1) && (bytesRead < maxBytes))
        {
            bytesRead += i;

            updateModel();
        }

        return i;
    }

    /**
     * Method read
     *
     * @throws IOException
     */
    public int read(byte b[], int off, int len) throws IOException
    {

        int i = super.read(b, off, len);

        if ((i != -1) && (bytesRead < maxBytes))
        {
            bytesRead += i;

            updateModel();
        }

        return i;
    }

    /**
     * Method skip
     *
     * @throws IOException
     */
    public long skip(long n) throws IOException
    {

        long i = super.skip(n);

        if ((i != -1) && (bytesRead < maxBytes))
        {
            bytesRead += i;

            updateModel();
        }

        return i;
    }
}
