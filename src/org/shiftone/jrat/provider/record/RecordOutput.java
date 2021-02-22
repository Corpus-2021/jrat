package org.shiftone.jrat.provider.record;

import org.shiftone.jrat.core.MethodKey;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;


/**
 * Class RecordOutput
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.4 $
 */
public class RecordOutput extends DataOutputStream {

    private static final Log   LOG          = LogFactory.getLogger(RecordOutput.class);
    public static final Class  VIEWER_CLASS = Object.class;
    public static final String HEADER_TEXT  = VIEWER_CLASS.getName() + "\n";
    public static final char   PREFIX_THREAD       = 'T';
    public static final char   PREFIX_METHOD       = 'M';
    public static final char   PREFIX_METHOD_BEGIN = 'B';
    public static final char   PREFIX_METHOD_END   = 'E';
    public static final char   PREFIX_METHOD_ERROR = 'R';
    public static final char   PREFIX_CLOSE        = 'C';
    private ThreadLocal        threadLocal         = null;
    private int                threadCount         = 0;
    private int                methodCount         = 0;

    public RecordOutput(OutputStream outputStream) throws IOException {
        super(outputStream);

        // writes the output viewer the the file's first line
        for (int i = 0; i < HEADER_TEXT.length(); i++) {

            write((byte) HEADER_TEXT.charAt(i));

        }

        threadLocal = new ThreadLocal();

    }

    private int getThreadId() throws IOException {

        int     id;
        Integer integer = (Integer) threadLocal.get();

        if (integer == null) {

            id          = writeThreadDef(Thread.currentThread());
            integer     = new Integer(id);

            threadLocal.set(integer);

        }

        return integer.intValue();

    }

    /**
     * method
     *
     * @param thread .
     *
     * @return .
     */
    private synchronized int writeThreadDef(Thread thread)
        throws IOException {

        int tId = threadCount++;

        LOG.info("thread def " + tId + " " + thread.hashCode());

        writeChar(PREFIX_THREAD);
        writeLong(System.currentTimeMillis());
        writeInt(tId);
        writeUTF(thread.getName());

        return tId;

    }

    /**
     * method
     *
     * @param methodKey .
     *
     * @return .
     */
    public synchronized int writeMethodDef(MethodKey methodKey)
        throws IOException {

        int mId = 0;

        mId = methodCount++;
        writeChar(PREFIX_METHOD);
        writeLong(System.currentTimeMillis());
        writeInt(mId);
        writeUTF(methodKey.getClassName());
        writeUTF(methodKey.getMethodName());
        writeUTF(methodKey.getSignature());

        return mId;

    }

    /**
     * method
     *
     * @param methodId .
     */
    public synchronized void writeMethodStart(int methodId)
        throws IOException {

        int tId = getThreadId();

        writeChar(PREFIX_METHOD_BEGIN);
        writeLong(System.currentTimeMillis());
        writeInt(tId);
        writeInt(methodId);

    }

    /**
     * method
     *
     * @param methodId .
     * @param duration .
     * @param success .
     */
    public synchronized void writeMethodFinish(int methodId, long duration, boolean success)
        throws IOException {

        int tId = getThreadId();

        writeChar(PREFIX_METHOD_END);
        writeLong(System.currentTimeMillis());
        writeInt(tId);
        writeInt(methodId);
        writeLong(duration);
        writeBoolean(success);

    }

    /**
     * method
     *
     * @param methodId .
     * @param throwable .
     */
    public synchronized void writeMethodError(int methodId, Throwable throwable)
        throws IOException {

        int tId = getThreadId();

        writeChar(PREFIX_METHOD_ERROR);
        writeLong(System.currentTimeMillis());
        writeInt(tId);
        writeInt(methodId);
        writeUTF(throwable.getClass().getName());
        writeUTF(throwable.getMessage());

    }

    /**
     * method
     */
    public synchronized void close() throws IOException {

        writeChar(PREFIX_CLOSE);
        writeLong(System.currentTimeMillis());
        super.close();

    }

}
