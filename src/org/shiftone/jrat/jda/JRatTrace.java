package org.shiftone.jrat.jda;

import com.sun.jdi.Bootstrap;
import com.sun.jdi.VirtualMachineManager;

import org.shiftone.arbor.Arbor;

import org.shiftone.jrat.jda.config.JRatConfig;
import org.shiftone.jrat.jda.config.VMTraceConfig;

import org.shiftone.jrat.jda.config.connect.ConnectorConfig;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.io.FileInputStream;
import java.io.InputStream;


/**
 * Class JRatTrace
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 */
public class JRatTrace {

    private static final Log LOG = LogFactory.getLogger(JRatTrace.class);

    /**
     * Method loadConfig
     *
     * @return .
     *
     * @throws Exception
     */
    private JRatConfig loadConfig() throws Exception {

        JRatConfig  config      = new JRatConfig();
        InputStream inputStream = new FileInputStream("chain/jrat-jda.xml");
        Arbor       arbor       = new Arbor();

        arbor.process(config, inputStream);
        LOG.info("configuration complete.");

        return config;

    }

    /**
     * Method begin
     */
    public void begin() {

        VirtualMachineManager vmm    = Bootstrap.virtualMachineManager();
        JRatConfig            config = null;
        VMTraceConfig[]       vmc    = null;
        ConnectorConfig       cc     = null;

        try {

            config     = loadConfig();
            vmc        = config.getVirtualMachineConfigs();

            for (int i = 0; i < vmc.length; i++) {

                new VMTracer(vmc[i]).trace();

            }

        } catch (Exception e) {

            LOG.error("Trace Failed", e);

        }

        LOG.error("DONE.");

    }

    /**
     * Method main
     *
     * @param args .
     */
    public static void main(String[] args) {

        JRatTrace trace = new JRatTrace();

        trace.begin();

    }

}
