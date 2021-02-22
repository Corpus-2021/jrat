package org.shiftone.jrat.jda.config.connect;



import com.sun.jdi.VirtualMachine;
import com.sun.jdi.VirtualMachineManager;
import com.sun.jdi.connect.AttachingConnector;

import org.shiftone.jrat.jda.TraceException;
import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.util.List;


/**
 * Class AttachingConnectorConfig
 *
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 */
public class AttachingConnectorConfig extends ConnectorConfig
{

    private static final Log LOG = LogFactory.getLogger(AttachingConnectorConfig.class);

    /**
     * Method connect
     *
     * @throws TraceException
     */
    public VirtualMachine connect(VirtualMachineManager vmm) throws TraceException
    {

        VirtualMachine     vm         = null;
        AttachingConnector connector  = null;
        List               connectors = null;

        try
        {
            connectors = vmm.attachingConnectors();
            connector  = (AttachingConnector) pickConnector(connectors);
            vm         = connector.attach(getConnectorArguments(connector));
        }
        catch (TraceException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new TraceException("error attaching to VM", e);
        }

        return vm;
    }
}
