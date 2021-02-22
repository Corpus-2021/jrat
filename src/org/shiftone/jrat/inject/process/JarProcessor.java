package org.shiftone.jrat.inject.process;



import org.shiftone.jrat.inject.InjectionException;
import org.shiftone.jrat.inject.Injector;
import org.shiftone.jrat.util.IOUtil;
import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;


/**
 * Class JarProcessor
 *
 *
 * @author Jeff Drost
 */
public class JarProcessor extends AbstractProcessor
{

    private static final Log LOG = LogFactory.getLogger(JarProcessor.class);

    /**
     * Method injectJar
     *
     *
     * @throws InjectionException
     */
    public boolean processStream(Injector injector, InputStream source, OutputStream target, String fileName) throws InjectionException
    {

        ZipInputStream  zipInputStream   = null;
        InputStream     entryInputStream = null;
        ZipOutputStream zipOutputStream  = null;
        ZipEntry        inEntry          = null;
        ZipEntry        outEntry         = null;

        try
        {
            zipOutputStream = new ZipOutputStream(target);
            zipInputStream  = new ZipInputStream(source);

            while ((inEntry = zipInputStream.getNextEntry()) != null)
            {
                LOG.info("injecting Jar entry : " + inEntry.getName());

                outEntry         = new ZipEntry(inEntry.getName());
                entryInputStream = new OpenInputStream(zipInputStream);

                if (inEntry.getName().endsWith(".class"))
                {
                    byte[] classData = injector.injectStream(entryInputStream, outEntry.getName());

                    entryInputStream = new ByteArrayInputStream(classData);
                }

                zipOutputStream.putNextEntry(outEntry);
                IOUtil.copy(entryInputStream, zipOutputStream);
                zipOutputStream.closeEntry();
            }

            // zipOutputStream.close();
        }
        catch (Exception e)
        {
            throw new InjectionException("Jar injection error", e);
        }
        finally
        {
            IOUtil.close(zipOutputStream);
            IOUtil.close(target);
            IOUtil.close(zipInputStream);
            IOUtil.close(source);
        }

        return true;
    }
}
