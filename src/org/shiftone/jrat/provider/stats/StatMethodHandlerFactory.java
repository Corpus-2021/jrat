package org.shiftone.jrat.provider.stats;

import org.shiftone.jrat.core.MethodKey;

import org.shiftone.jrat.core.spi.AbstractMethodHandlerFactory;
import org.shiftone.jrat.core.spi.MethodHandler;

import org.shiftone.jrat.util.IOUtil;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Class StatMethodHandlerFactory
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.23 $
 */
public class StatMethodHandlerFactory extends AbstractMethodHandlerFactory
{
    private static final Log LOG          = LogFactory.getLogger(StatMethodHandlerFactory.class);
    private List             handlerList  = new ArrayList();
    private boolean          recordUnused = true;

    /**
     * method
     *
     * @param methodKey .
     *
     * @return .
     */
    public MethodHandler createMethodHandler(MethodKey methodKey)
    {
        MethodHandler handler = null;

        handler = new StatMethodHandler(methodKey);

        handlerList.add(handler);

        return handler;
    }

    /**
     * method
     *
     * @param recordUnused .
     */
    public void setRecordUnused(boolean recordUnused)
    {
        this.recordUnused = recordUnused;
    }

    /**
     * Method shutdown
     */
    public synchronized void shutdown()
    {
        OutputStream        outputStream = null;
        StatOutput          statOutput = null;
        StatMethodHandler[] handlers   = null;

        try
        {
            outputStream     = getContext().newOutputStream(getOutputFile() + ".jrat", isCompressOutput());
            statOutput       = new StatOutput(outputStream);
			handlers		 = new StatMethodHandler[handlerList.size()];
            handlers         = (StatMethodHandler[]) handlerList.toArray(handlers);
			
            Arrays.sort(handlers, StatComparator.INSTANCE);
            statOutput.printStats(handlers, recordUnused);
        }
        catch (Exception e)
        {
            LOG.error("Error writting to " + getOutputFile(), e);
        }
        finally
        {
            IOUtil.close(statOutput);
            IOUtil.close(outputStream);
        }
    }

    /**
     * method
     *
     * @return .
     */
    public String toString()
    {
        return "Stats Handler Factory";
    }
}
