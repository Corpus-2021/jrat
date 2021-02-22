package org.shiftone.jrat.util;

import java.io.IOException;
import java.io.OutputStream;


/**
 * class
 * @author $author$
 * @version $Revision: 1.3 $
 */
public class NullOutputStream extends OutputStream {

    public static final OutputStream INSTANCE = new NullOutputStream();

    /**
     * method
     *
     * @param b .
     *
     * @throws IOException .
     */
    public void write(int b) throws IOException {

    }

    /**
     * method
     *
     * @throws IOException .
     */
    public void close() throws IOException {

    }

    /**
     * method
     *
     * @throws IOException .
     */
    public void flush() throws IOException {

    }

    /**
     * method
     *
     * @param b .
     * @param off .
     * @param len .
     *
     * @throws IOException .
     */
    public void write(byte[] b, int off, int len) throws IOException {

    }

    /**
     * method
     *
     * @param b .
     *
     * @throws IOException .
     */
    public void write(byte[] b) throws IOException {

    }

}
