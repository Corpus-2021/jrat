package org.shiftone.jrat.jda.config;



import org.shiftone.jrat.jda.config.connect.AttachingConnectorConfig;
import org.shiftone.jrat.jda.config.connect.ConnectorConfig;
import org.shiftone.jrat.jda.config.connect.LaunchingConnectorConfig;
import org.shiftone.jrat.jda.config.connect.ListeningConnectorConfig;
import org.shiftone.jrat.jda.config.filter.FilterConfig;
import org.shiftone.jrat.jda.config.filter.FilterSetConfig;
import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;


/**
 * Class VMTraceConfig
 *
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 */
public class VMTraceConfig
{

    private static final Log LOG             = LogFactory.getLogger(VMTraceConfig.class);
    private FilterSetConfig  filterSetConfig = new FilterSetConfig();
    private ConnectorConfig  connectorConfig = null;
    private String           title           = null;

    /**
     * Method assertNoConnector
     *
     * @throws TraceConfigException
     */
    private void assertNoConnector() throws TraceConfigException
    {

        if (connectorConfig != null)
        {
            throw new TraceConfigException("only one connector can be defined per VM");
        }
    }

    /**
     * Method createLaunchingConnector
     *
     * @throws TraceConfigException
     */
    public ConnectorConfig createLaunchingConnector() throws TraceConfigException
    {

        assertNoConnector();

        connectorConfig = new LaunchingConnectorConfig();

        return connectorConfig;
    }

    /**
     * Method createAttachingConnector
     *
     * @throws TraceConfigException
     */
    public ConnectorConfig createAttachingConnector() throws TraceConfigException
    {

        assertNoConnector();

        connectorConfig = new AttachingConnectorConfig();

        return connectorConfig;
    }

    /**
     * Method createListeningConnector
     *
     * @throws TraceConfigException
     */
    public ConnectorConfig createListeningConnector() throws TraceConfigException
    {

        assertNoConnector();

        connectorConfig = new ListeningConnectorConfig();

        return connectorConfig;
    }

    /**
     * Method createFilters
     */
    public FilterSetConfig createFilters()
    {
        return filterSetConfig;
    }

    /**
     * Method getConnectorConfig
     */
    public ConnectorConfig getConnectorConfig()
    {
        return connectorConfig;
    }

    /**
     * Method getFilterConfigs
     */
    public FilterConfig[] getFilterConfigs()
    {
        return filterSetConfig.getFilterConfigs();
    }

    /**
     * Method getTitle
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * Method setTitle
     */
    public void setTitle(String title)
    {
        this.title = title;
    }
}
