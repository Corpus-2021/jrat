package org.shiftone.jrat.provider.log.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;


/**
 * class
 *
 * @author $author$
 * @version $Revision: 1.3 $
 */
public class Converter
{
    private static final Log   LOG                   = LogFactory.getLogger(Converter.class);
    public static final String DEFAULT_DATE_FORMAT   = "yyyy.MM.dd HH:mm:ss,SSS";
    private String             dateFormatText        = DEFAULT_DATE_FORMAT;
    private DateFormat         dateFormat            = null;
    private boolean            printSeqId            = true;
    private boolean            printThreadDepth      = false;
    private boolean            indentThreadDepth     = false;
    private boolean            printQualifiedClasses = false;

    /**
     * method
     *
     * @param in .
     * @param out .
     *
     * @throws IOException .
     */
   
    public void convert(InputStream in, OutputStream out)
        throws IOException
    {
   /*
        Writer   writer   = new OutputStreamWriter(out);
        LogInput logInput = new LogInput(in);
        Message  message  = null;

        while ((message = logInput.readMessage()) != null)
        {
            writer.write(String.valueOf(message));
            writer.write("\n");
        }

        writer.flush();

        writer.close();
        IOUtil.close(in);
        */
    }

    /**
     * method
     *
     * @param dateFormatText .
     */
    public void setDateFormat(String dateFormatText)
    {
        this.dateFormatText = dateFormatText;
        this.dateFormat = new SimpleDateFormat(dateFormatText);
    }

    /**
     * method
     *
     * @param printQualifiedClasses .
     */
    public void setPrintQualifiedClasses(boolean printQualifiedClasses)
    {
        this.printQualifiedClasses = printQualifiedClasses;
    }

    /**
     * method
     *
     * @param printSeqId .
     */
    public void setPrintSeqId(boolean printSeqId)
    {
        this.printSeqId = printSeqId;
    }

    /**
     * method
     *
     * @param printThreadDepth .
     */
    public void setPrintThreadDepth(boolean printThreadDepth)
    {
        this.printThreadDepth = printThreadDepth;
    }
}
