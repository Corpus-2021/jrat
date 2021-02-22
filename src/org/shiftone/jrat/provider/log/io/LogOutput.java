package org.shiftone.jrat.provider.log.io;

import org.shiftone.jrat.core.MethodKey;

import org.shiftone.jrat.core.spi.ShutdownListener;

import org.shiftone.jrat.provider.log.ui.LogOutputViewerFactory;

import org.shiftone.jrat.util.Sequence;
import org.shiftone.jrat.util.StringUtil;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Class LogOutput.
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.5 $
 * 
 * Main JBoss thread during startup/shitdown cycle produces 
 * 9609611 log messages in a 277 Meg file.  
 * This is about 30.2 bytes per message.

 */
public class LogOutput implements ShutdownListener {

    private static final Log       LOG                        = LogFactory.getLogger(LogOutput.class);
    private static final Class     VIEWER_CLASS               = LogOutputViewerFactory.class;
    private static final String    VIEWER_TEXT                = VIEWER_CLASS.getName();
    public static final int        PAGE_SIZE_IN_MESSAGES      = 1024 * 18;
    public static final byte       PREFIX_THREAD              = 'T';
    public static final byte       PREFIX_METHOD              = 'M';
    public static final byte       PREFIX_METHOD_BEGIN        = 'B';
    public static final byte       PREFIX_METHOD_END          = 'E';
    public static final byte       PREFIX_METHOD_ERROR        = 'R';
    public static final byte       PREFIX_CLOSE               = 'C';
    public static final byte       PREFIX_FOOTER              = 'F';
    public static final byte       PREFIX_FOOTER_METHOD_TABLE = 'm';
    public static final byte       PREFIX_FOOTER_THREAD_TABLE = 't';
    public static final byte       PREFIX_FOOTER_OFFSET_TABLE = 'o';
    public static final byte       EOF                        = 0x1A;
    private final DataOutputStream out;
    private final ThreadLocal      infoThreadLocal;
    private final Sequence         logMessageSeq;
    private final Sequence         threadSeq;
    private final Map              methodMap;
    private final List             methodList;
    private final List             offsetList;
    private final List             threadList;
    private int                    messagesCounter            = 0;

    public LogOutput(OutputStream outputStream, Sequence logMessageSeq)
        throws IOException {

        this.logMessageSeq     = logMessageSeq;

        this.threadSeq           = new Sequence();
        this.methodMap           = new HashMap();
        this.methodList          = new ArrayList(1024);
        this.offsetList          = new ArrayList(1024);
        this.threadList          = new ArrayList(10);
        this.infoThreadLocal     = new ThreadLocal();
        this.out                 = new DataOutputStream(outputStream);

        out.write(StringUtil.toBytes("viewer=\"" + VIEWER_TEXT + "\""));
        out.writeByte(13);
        out.writeByte(10);
        out.writeByte('J');
        out.flush();

    }

    private int getMethodId(MethodKey methodKey) throws IOException {

        Integer methodId = (Integer) methodMap.get(methodKey);

        if (methodId == null) {

            int id = methodMap.size();

            methodId = new Integer(id);
            methodMap.put(methodKey, methodId);

            methodList.add(id, methodKey);
            writeMethodDef(out, methodKey, methodId.intValue());

        }

        return methodId.intValue();

    }

    private synchronized ThreadInfo getThreadInfo() throws IOException {

        ThreadInfo threadInfo = (ThreadInfo) infoThreadLocal.get();

        if (threadInfo == null) {

            Thread      thread    = Thread.currentThread();
            ThreadGroup group     = thread.getThreadGroup();
            String      groupName = (group != null) ? group.getName() : "[null]";

            threadInfo                = new ThreadInfo();
            threadInfo.name           = thread.getName();
            threadInfo.groupName      = groupName;
            threadInfo.threadId       = threadList.size();
            threadInfo.stackDepth     = 0;

            LOG.info("getThreadInfo " + threadInfo.threadId);

            threadList.add(threadInfo.threadId, threadInfo);

            writeThreadDef(out, threadInfo);

            infoThreadLocal.set(threadInfo);

        }

        return threadInfo;

    }

    private void writeMethodDef(DataOutput output, MethodKey methodKey, int methodId)
        throws IOException {

        output.writeByte(PREFIX_METHOD);
        output.writeInt(methodId);
        writeString(output, methodKey.getClassName());
        writeString(output, methodKey.getMethodName());
        writeString(output, methodKey.getSignature());

    }

    private void writeThreadDef(DataOutput output, ThreadInfo threadInfo)
        throws IOException {

        output.writeByte(PREFIX_THREAD);
        output.writeInt(threadInfo.threadId);
        writeString(output, threadInfo.groupName);
        writeString(output, threadInfo.name);

    }

    private void writeEventPrefix(byte c, ThreadInfo threadInfo, int methodId)
        throws IOException {

        if ((messagesCounter % PAGE_SIZE_IN_MESSAGES) == 0) {

            offsetList.add(new Integer(out.size()));

        }

        messagesCounter++;

        out.writeByte(c);

        out.writeLong(System.currentTimeMillis());
        out.writeInt((int) logMessageSeq.getNextValue());

        out.writeInt(threadInfo.threadId);
        out.writeInt(threadInfo.stackDepth);
        out.writeInt(methodId);

    }

    public synchronized void writeMethodStart(MethodKey methodKey)
        throws IOException {

        ThreadInfo threadInfo = getThreadInfo();
        int        methodId = getMethodId(methodKey);

        threadInfo.stackDepth++;
        writeEventPrefix(PREFIX_METHOD_BEGIN, threadInfo, methodId);

    }

    public synchronized void writeMethodFinish(MethodKey methodKey, long duration, boolean success)
        throws IOException {

        ThreadInfo threadInfo = getThreadInfo();
        int        methodId = getMethodId(methodKey);

        writeEventPrefix(PREFIX_METHOD_END, threadInfo, methodId);

        out.writeLong(duration);
        out.writeBoolean(success);
        threadInfo.stackDepth--;

    }

    public synchronized void writeMethodError(MethodKey methodKey, Throwable e)
        throws IOException {

        ThreadInfo threadInfo = getThreadInfo();
        int        methodId = getMethodId(methodKey);

        writeEventPrefix(PREFIX_METHOD_ERROR, threadInfo, methodId);

        writeString(out, e.getClass().getName());
        writeString(out, e.getMessage());

    }

    private synchronized void writeClose(DataOutput output) throws IOException {

        output.writeByte(PREFIX_CLOSE);
        output.writeLong(System.currentTimeMillis());
        output.writeInt((int) logMessageSeq.getNextValue());

    }

    private void writeString(DataOutput output, String text)
        throws IOException {

        output.writeUTF((text == null) ? "" : text);

    }

    public synchronized void shutdown() {

        LOG.info("shutdown");

        try {

            close();

        } catch (Exception e) {

            LOG.error("error closing output", e);

        }

    }

    public synchronized void close() throws IOException {

        writeClose(out);

        writeFooter();

        out.flush();
        out.close();

    }

    private void writeFooter() throws IOException {

        ByteArrayOutputStream buffOut           = null;
        DataOutputStream      footer            = null;
        byte[]                buff              = null;
        int                   footerStartOffset = out.size();

        buffOut     = new ByteArrayOutputStream();
        footer      = new DataOutputStream(buffOut);

        writeFooterThreadTable(footer);
        writeFooterMethodTable(footer);
        writeClose(footer);

        writeFooterOffsetTable(footer);

        footer.flush();
        buff = buffOut.toByteArray();

        out.write(buff);
        out.writeInt(footerStartOffset);
        out.writeByte(EOF);

        LOG.info("footer wrote successfully");

    }

    private void writeFooterMethodTable(DataOutput footer)
        throws IOException {

        MethodKey methodKey = null;
        int       size = methodList.size();

        //footer.writeByte(PREFIX_FOOTER_METHOD_TABLE);
        //footer.writeInt(size);
        for (int i = 0; i < size; i++) {

            methodKey = (MethodKey) methodList.get(i);
            writeMethodDef(footer, methodKey, i);

        }

    }

    private void writeFooterThreadTable(DataOutput footer)
        throws IOException {

        ThreadInfo threadInfo = null;
        int        size = threadList.size();

        //footer.writeByte(PREFIX_FOOTER_THREAD_TABLE);
        //footer.writeInt(size);
        for (int i = 0; i < size; i++) {

            threadInfo = (ThreadInfo) threadList.get(i);
            writeThreadDef(footer, threadInfo);

        }

    }

    private void writeFooterOffsetTable(DataOutput footer)
        throws IOException {

        Integer offset = null;
        int     size = offsetList.size();

        footer.writeByte(PREFIX_FOOTER_OFFSET_TABLE);
        footer.writeInt(PAGE_SIZE_IN_MESSAGES);
        footer.writeInt(messagesCounter);
        footer.writeInt(size);

        for (int i = 0; i < size; i++) {

            offset = (Integer) offsetList.get(i);
            footer.writeInt(offset.intValue());

        }

    }

    /**
     * method
     *
     * @return .
     */
    public String toString() {

        return "LogOutput";

    }

}


/**
 * class
 * @author $author$
 * @version $Revision: 1.5 $
 */
class ThreadInfo {

    int    threadId;
    int    stackDepth;
    String groupName;
    String name;

}
