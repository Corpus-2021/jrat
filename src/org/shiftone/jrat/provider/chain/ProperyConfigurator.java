package org.shiftone.jrat.provider.chain;



import org.shiftone.jrat.core.ConfigurationException;
import org.shiftone.jrat.core.spi.MethodHandlerFactory;
import org.shiftone.jrat.util.PropertiesTree;
import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.io.InputStream;

import java.util.Properties;


/**
 * Class ProperyConfigurator
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.8 $
 */
public class ProperyConfigurator
{

    private static final Log          LOG     = LogFactory.getLogger(ProperyConfigurator.class);
    private ChainMethodHandlerFactory factory = null;

    /**
     * Constructor ProperyConfigurator
     *
     *
     * @param factory
     */
    public ProperyConfigurator(ChainMethodHandlerFactory factory)
    {
        this.factory = factory;
    }

    /**
     * Method configure
     *
     * @throws ConfigurationException
     */
    public void configure(Properties props) throws ConfigurationException
    {

        PropertiesTree      tree     = new PropertiesTree(props);
        PropertiesTree.Node root     = tree.getRoot();
        String              handlers = root.getNode("handlers").getValue();
    }

    /**
     * Method buildFactory
     *
     * @throws ConfigurationException
     */
    public static MethodHandlerFactory buildFactory(InputStream inputStream) throws ConfigurationException
    {

        Properties                props        = new Properties();
        ChainMethodHandlerFactory factory      = null;
        ProperyConfigurator       configurator = null;

        try
        {
            props.load(inputStream);

            factory      = new ChainMethodHandlerFactory();
            configurator = new ProperyConfigurator(factory);

            configurator.configure(props);
        }
        catch (Exception e)
        {
            throw new ConfigurationException("error building property file configured factory", e);
        }

        return null;
    }
}
