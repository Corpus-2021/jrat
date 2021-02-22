package org.shiftone.jrat.core.spi;



import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;


/**
 * Interface RuntimeContext
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.1 $
 */
public interface RuntimeContext
{
    

    /**
     * Method newOutputStream
     *
     * @return .
     *
     * @throws IOException
     */
    OutputStream newOutputStream(String fileName) throws IOException;

    /**
     * Method newOutputStream
     *
     * @return .
     *
     * @throws IOException
     */
    OutputStream newOutputStream(String fileName, boolean compress)
        throws IOException;

    /**
     * Method uniqNumber
     *
     * @return .
     */
    long uniqNumber();

    /**
     * Method getStartupDate
     *
     * @return .
     */
    Date getStartupDate();

    /**
     * Method getStartTimeMs
     *
     * @return .
     */
    long getStartTimeMs();

    /**
     * Method getUpTimeMs
     *
     * @return .
     */
    long getUpTimeMs();

    /**
     * Method addShutdownListener
     */
    void addShutdownListener(ShutdownListener listener);
}
