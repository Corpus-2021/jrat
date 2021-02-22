package org.shiftone.jrat.proxy.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.ConnectionEventListener;
import javax.sql.PooledConnection;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;


/**
 * Class ProxyPooledConnection
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 */
public class ProxyPooledConnection implements PooledConnection {

    private static final Log LOG                    = LogFactory.getLogger(ProxyPooledConnection.class);
    PooledConnection         targetPooledConnection = null;

    /**
     * Constructor ProxyPooledConnection
     *
     * @param targetPooledConnection
     */
    public ProxyPooledConnection(PooledConnection targetPooledConnection) {

        LOG.info("new ProxyPooledConnection");

        this.targetPooledConnection = targetPooledConnection;

    }

    /**
     * Method close
     *
     * @throws SQLException
     */
    public void close() throws SQLException {

        LOG.info("close()");
        targetPooledConnection.close();

    }

    /**
     * Method getConnection
     *
     * @return .
     *
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {

        LOG.info("getConnection()");

        return new ProxyConnection(targetPooledConnection.getConnection());

    }

    /**
     * Method addConnectionEventListener
     *
     * @param listener .
     */
    public void addConnectionEventListener(ConnectionEventListener listener) {

        LOG.info("addConnectionEventListener(.)");
        targetPooledConnection.addConnectionEventListener(listener);

    }

    /**
     * Method removeConnectionEventListener
     *
     * @param listener .
     */
    public void removeConnectionEventListener(ConnectionEventListener listener) {

        LOG.info("removeConnectionEventListener(.)");
        targetPooledConnection.removeConnectionEventListener(listener);

    }

}
