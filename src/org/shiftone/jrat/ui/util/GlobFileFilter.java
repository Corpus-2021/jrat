package org.shiftone.jrat.ui.util;



import org.shiftone.jrat.util.GlobMatcher;
import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.io.File;


/**
 * Class GlobFileFilter
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.3 $
 */
public class GlobFileFilter extends javax.swing.filechooser.FileFilter implements java.io.FileFilter
{

    private static final Log LOG          = LogFactory.getLogger(GlobFileFilter.class);
    private GlobMatcher[]    globMatchers = null;
    private String           description  = null;

    /**
     * Constructor GlobFileFilter
     *
     *
     * @param globPatterns
     * @param description
     */
    public GlobFileFilter(String[] globPatterns, String description)
    {

        StringBuffer sb = new StringBuffer(" (");

        this.globMatchers = new GlobMatcher[globPatterns.length];

        for (int i = 0; i < globPatterns.length; i++)
        {
            globMatchers[i] = new GlobMatcher(globPatterns[i]);

            if (i != 0)
            {
                sb.append(", ");
            }

            sb.append(globPatterns[i]);
        }

        sb.append(")");

        this.description = description + sb.toString();
    }

    /**
     * Method accept
     */
    public boolean accept(File f)
    {

        if (f.isDirectory())
        {
            return true;
        }

        for (int i = 0; i < globMatchers.length; i++)
        {
            if (globMatchers[i].isMatch(f.getName()))
            {
                return true;
            }
        }

        return false;
    }

    /**
     * Method getDescription
     */
    public String getDescription()
    {
        return description;
    }
}
