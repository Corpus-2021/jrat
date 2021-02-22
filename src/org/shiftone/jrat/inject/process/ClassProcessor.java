package org.shiftone.jrat.inject.process;



import org.shiftone.jrat.inject.InjectionException;
import org.shiftone.jrat.inject.Injector;
import org.shiftone.jrat.util.IOUtil;
import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * Class ClassProcessor
 *
 * @author $Author: jeffdrost $
 * @version $Revision: 1.2 $
 */
public class ClassProcessor extends AbstractProcessor
{

    private static final Log LOG = LogFactory.getLogger(ClassProcessor.class);

    /**
     * Method processStreams
     *
     * @throws IOException
     * @throws InjectionException
     */
    protected boolean processStream(Injector injector, InputStream inputStream, OutputStream outputStream, String fileName)
            throws InjectionException, IOException
    {

        byte[] newClassDate = null;

        newClassDate = injector.injectStream(inputStream, fileName);

        outputStream.write(newClassDate);
        IOUtil.close(outputStream);
        IOUtil.close(inputStream);

        return true;
    }
}
