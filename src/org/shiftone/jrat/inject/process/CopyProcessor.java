package org.shiftone.jrat.inject.process;



import org.shiftone.jrat.inject.Injector;
import org.shiftone.jrat.util.IOUtil;
import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.io.File;
import java.io.IOException;


/**
 * Class CopyProcessor
 *
 *
 * @author Jeff Drost
 */
public class CopyProcessor extends AbstractProcessor
{

    private static final Log LOG = LogFactory.getLogger(CopyProcessor.class);

    /**
     * Method process
     *
     * @throws IOException
     */
    public boolean processFile(Injector injector, File source, File target) throws IOException
    {
        return IOUtil.copy(source, target);
    }
}
