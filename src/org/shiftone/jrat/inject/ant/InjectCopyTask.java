package org.shiftone.jrat.inject.ant;



import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Copy;
import org.apache.tools.ant.types.FilterSet;
import org.apache.tools.ant.types.FilterSetCollection;

import org.shiftone.jrat.inject.Injector;
import org.shiftone.jrat.inject.criteria.InjectionCriteria;
import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.io.File;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;


/**
 * Apache Ant Task that extends the common Copy task.
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.11 $
 */
public class InjectCopyTask extends Copy
{

    private static final Log  LOG      = LogFactory.getLogger(InjectCopyTask.class);
    private InjectionCriteria criteria = new InjectionCriteria();
    private Injector          injector = null;

    /**
     * Method copyOrInjectFile
     *
     * @throws Exception
     */
    private void copyOrInjectFile(String fromFile, String toFile, FilterSetCollection executionFilters) throws Exception
    {

        if (fromFile.endsWith(".class") || (fromFile.endsWith(".jar")))
        {
            injector.inject(fromFile, toFile);
        }
        else
        {
            if (fromFile.equals(toFile))
            {
                log("Skipping self-copy of " + fromFile, verbosity);
            }
            else
            {
                log("Sopying " + fromFile, verbosity);
                getFileUtils().copyFile(     //
                    fromFile,                //
                    toFile,                  //
                    executionFilters,        // token filtering
                    getFilterChains(),       // token filtering
                    forceOverwrite,          //
                    preserveLastModified,    //
                    getEncoding(),           //
                    getProject());
            }
        }
    }

    /**
     * Actually does the file (and possibly empty directory) copies.
     * This is a good method for subclasses to override.
     */
    protected void doFileOperations()
    {

        Iterator sourceFiles    = null;
        Map      orderedCopyMap = new TreeMap(fileCopyMap);

        if (orderedCopyMap.size() > 0)
        {
            log("Copying " + orderedCopyMap.size() + " file" + ((orderedCopyMap.size() == 1)
                                                                ? ""
                                                                : "s") + " to " + destDir.getAbsolutePath());

            sourceFiles = orderedCopyMap.keySet().iterator();
            injector    = new Injector();

            injector.setInjectionCriteria(criteria);
            injector.setForceOverwrite(forceOverwrite);
            injector.setPreserveLastModified(preserveLastModified);

            while (sourceFiles.hasNext())
            {
                String fromFile = (String) sourceFiles.next();
                String toFile   = (String) orderedCopyMap.get(fromFile);

                log(fromFile);

                try
                {
                    FilterSetCollection executionFilters = new FilterSetCollection();

                    if (filtering)
                    {
                        executionFilters.addFilterSet(getProject().getGlobalFilterSet());
                    }

                    for (Enumeration filterEnum = getFilterSets().elements(); filterEnum.hasMoreElements(); )
                    {
                        executionFilters.addFilterSet((FilterSet) filterEnum.nextElement());
                    }

                    copyOrInjectFile(fromFile, toFile, executionFilters);
                }
                catch (Exception e)
                {
                    LOG.error("InjectCopyTask error", e);

                    throw new BuildException("Error instramenting " + fromFile, e, getLocation());
                }
            }
        }

        if (includeEmpty)
        {
            copyEmptyDirectories();
        }
    }

    /**
     * Method copyEmptyDirectories
     */
    private void copyEmptyDirectories()
    {

        Enumeration e     = dirCopyMap.elements();
        int         count = 0;

        while (e.hasMoreElements())
        {
            File d = new File((String) e.nextElement());

            if (!d.exists())
            {
                if (!d.mkdirs())
                {
                    log("Unable to create directory " + d.getAbsolutePath(), Project.MSG_ERR);
                }
                else
                {
                    count++;
                }
            }
        }

        if (count > 0)
        {
            log("Copied " + count + " empty director" + ((count == 1)
                                                         ? "y"
                                                         : "ies") + " to " + destDir.getAbsolutePath());
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
