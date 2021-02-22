package org.shiftone.jrat.provider.log.io.message;

import org.shiftone.jrat.provider.log.io.LogInput;

import org.shiftone.jrat.util.StringUtil;

import java.io.DataInput;
import java.io.IOException;


public class FinishMessage extends MethodMessage {

    private final long    duration;
    private final boolean success;

    public FinishMessage(LogInput logIn, DataInput in)
        throws IOException {
        super(logIn, in);
        duration     = in.readLong();
        success      = in.readBoolean();

    }

    public String toString() {

        String verb = success ? " succeeded in " : " failed in ";

        return super.toString() + verb + duration + " ms ";

    }

    /**
     * @return
     */
    public long getDuration() {

        return duration;

    }

    /**
     * @return
     */
    public boolean isSuccess() {

        return success;

    }

    public String getText() {

        return (duration == 0) ? ("exited") : ("exited after " + StringUtil.durationToString(duration));

    }

}
