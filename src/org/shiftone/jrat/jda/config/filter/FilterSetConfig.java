
/*
 * User: jeff
 */
package org.shiftone.jrat.jda.config.filter;



import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * Class FilterSetConfig
 *
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 */
public class FilterSetConfig
{

    private static final Log LOG        = LogFactory.getLogger(FilterSetConfig.class);
    private List             filterList = new ArrayList();

    /**
     * Method createFilter
     */
    public FilterConfig createFilter()
    {

        FilterConfig filterConfig = new FilterConfig();

        filterList.add(filterConfig);

        return filterConfig;
    }

    /**
     * Method getFilterConfigs
     */
    public FilterConfig[] getFilterConfigs()
    {

        FilterConfig[] filterConfigs = new FilterConfig[filterList.size()];

        for (int i = 0; i < filterConfigs.length; i++)
        {
            filterConfigs[i] = (FilterConfig) filterList.get(i);
        }

        return filterConfigs;
    }
}
