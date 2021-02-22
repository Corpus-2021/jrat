package org.shiftone.jrat.provider.log.io.message;

import java.io.DataInput;
import java.io.IOException;

import org.shiftone.jrat.provider.log.io.LogInput;
import org.shiftone.jrat.util.StringUtil;


public class CloseMessage implements Message {

    private final long timeMs;
    private final int messageId;

    public CloseMessage(LogInput logIn, DataInput in) throws IOException {

        this.timeMs        = in.readLong();
        this.messageId     = in.readInt();

    }

    public long getMessageId() {

        return messageId;

    }

    public long getTimeMs() {

        return timeMs;

    }

    public String toString() {

        return StringUtil.hex(messageId) + " Log Closed at " //
         +StringUtil.dateToString(timeMs);

    }

}
