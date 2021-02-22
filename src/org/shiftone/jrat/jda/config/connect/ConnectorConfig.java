package org.shiftone.jrat.jda.config.connect;



import com.sun.jdi.VirtualMachine;
import com.sun.jdi.VirtualMachineManager;
import com.sun.jdi.connect.Connector;

import org.shiftone.jrat.jda.TraceException;
import org.shiftone.jrat.jda.config.TraceConfigException;
import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Class ConnectorConfig
 *
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 */
public abstract class ConnectorConfig
{

    private static final Log LOG       = LogFactory.getLogger(ConnectorConfig.class);
    private List             arguments = new ArrayList();
    private String           name      = null;

    /**
     * Method setName
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Method getName
     */
    public String getName()
    {
        return name;
    }

    /**
     * Method createArgument
     */
    public ArgumentConfig createArgument()
    {

        ArgumentConfig arg = new ArgumentConfig();

        arguments.add(arg);

        return arg;
    }

    /**
     * Method connect
     *
     * @throws TraceException
     */
    public abstract VirtualMachine connect(VirtualMachineManager vmm) throws TraceException;

    /**
     * Method pickConnector
     *
     * @throws TraceConfigException
     */
    protected Connector pickConnector(List connectors) throws TraceConfigException
    {

        Connector connector = null;

        for (int i = 0; i < connectors.size(); i++)
        {
            connector = (Connector) connectors.get(i);

            LOG.debug(connector.name() + " " + connector.getClass().getName());

            if (connector.name().equalsIgnoreCase(name))
            {
                return connector;
            }
        }

        throw new TraceConfigException("Appropriate connector not found : " + name);
    }

    /**
     * Method getConnectorArguments
     *
     * @throws TraceConfigException
     */
    protected Map getConnectorArguments(Connector connector) throws TraceConfigException
    {

        Map                map          = connector.defaultArguments();
        Connector.Argument value        = null;
        ArgumentConfig     arg          = null;
        boolean            argSupported = false;

        for (int i = 0; i < arguments.size(); i++)
        {
            arg          = (ArgumentConfig) arguments.get(i);
            argSupported = map.containsKey(arg.getName());

            if (argSupported)
            {
                value = (Connector.Argument) map.get(arg.getName());

                if (value.isValid(arg.getValue()))
                {
                    value.setValue(arg.getValue());
                    map.put(arg.getName(), value);
                }
                else
                {
                    throw new TraceConfigException("the value '" + value + "' is not valid for argument '" + arg.getName() + "' to "
                                                   + connector.name());
                }
            }
            else
            {
                throw new TraceConfigException(connector.name() + " does not support the argument '" + arg.getName() + "'");
            }
        }

        return map;
    }
}
