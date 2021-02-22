package org.shiftone.jrat.jda.config;



import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * Class JRatConfig
 *
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 */
public class JRatConfig
{

    private static final Log LOG = LogFactory.getLogger(JRatConfig.class);
    private List             vms = new ArrayList();

    /**
     * Method createVm
     */
    public VMTraceConfig createVm()
    {

        VMTraceConfig virtualMachineConfig = new VMTraceConfig();

        vms.add(virtualMachineConfig);

        return virtualMachineConfig;
    }

    /**
     * Method getVirtualMachineConfigs
     */
    public VMTraceConfig[] getVirtualMachineConfigs()
    {

        VMTraceConfig[] configs = new VMTraceConfig[vms.size()];

        for (int i = 0; i < configs.length; i++)
        {
            configs[i] = (VMTraceConfig) vms.get(i);
        }

        return configs;
    }
}
