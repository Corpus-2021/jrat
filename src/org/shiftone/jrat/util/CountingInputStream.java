package org.shiftone.jrat.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * Class CountingInputStream
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.4 $
 */
public class CountingInputStream extends BufferedInputStream {

    private long bytesReadMark = 0;
    private long bytesRead = 0;

    /**
     * Constructor for CountingInputStream
     *
     * @param in .
     * @param size .
     */
    public CountingInputStream(InputStream in, int size) {
        super(in, size);

    }

    /**
     * Constructor for CountingInputStream
     *
     * @param in .
     */
    public CountingInputStream(InputStream in) {
        super(in);

    }

    /**
     * method
     *
     * @return .
     */
    public long getBytesRead() {

        return bytesRead;

    }

    /**
     * method
     *
     * @return .
     *
     * @throws IOException .
     */
    public synchronized int read() throws IOException {

        int read = super.read();

        if (read >= 0) {

            bytesRead++;

        }

        return read;

    }

    /**
     * method
     *
     * @param b .
     * @param off .
     * @param len .
     *
     * @return .
     *
     * @throws IOException .
     */
    public synchronized int read(byte[] b, int off, int len)
        throws IOException {

        int read = super.read(b, off, len);

        if (read >= 0) {

            bytesRead += read;

        }

        return read;

    }

    /**
     * method
     *
     * @param n .
     *
     * @return .
     *
     * @throws IOException .
     */
    public synchronized long skip(long n) throws IOException {

        long skipped = super.skip(n);

        if (skipped >= 0) {

            bytesRead += skipped;

        }

        return skipped;

    }

    /**
     * method
     *
     * @param readlimit .
     */
    public synchronized void mark(int readlimit) {

        super.mark(readlimit);
        bytesReadMark = bytesRead;

    }

    /**
     * method
     *
     * @throws IOException .
     */
    public synchronized void reset() throws IOException {

        super.reset();
        bytesRead = bytesReadMark;

    }

}
