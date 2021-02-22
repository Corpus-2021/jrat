package org.shiftone.jrat.jda.config.connect.pipe;



import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.io.IOException;
import java.io.OutputStream;


/**
 * Class NullOutputStream
 *
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 */
public class NullOutputStream extends OutputStream
{

    private static final Log LOG       = LogFactory.getLogger(NullOutputStream.class);
    private long             byteCount = 0;

    /**
     * Method write
     *
     * @throws IOException
     */
    public void write(byte b[]) throws IOException
    {
        byteCount += b.length;
    }

    /**
     * Method write
     *
     * @throws IOException
     */
    public void write(byte b[], int off, int len) throws IOException
    {
        byteCount += len;
    }

    /**
     * Method write
     *
     * @throws IOException
     */
    public void write(int b) throws IOException
    {
        byteCount += 1;
    }

    /**
     * Method flush
     *
     * @throws IOException
     */
    public void flush() throws IOException {}

    /**
     * Method close
     *
     * @throws IOException
     */
    public void close() throws IOException
    {
        LOG.info("total of " + byteCount + " bytes discarded");
    }

    /**
     * Method toString
     */
    public String toString()
    {
        return "bit bucket";
    }
}
