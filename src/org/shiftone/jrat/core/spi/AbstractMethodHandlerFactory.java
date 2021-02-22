package org.shiftone.jrat.core.spi;

import org.shiftone.jrat.core.MethodKey;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;


/**
 * Class AbstractMethodHandlerFactory
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.5 $
 */
public abstract class AbstractMethodHandlerFactory implements MethodHandlerFactory, ShutdownListener
{
    private static final Log    LOG             = LogFactory.getLogger(AbstractMethodHandlerFactory.class);
    private static final String FACTORY_POSTFIX = "Factory";
    private RuntimeContext      context         = null;
    private boolean             compressOutput  = false;
    private String              outputFile      = getDefaultOutputFile();

    /**
     * Method createMethodHandler
     *
     * @return .
     */
    public abstract MethodHandler createMethodHandler(MethodKey methodKey);

    // ------------------------------------------------------------

    /**
     * Method isCompress
     *
     * @return .
     */
    public boolean isCompressOutput()
    {
        return compressOutput;
    }

    /**
     * Method setCompress
     *
     * @param compress .
     */
    public void setCompressOutput(boolean compress)
    {
        this.compressOutput = compress;
    }

    /**
     * Method getContext
     *
     * @return .
     */
    public RuntimeContext getContext()
    {
        return context;
    }

    /**
     * Method getDefaultOutputFile
     *
     * @return .
     */
    public String getDefaultOutputFile()
    {
        Class  klass     = this.getClass();
        String klassName = klass.getName();
        int    lastDot   = klassName.lastIndexOf(".");
        String name      = (lastDot == -1) ? klassName : klassName.substring(lastDot + 1);

        if (name.endsWith(FACTORY_POSTFIX))
        {
            name = name.substring(0, name.length() - FACTORY_POSTFIX.length());
        }

        return name;
    }

    /**
     * Method getOutputFile
     *
     * @return .
     */
    public String getOutputFile()
    {
        return outputFile;
    }

    /**
     * Method setOutputFile
     *
     * @param outputFile .
     */
    public void setOutputFile(String outputFile)
    {
        this.outputFile = outputFile;
    }

    // ------------------------------------------------------------

    /**
     * Method shutdown
     */
    public void shutdown()
    {
    }

    /**
     * Method startup
     *
     * @param context .
     */
    public void startup(RuntimeContext context) throws Exception
    {
        this.context = context;

        context.addShutdownListener(this);
    }
}
