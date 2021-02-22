package org.shiftone.jrat.util;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;


/**
 * class
 * @author $author$
 * @version $Revision: 1.5 $
 */
public class AsyncOutputStream extends OutputStream implements Runnable {

    private static final Log           LOG                    = LogFactory.getLogger(AsyncOutputStream.class);
    private static final int           INITIAL_BUFFER_SIZE    = 1024 * 512;
    private static final int           DEFAULT_SYNC_THRESHOLD = 1024 * 1024;
    private static final int           FLUSH_FREQUENCY        = 500;
    private static final WeakScheduler SCHEDULER              = new WeakScheduler();
    private int                        syncThreshold          = DEFAULT_SYNC_THRESHOLD;
    private Object                     bufferLock             = new Object();
    private Object                     targetLock             = new Object();
    private ByteArrayOutputStream      buffer;
    private OutputStream               target;

    /**
     * Constructor for AsyncOutputStream
     *
     * @param outputStream .
     */
    public AsyncOutputStream(OutputStream outputStream) {

        this.target     = outputStream;
        this.buffer     = newBuffer();

        SCHEDULER.schedule(this, FLUSH_FREQUENCY);

    }

    /**
     * method
     *
     * @return .
     */
    private static ByteArrayOutputStream newBuffer() {

        return new ByteArrayOutputStream(INITIAL_BUFFER_SIZE);

    }

    /**
     * method
     *
     * @return .
     */
    private byte[] swapBuffer() {

        ByteArrayOutputStream oldOutputStream = null;

        synchronized (bufferLock) {

            oldOutputStream     = this.buffer;
            this.buffer         = newBuffer();

        }

        return oldOutputStream.toByteArray();

    }

    /**
     * method
     *
     * @throws IOException .
     */
    public void close() throws IOException {

        flush();
        super.close();

    }

    /**
     * Dumps the current buffer to the real target OutputStream, and then flushes the target OutputStream.
     *
     * @throws IOException .
     */
    public void flush() throws IOException {

        byte[] oldBuffer = swapBuffer();

        if (oldBuffer.length > 0) {

            synchronized (targetLock) {

                LOG.info("flush " + oldBuffer.length);
                target.write(oldBuffer);
                target.flush();

            }

        }

    }

    /**
     * method
     *
     * @throws IOException .
     */
    private void performSyncIfNeeded() throws IOException {

        if (buffer.size() >= syncThreshold) {

            flush();

        }

    }

    /**
     * method
     *
     * @param arg0 .
     * @param arg1 .
     * @param arg2 .
     *
     * @throws IOException .
     */
    public void write(byte[] arg0, int arg1, int arg2)
        throws IOException {

        synchronized (bufferLock) {

            buffer.write(arg0, arg1, arg2);
            performSyncIfNeeded();

        }

    }

    /**
     * method
     *
     * @param arg0 .
     *
     * @throws IOException .
     */
    public void write(byte[] arg0) throws IOException {

        synchronized (bufferLock) {

            buffer.write(arg0);
            performSyncIfNeeded();

        }

    }

    /**
     * method
     *
     * @param arg0 .
     *
     * @throws IOException .
     */
    public void write(int arg0) throws IOException {

        synchronized (bufferLock) {

            buffer.write(arg0);
            performSyncIfNeeded();

        }

    }

    /**
     * method
     */
    public void run() {

        try {

            flush();

        } catch (Exception e) {

            throw new RuntimeException("error flushing async output stream", e);

        }

    }

}
