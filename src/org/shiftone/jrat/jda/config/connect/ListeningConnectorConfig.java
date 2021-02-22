package org.shiftone.jrat.jda.config.connect;



import com.sun.jdi.VirtualMachine;
import com.sun.jdi.VirtualMachineManager;
import com.sun.jdi.connect.ListeningConnector;

import org.shiftone.jrat.jda.TraceException;
import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.util.List;


/**
 * Class ListeningConnectorConfig
 *
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 */
public class ListeningConnectorConfig extends ConnectorConfig
{

    private static final Log LOG = LogFactory.getLogger(ListeningConnectorConfig.class);

    /**
     * Method connect
     *
     * @throws TraceException
     */
    public VirtualMachine connect(VirtualMachineManager vmm) throws TraceException
    {

        VirtualMachine     vm         = null;
        ListeningConnector connector  = null;
        List               connectors = null;

        try
        {
            connectors = vmm.listeningConnectors();
            connector  = (ListeningConnector) pickConnector(connectors);
            vm         = connector.accept(getConnectorArguments(connector));
        }
        catch (TraceException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new TraceException("error accepting connection from VM", e);
        }

        return vm;
    }
}
