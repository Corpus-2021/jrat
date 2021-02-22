package org.shiftone.jrat.provider.log.io.message;

import org.shiftone.jrat.provider.log.io.LogInput;

import java.io.DataInput;
import java.io.IOException;


public class ErrorMessage extends MethodMessage {

    private final String errorClassName;
    private final String errorMessage;

    public ErrorMessage(LogInput logIn, DataInput in) throws IOException {
        super(logIn, in);
        errorClassName     = in.readUTF();
        errorMessage       = in.readUTF();

    }

    public String toString() {

        return super.toString() + " error " + errorClassName + " : " + errorMessage;

    }

    /**
     * @return
     */
    public String getErrorClassName() {

        return errorClassName;

    }

    /**
     * @return
     */
    public String getErrorMessage() {

        return errorMessage;

    }

    public String getText() {

        return "threw " + errorClassName + " : " + errorMessage;

    }

}
