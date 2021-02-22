package org.shiftone.jrat.inject.process;



import org.shiftone.jrat.inject.InjectionException;
import org.shiftone.jrat.inject.Injector;
import org.shiftone.jrat.util.IOUtil;
import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * Class AbstractProcessor
 *
 * @author $Author: jeffdrost $
 * @version $Revision: 1.3 $
 */
public abstract class AbstractProcessor implements Processor
{

    private static final Log  LOG                  = LogFactory.getLogger(AbstractProcessor.class);
    private static final long DEFAULT_BUFFER_SIZE  = 1024 * 6;
    private boolean           forceOverwrite       = true;    //false;
    private boolean           overwriteNewer       = false;
    private boolean           preserveLastModified = false;

    /**
     * Method process
     *
     * @throws IOException
     * @throws InjectionException
     */
    public boolean process(Injector injector, File source, File target) throws InjectionException, IOException
    {

        boolean worked = false;
        long    lastModified;

        if (!source.exists())
        {
            throw new IOException("source file does not exist : " + source);
        }

        if (source.isDirectory())
        {
            throw new IOException("source file is a directory : " + source);
        }

        if (source.canRead() == false)
        {
            throw new IOException("source file can not be read (check permissions): " + source);
        }

        lastModified = source.lastModified();

        if (target.exists())
        {
            if (forceOverwrite == false)
            {
                LOG.debug("target exists and forceOverwrite is disabled : " + source);

                return false;
            }

            if (target.isDirectory())
            {
                throw new IOException("target is directory : " + target);
            }

            if (target.canWrite() == false)
            {
                throw new IOException("unable to write to target (check permissions) : " + target);
            }

            // newer is bigger
            if (target.lastModified() > source.lastModified())
            {

                // target is newer than source
                if (!overwriteNewer)
                {
                    LOG.info("target is newer than source and overwriteNewer is disabled : " + source);

                    return false;
                }
            }

            worked = processUsingSwapFile(injector, source, target);
        }
        else
        {
            worked = processFile(injector, source, target);
        }

        if (preserveLastModified)
        {
            target.setLastModified(lastModified);
        }

        return worked;
    }

    /**
     * Method processUsingSwapFile
     *
     * @throws IOException
     * @throws InjectionException
     */
    protected boolean processUsingSwapFile(Injector injector, File source, File target) throws InjectionException, IOException
    {

        boolean worked   = false;
        File    workFile = new File(target.getAbsolutePath() + Injector.WORK_FILE_END);

        if (workFile.exists())
        {
            LOG.info("workfile found, deleting");
            IOUtil.delete(workFile);
        }

        try
        {
            worked = processFile(injector, source, workFile);

            if ((worked == true) && (workFile.exists() == false))
            {
                throw new InjectionException("processFile claims to have worked, but target file doesn't exist : " + source);
            }

            if ((worked == false) && (workFile.exists() == true))
            {
                throw new InjectionException("processFile caims to have failed, but workfile exists : " + workFile);
            }

            if (worked)
            {
                IOUtil.rename(workFile, target, true);
            }
        }
        catch (Exception e)
        {
            String msg = "Failed to instrument " + source;

            if ((workFile.exists()) && (!workFile.delete()))
            {
                msg += " and couldn't delete the corrupt file " + workFile.getAbsolutePath();
            }

            throw new InjectionException(msg, e);
        }

        return worked;
    }

    /**
     * Method processFile
     *
     * @throws IOException
     * @throws InjectionException
     */
    protected boolean processFile(Injector injector, File source, File target) throws InjectionException, IOException
    {

        InputStream  inputStream  = null;
        OutputStream outputStream = null;
        int          bufferSize   = (int) Math.min(DEFAULT_BUFFER_SIZE, source.length());

        inputStream  = new BufferedInputStream(new FileInputStream(source), bufferSize);
        outputStream = new BufferedOutputStream(new FileOutputStream(target), bufferSize);

        return processStream(injector, inputStream, outputStream, source.getName());
    }

    /**
     * Method processStreams
     *
     * @throws IOException
     * @throws InjectionException
     */
    protected boolean processStream(Injector injector, InputStream inputStream, OutputStream outputStream, String fileName)
            throws InjectionException, IOException
    {
        throw new UnsupportedOperationException("processStream should be implemented by derived class");
    }
}
