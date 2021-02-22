package org.shiftone.jrat.proxy.jdbc;

import java.sql.SQLException;

import javax.sql.XAConnection;
import javax.transaction.xa.XAResource;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;


/**
 * Class ProxyXAConnection
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 */
public class ProxyXAConnection extends ProxyPooledConnection implements XAConnection {

    private static final Log LOG                = LogFactory.getLogger(ProxyXAConnection.class);
    XAConnection             targetXAConnection = null;

    /**
     * Constructor ProxyXAConnection
     *
     * @param targetXAConnection
     */
    public ProxyXAConnection(XAConnection targetXAConnection) {
        super(targetXAConnection);

        LOG.info("new ProxyXAConnection");

        this.targetXAConnection = targetXAConnection;

    }

    /**
     * Method getXAResource
     *
     * @return .
     *
     * @throws SQLException
     */
    public XAResource getXAResource() throws SQLException {

        LOG.info("getXAResource()");

        return targetXAConnection.getXAResource();

    }

}
