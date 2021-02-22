package org.shiftone.jrat.jda.config.filter;



import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;


/**
 * Class FilterConfig
 *
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 */
public class FilterConfig
{

    private static final Log LOG     = LogFactory.getLogger(FilterConfig.class);
    private String           include = null;
    private String           exclude = null;

    /**
     * Method getExclude
     */
    public String getExclude()
    {
        return exclude;
    }

    /**
     * Method setExclude
     */
    public void setExclude(String exclude)
    {
        this.exclude = exclude;
    }

    /**
     * Method getInclude
     */
    public String getInclude()
    {
        return include;
    }

    /**
     * Method setInclude
     */
    public void setInclude(String include)
    {
        this.include = include;
    }
}
