package org.shiftone.jrat.inject.process;



import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.io.IOException;
import java.io.InputStream;


/**
 * Class OpenInputStream wrapps/proxies to a real InputStream and prevents the caller
 * from closing the underlying input stream.  This is useful when reading chunks from a
 * ZipInputStream passing the archive entry inputStreams to code that calls close().
 * This would typically close the entire ZipInputStream, which would prevent any other
 * archive entries from being read.
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.1 $
 */
public class OpenInputStream extends InputStream
{

    private static final Log LOG         = LogFactory.getLogger(OpenInputStream.class);
    private InputStream      inputStream = null;
    private boolean          isOpen      = true;

    /**
     * Constructor OpenInputStream
     *
     *
     * @param inputStream
     */
    public OpenInputStream(InputStream inputStream)
    {
        this.inputStream = inputStream;
    }

    /**
     * Method assertOpen
     *
     * @throws java.io.IOException
     *
     * @throws IOException
     */
    public void assertOpen() throws IOException
    {

        if (isOpen == false)
        {
            throw new IOException("InputStream is closed");
        }
    }

    /**
     * Method available
     *
     * @throws java.io.IOException
     *
     * @throws IOException
     */
    public int available() throws IOException
    {
        return inputStream.available();
    }

    /**
     * Method close does not call close() on the underlying input stream.
     * It set's a flag that is used assertions in the read methods of this class.
     *
     * @throws java.io.IOException
     *
     * @throws IOException
     */
    public void close() throws IOException
    {

        assertOpen();

        isOpen = false;

        // DO NOT inputStream.close();
    }

    /**
     * Method mark
     */
    public synchronized void mark(int readlimit)
    {
        inputStream.mark(readlimit);
    }

    /**
     * Method markSupported
     */
    public boolean markSupported()
    {
        return inputStream.markSupported();
    }

    /**
     * Method read
     *
     * @throws java.io.IOException
     *
     * @throws IOException
     */
    public int read() throws IOException
    {

        assertOpen();

        return inputStream.read();
    }

    /**
     * Method read
     *
     * @throws java.io.IOException
     *
     * @throws IOException
     */
    public int read(byte b[]) throws IOException
    {

        assertOpen();

        return inputStream.read(b);
    }

    /**
     * Method read
     *
     * @throws java.io.IOException
     *
     * @throws IOException
     */
    public int read(byte b[], int off, int len) throws IOException
    {

        assertOpen();

        return inputStream.read(b, off, len);
    }

    /**
     * Method reset
     *
     * @throws java.io.IOException
     *
     * @throws IOException
     */
    public synchronized void reset() throws IOException
    {
        assertOpen();
        inputStream.reset();
    }

    /**
     * Method skip
     *
     * @throws java.io.IOException
     *
     * @throws IOException
     */
    public long skip(long n) throws IOException
    {

        assertOpen();

        return inputStream.skip(n);
    }
}
