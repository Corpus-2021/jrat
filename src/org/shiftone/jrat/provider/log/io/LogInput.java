package org.shiftone.jrat.provider.log.io;

import org.shiftone.jrat.core.MethodKey;

import org.shiftone.jrat.provider.log.io.message.CloseMessage;
import org.shiftone.jrat.provider.log.io.message.EnterMessage;
import org.shiftone.jrat.provider.log.io.message.ErrorMessage;
import org.shiftone.jrat.provider.log.io.message.FinishMessage;
import org.shiftone.jrat.provider.log.io.message.Message;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.io.DataInput;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;


/**
 * class
 *
 * @author $author$
 * @version $Revision: 1.7 $
 */
public class LogInput {

    private static final Log LOG       = LogFactory.getLogger(LogInput.class);
    private DataInput        in        = null;
    private Map              threadMap = new HashMap();
    private Map              methodMap = new HashMap();

    public LogInput(DataInput dataInput) throws IOException {

        LOG.info("LogInput " + dataInput);
        this.in = dataInput;

    }

    public void readPreamble() throws IOException {

        byte c;

        LOG.info("readPreamble " + in);

        while (in.readByte() != 13) {

            ;

        }

        if ((in.readByte() != 10) || (in.readByte() != 'J')) {

            throw new LogFileFormatException("log file preamble is corrupt");

        }

    }

    /**
     * method
     *
     * @return .
     *
     * @throws IOException .
     */
    public synchronized Message readMessage() throws IOException {

        Message message = null;
        byte    c;

        if (in == null) {

            return null;

        }

        c = in.readByte();

        while ((c == LogOutput.PREFIX_METHOD) || (c == LogOutput.PREFIX_THREAD)) {

            if (c == LogOutput.PREFIX_METHOD) {

                readMethodDef();

            } else if (c == LogOutput.PREFIX_THREAD) {

                readThreadDef();

            }

            c = in.readByte();

        }

        if (c == LogOutput.PREFIX_METHOD_BEGIN) {

            message = new EnterMessage(this, in);

        } else if (c == LogOutput.PREFIX_METHOD_END) {

            message = new FinishMessage(this, in);

        } else if (c == LogOutput.PREFIX_METHOD_ERROR) {

            message = new ErrorMessage(this, in);

        } else if (c == LogOutput.PREFIX_CLOSE) {

            message = new CloseMessage(this, in);

        } else {

            throw new LogFileFormatException("bad message format : " + c + " = " + (char) c);

        }

        return message;

    }

    /**
     * method
     *
     * @throws IOException .
     */
    private void readThreadDef() throws IOException {

        int    threadId = in.readInt();

        String groupName = in.readUTF();
        String name      = in.readUTF();

        defineThread(threadId, groupName, name);

    }

    public void defineThread(int threadId, String groupName, String name) {

        ThreadNames names = new ThreadNames();

        names.groupName     = groupName;
        names.name          = name;

        threadMap.put(new Integer(threadId), names);

    }

    /**
     * method
     *
     * @param threadId .
     *
     * @return .
     */
    ThreadNames getThreadNames(int threadId) {

        ThreadNames names = (ThreadNames) threadMap.get(new Integer(threadId));

        return names;

    }

    /**
     * method
     *
     * @param threadId .
     *
     * @return .
     */
    public String getThreadName(int threadId) {

        return getThreadNames(threadId).name;

    }

    /**
     * method
     *
     * @param threadId .
     *
     * @return .
     */
    String getThreadGroupName(int threadId) {

        return getThreadNames(threadId).groupName;

    }

    /**
     * method
     *
     * @throws IOException .
     */
    private void readMethodDef() throws IOException {

        int       methodId   = in.readInt();
        String    className  = in.readUTF();
        String    methodName = in.readUTF();
        String    signature  = in.readUTF();
        MethodKey methodKey  = new MethodKey(className, methodName, signature);

        LOG.debug("method def : " + methodKey);
        defineMethod(methodId, methodKey);

    }

    private void defineMethod(int methodId, MethodKey methodKey) {

        methodMap.put(new Integer(methodId), methodKey);

    }

    /**
     * method
     *
     * @param methodId .
     *
     * @return .
     */
    public MethodKey getMethodKey(int methodId) {

        return (MethodKey) methodMap.get(new Integer(methodId));

    }

}


/**
 * class
 *
 * @author $author$
 * @version $Revision: 1.7 $
 */
class ThreadNames {

    String groupName;
    String name;

}
