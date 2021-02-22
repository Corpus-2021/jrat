package org.shiftone.jrat.ui.inject;



import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.shiftone.jrat.ui.UIConstants;
import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;


/**
 * Class InjectRunnable
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.5 $
 */
public class InjectRunnable implements Runnable, UIConstants
{

    private static final Log LOG     = LogFactory.getLogger(InjectRunnable.class);
    private File[]           targets = null;
    private InjectionManager im      = null;

    /**
     * Constructor InjectRunnable
     *
     *
     * @param im
     * @param targets
     */
    InjectRunnable(InjectionManager im, File[] targets)
    {
        this.targets = targets;
        this.im      = im;
    }

    /**
     * Constructor InjectRunnable
     *
     *
     * @param im
     * @param target
     */
    InjectRunnable(InjectionManager im, File target)
    {
        this.im      = im;
        this.targets = new File[]{ target };
    }

    /**
     * Method run
     */
    public void run()
    {

        Set      fileSet  = new HashSet();
        Iterator iterator = null;

        for (int i = 0; i < targets.length; i++)
        {
            LOG.info("scanning " + targets[i] + "...");
            scan(targets[i], fileSet);
        }

        LOG.info("done scanning : " + fileSet.size() + " files found");

        iterator = fileSet.iterator();

        while (iterator.hasNext())
        {
            File f = (File) iterator.next();

            try
            {
                im.inject(f);
            }
            catch (Exception e)
            {
                throw new RuntimeException("Error injecting file : " + f, e);
            }
        }
    }

    /**
     * Method scan
     */
    private void scan(File file, Set fileSet)
    {

        if (file.isDirectory())
        {
            File[] files = file.listFiles(INJECT_FILE_FILTER);

            for (int i = 0; i < files.length; i++)
            {
                scan(files[i], fileSet);
            }
        }
        else if (INJECT_FILE_FILTER.accept(file))
        {
            fileSet.add(file);
        }
    }
}
