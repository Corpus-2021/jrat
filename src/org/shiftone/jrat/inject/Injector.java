package org.shiftone.jrat.inject;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;

import org.shiftone.jrat.inject.criteria.InjectionCriteria;

import org.shiftone.jrat.inject.process.ClassProcessor;
import org.shiftone.jrat.inject.process.CopyProcessor;
import org.shiftone.jrat.inject.process.JarProcessor;
import org.shiftone.jrat.inject.process.Processor;

import org.shiftone.jrat.util.IOUtil;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;


/**
 * Class Processor
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.22 $
 */
public class Injector {

    private static final Log   LOG                  = LogFactory.getLogger(Injector.class);
    public static final String WORK_FILE_END        = "-JRatWorkFile";
    private boolean            forceOverwrite       = false;
    private boolean            overwriteNewer       = false;
    private boolean            preserveLastModified = false;
    private InjectionCriteria  injectionCriteria    = new InjectionCriteria();

    /**
     * Method callClassInjector
     *
     * @param inputStream .
     * @param fileName .
     *
     * @return .
     *
     * @throws IOException
     * @throws ClassFormatError
     */

    /*
       public static byte[] injectStream(InputStream inputStream, String fileName, InjectionCriteria injectionCriteria) throws IOException, ClassFormatError
       {
           JavaClass   javaClass   = null;
           ClassParser classParser = null;
           classParser = new ClassParser(inputStream, fileName);
           javaClass   = classParser.parse();
           javaClass   = ClassInjector.injectClass(javaClass, injectionCriteria);
           return javaClass.getBytes();
       }
     */
    public byte[] injectStream(InputStream inputStream, String fileName)
        throws IOException, ClassFormatError {

        JavaClass   javaClass   = null;
        ClassParser classParser = null;

        classParser     = new ClassParser(inputStream, fileName);
        javaClass       = classParser.parse();
        javaClass       = ClassInjector.injectClass(javaClass, injectionCriteria);

        return javaClass.getBytes();

    }

    /**
     * Method inject
     *
     * @param sourceFile .
     * @param targetFile .
     *
     * @throws InjectionException
     */
    public void inject(File sourceFile, File targetFile)
        throws InjectionException {

        Processor processor = null;
        String    sourceExt = IOUtil.getExtention(sourceFile);
        String    targetExt = IOUtil.getExtention(targetFile);

        if (sourceExt.equals(targetExt) == false) {

            throw new InjectionException("source and target file extention are not the same");

        }

        processor = getCorrectProcessor(sourceExt);

        try {

            processor.process(this, sourceFile, targetFile);

        } catch (IOException e) {

            throw new InjectionException("injection failed do to file access error\n" + sourceFile + " -> " + targetFile, e);

        }

    }

    /**
     * Method getCorrectProcessor
     *
     * @param fileExt .
     *
     * @return .
     */
    private Processor getCorrectProcessor(String fileExt) {

        Processor processor = null;

        if (fileExt.equalsIgnoreCase("class")) {

            processor = new ClassProcessor();

        } else if (fileExt.equalsIgnoreCase("jar")) {

            processor = new JarProcessor();

        } else {

            processor = new CopyProcessor();

        }

        return processor;

    }

    /**
     * Method inject
     *
     * @param source .
     * @param target .
     *
     * @throws InjectionException
     */
    public void inject(String source, String target) throws InjectionException {

        inject(new File(source), new File(target));

    }

    /**
     * Method inject - inject a file IN PLACE
     *
     * @param file .
     *
     * @throws InjectionException
     */
    public void inject(File file) throws InjectionException {

        if (file.getName().endsWith(Injector.WORK_FILE_END)) {

            try {

                IOUtil.delete(file);

            } catch (IOException e) {

                LOG.warn("unable to delete : " + file);

            }

        } else {

            inject(file, file);

        }

    }

    /**
     * Method inject - inject a file IN PLACE
     *
     * @param fileName .
     *
     * @throws InjectionException
     */
    public void inject(String fileName) throws InjectionException {

        inject(new File(fileName));

    }

    /**
     * Method isBCELPresent tests that BCEL is avalible
     *
     * @return .
     */
    public boolean isBCELPresent() {

        try {

            Class.forName("org.apache.bcel.Repository");

            return true;

        } catch (Exception e) {

        }

        return false;

    }

    // ------------------------------------------------------------------------------

    /**
     * Method getInjectionCriteria
     *
     * @return .
     */
    public InjectionCriteria getInjectionCriteria() {

        return injectionCriteria;

    }

    /**
     * Method setInjectionCriteria
     *
     * @param injectionCriteria .
     */
    public void setInjectionCriteria(InjectionCriteria injectionCriteria) {

        this.injectionCriteria = injectionCriteria;

    }

    /**
     * Method isForceOverwrite
     *
     * @return .
     */
    public boolean isForceOverwrite() {

        return forceOverwrite;

    }

    /**
     * Method setForceOverwrite
     *
     * @param forceOverwrite .
     */
    public void setForceOverwrite(boolean forceOverwrite) {

        this.forceOverwrite = forceOverwrite;

    }

    /**
     * Method isOverwriteNewer
     *
     * @return .
     */
    public boolean isOverwriteNewer() {

        return overwriteNewer;

    }

    /**
     * Method setOverwriteNewer
     *
     * @param overwriteNewer .
     */
    public void setOverwriteNewer(boolean overwriteNewer) {

        this.overwriteNewer = overwriteNewer;

    }

    /**
     * Method isPreserveLastModified
     *
     * @return .
     */
    public boolean isPreserveLastModified() {

        return preserveLastModified;

    }

    /**
     * Method setPreserveLastModified
     *
     * @param preserveLastModified .
     */
    public void setPreserveLastModified(boolean preserveLastModified) {

        this.preserveLastModified = preserveLastModified;

    }

}
