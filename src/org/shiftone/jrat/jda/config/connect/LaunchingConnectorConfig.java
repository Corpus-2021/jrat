
/*
 * User: jeff
 */
package org.shiftone.jrat.jda.config.connect;



import com.sun.jdi.VirtualMachine;
import com.sun.jdi.VirtualMachineManager;
import com.sun.jdi.connect.LaunchingConnector;

import org.shiftone.jrat.jda.TraceException;
import org.shiftone.jrat.jda.config.connect.pipe.InputOutputPipe;
import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.util.List;


/**
 * Class LaunchingConnectorConfig
 *
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 */
public class LaunchingConnectorConfig extends ConnectorConfig
{

    private static final Log LOG = LogFactory.getLogger(LaunchingConnectorConfig.class);

    /**
     * Method connect
     *
     * @throws TraceException
     */
    public VirtualMachine connect(VirtualMachineManager vmm) throws TraceException
    {

        VirtualMachine     vm         = null;
        Process            process    = null;
        LaunchingConnector connector  = null;
        List               connectors = null;

        try
        {
            connectors = vmm.launchingConnectors();
            connector  = (LaunchingConnector) pickConnector(connectors);
            vm         = connector.launch(getConnectorArguments(connector));

            //process = vm.process();
            new InputOutputPipe(vm.process().getInputStream(), System.out);
            new InputOutputPipe(vm.process().getErrorStream(), System.out);
        }
        catch (TraceException e)
        {
            throw e;
        }
        catch (Exception e)
        {

            //LOG.debug("", e);
            throw new TraceException("error launching VM", e);
        }

        return vm;
    }
}
