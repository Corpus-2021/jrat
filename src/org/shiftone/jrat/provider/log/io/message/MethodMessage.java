package org.shiftone.jrat.provider.log.io.message;

import org.shiftone.jrat.core.MethodKey;

import org.shiftone.jrat.provider.log.io.LogInput;
import org.shiftone.jrat.util.StringUtil;

import java.io.DataInput;
import java.io.IOException;


/**
 * class
 *
 * @author $author$
 * @version $Revision: 1.1 $
 */
public abstract class MethodMessage implements Message {

    private final LogInput logIn;
    private final long     timeMs;
    private final int      messageId;
    private final int      threadId;
    private final int      threadDepth;
    private final int      methodId;
    private String         threadName;
    private MethodKey      methodKey;

    /**
     * Constructor for MethodMessage
     *
     * @param logIn .
     * @param in .
     *
     * @throws IOException .
     */
	public MethodMessage(LogInput logIn, DataInput in) throws IOException {

        this.logIn           = logIn;
        
        this.timeMs          = in.readLong();
        this.messageId       = in.readInt();
        
        this.threadId        = in.readInt();
        this.threadDepth     = in.readInt();
        this.methodId        = in.readInt();

    }

    /**
     * method
     *
     * @return .
     */
    public String toString() {

        return StringUtil.hex(messageId) + " [" + getThreadName() + "] " //
         +StringUtil.dateToString(timeMs) + " " + threadDepth + " " //
         +getMethodKey().getMethodName();

    }

    /**
     * method
     *
     * @return .
     */
    public long getMessageId() {

        return messageId;

    }

    /**
     * DOCUMENT ME!
     *
     * @return
     */
    public MethodKey getMethodKey() {

        if (methodKey == null) {

            methodKey = logIn.getMethodKey(methodId);

        }

        return methodKey;

    }

    /**
     * DOCUMENT ME!
     *
     * @return
     */
    public int getThreadDepth() {

        return threadDepth;

    }

    /**
     * DOCUMENT ME!
     *
     * @return
     */
    public int getThreadId() {

        return threadId;

    }

    /**
     * DOCUMENT ME!
     *
     * @return
     */
    public String getThreadName() {

        if (threadName == null) {

            threadName = logIn.getThreadName(threadId);

        }

        return threadName;

    }

    /**
     * DOCUMENT ME!
     *
     * @return
     */
    public long getTimeMs() {

        return timeMs;

    }
    
    public abstract String getText() ;

}
