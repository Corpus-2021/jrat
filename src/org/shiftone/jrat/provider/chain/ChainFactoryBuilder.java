package org.shiftone.jrat.provider.chain;



import org.shiftone.jrat.core.ConfigurationException;
import org.shiftone.jrat.core.spi.MethodHandlerFactory;
import org.shiftone.jrat.util.ResourceUtil;
import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;


/**
 * Class ChainFactoryBuilder is the entry point to configure a new ChainMethodHandlerFactory from
 * a resource (xml or properties).
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.3 $
 */
public class ChainFactoryBuilder
{

    private static final Log LOG = LogFactory.getLogger(ChainFactoryBuilder.class);

    /**
     * Delegates to the appropriate configurator based on file extention.
     *
     * @throws ConfigurationException
     */
    public static MethodHandlerFactory buildFactory(String resourceName) throws ConfigurationException
    {

        if ((resourceName == null) || (resourceName.length() == 0))
        {
            throw new ConfigurationException("resource name is null or has zero length");
        }
        else if (resourceName.endsWith(".xml"))
        {
            return SAXConfigurator.buildFactory(ResourceUtil.loadResourceAsStream(resourceName));
        }
        else if (resourceName.endsWith(".properties"))
        {
            return ProperyConfigurator.buildFactory(ResourceUtil.loadResourceAsStream(resourceName));
        }
        else
        {
            throw new ConfigurationException("unknown configuration resource type : " + resourceName);
        }
    }
}
