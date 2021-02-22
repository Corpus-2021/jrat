package org.shiftone.jrat.test;

import junit.framework.TestCase;

import org.shiftone.jrat.proxy.jdbc.ProxyXADataSource;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import javax.sql.XAConnection;


/**
 * Class ProxyXADataSourceTestCase
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.12 $
 */
public class ProxyXADataSourceTestCase extends TestCase {

    private static final Log LOG = LogFactory.getLogger(ProxyXADataSourceTestCase.class);

    /**
     * Method testNewProxyXADataSource
     *
     * @throws Exception
     */
    public void _testNewProxyXADataSource() throws Exception {

        XAConnection      xaConnection      = null;
        ProxyXADataSource proxyXADataSource = null;

        proxyXADataSource = new ProxyXADataSource();

        proxyXADataSource.setTargetDriver("oracle.jdbc.xa.client.OracleXADataSource");
        proxyXADataSource.setTargetProperties("user=mcmapp|url=jdbc:oracle:oci8:@CMS-APD|password=mcmapp|dataSourceName=CMS XA Connection Pool");

        xaConnection = proxyXADataSource.getXAConnection();

    }

    /**
     * Method testNewProxyXADataSource2
     *
     * @throws Exception
     */
    public void _testNewProxyXADataSource2() throws Exception {

        XAConnection      xaConnection      = null;
        ProxyXADataSource proxyXADataSource = null;

        proxyXADataSource = new ProxyXADataSource();

        proxyXADataSource.setTargetDriver("oracle.jdbc.xa.client.OracleXADataSource");
        proxyXADataSource.setUser("mcmapp");
        proxyXADataSource.setURL("jdbc:oracle:oci8:@CMS-APD");
        proxyXADataSource.setPassword("mcmapp");
        proxyXADataSource.setDataSourceName("CMS XA Connection Pool");

        xaConnection = proxyXADataSource.getXAConnection();

    }

}
