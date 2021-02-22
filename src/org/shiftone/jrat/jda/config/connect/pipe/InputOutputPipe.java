package org.shiftone.jrat.jda.config.connect.pipe;



import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.io.InputStream;
import java.io.OutputStream;


/**
 * Class InputOutputPipe
 *
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 */
public class InputOutputPipe implements Runnable
{

    private static final Log LOG          = LogFactory.getLogger(InputOutputPipe.class);
    private InputStream      inputStream  = null;
    private OutputStream     outputStream = null;

    /**
     * Constructor InputOutputPipe
     *
     *
     * @param inputStream
     */
    public InputOutputPipe(InputStream inputStream)
    {
        this(inputStream, new NullOutputStream());
    }

    /**
     * Constructor InputOutputPipe
     *
     *
     * @param inputStream
     * @param outputStream
     */
    public InputOutputPipe(InputStream inputStream, OutputStream outputStream)
    {

        this.inputStream  = inputStream;
        this.outputStream = outputStream;

        new Thread(this).start();
    }

    /**
     * Method run
     */
    public void run()
    {

        byte[] buffer = new byte[512];
        int    bytes  = 0;

        try
        {
            while ((bytes = inputStream.read(buffer)) >= 0)
            {
                outputStream.write(buffer, 0, bytes);
            }
        }
        catch (Exception e)
        {
            LOG.info("error reading from process" + e.getMessage());
            close(outputStream);
            close(inputStream);
        }
    }

    /**
     * Method close
     */
    private void close(InputStream inputStream)
    {

        try
        {
            inputStream.close();
        }
        catch (Exception e) {}
    }

    /**
     * Method close
     */
    private void close(OutputStream outputStream)
    {

        try
        {
            outputStream.close();
        }
        catch (Exception e) {}
    }
}
