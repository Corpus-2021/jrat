package org.shiftone.jrat.provider.log.io.message;

import java.io.DataInput;
import java.io.IOException;

import org.shiftone.jrat.provider.log.io.LogInput;


public class EnterMessage extends MethodMessage {

	public EnterMessage(LogInput logIn, DataInput in) throws IOException {
        super(logIn, in);

    }

    public String toString() {

        return super.toString() + " entered";

    }
    
	public String getText() {
		return "entered";
	}

}
