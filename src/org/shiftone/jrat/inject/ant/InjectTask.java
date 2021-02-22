package org.shiftone.jrat.inject.ant;



import org.apache.tools.ant.BuildException;

import org.shiftone.jrat.inject.Injector;
import org.shiftone.jrat.inject.criteria.InjectionCriteria;

import java.io.File;


/**
 * Class InjectTask
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.9 $
 */
public class InjectTask extends AbstractFileTask
{

    private Injector          injector = null;
    private InjectionCriteria criteria = new InjectionCriteria();

    /**
     * Method getInjector
     */
    private Injector getInjector()
    {

        if (injector == null)
        {
            injector = new Injector();

            injector.setForceOverwrite(true);
            injector.setInjectionCriteria(criteria);
        }

        return injector;
    }

    /**
     * Method processFile
     *
     * @throws BuildException
     */
    protected void processFile(File file) throws BuildException
    {

        log("processFile(" + file.getAbsolutePath() + ")");

        try
        {
            getInjector().inject(file);
        }
        catch (Exception e)
        {
            throw new BuildException(e);
        }
    }

    /**
     * Method validateFile
     *
     * @throws BuildException
     */
    protected void validateFile(File file) throws BuildException
    {

        if (file.exists() == false)
        {
            throw new BuildException("File does not exist : " + file.getAbsolutePath());
        }
    }

    /**
     * Method createCriteria
     */
    public InjectionCriteria createCriteria()
    {
        return criteria;
    }
}
