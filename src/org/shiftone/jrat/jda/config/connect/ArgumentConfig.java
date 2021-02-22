package org.shiftone.jrat.jda.config.connect;



import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;


/**
 * Class ArgumentConfig
 *
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 */
public class ArgumentConfig
{

    private static final Log LOG   = LogFactory.getLogger(ArgumentConfig.class);
    private String           name  = null;
    private String           value = null;

    /**
     * Method getName
     */
    public String getName()
    {
        return name;
    }

    /**
     * Method setName
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Method getValue
     */
    public String getValue()
    {
        return value;
    }

    /**
     * Method setValue
     */
    public void setValue(String value)
    {
        this.value = value;
    }
}
