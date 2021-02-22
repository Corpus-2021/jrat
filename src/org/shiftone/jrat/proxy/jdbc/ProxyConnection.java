package org.shiftone.jrat.proxy.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.Map;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;


/**
 * Class ProxyConnection
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 */
public class ProxyConnection implements Connection {

    private static final Log LOG              = LogFactory.getLogger(ProxyConnection.class);
    private static int       instanceCounter  = 0;
    private final int        instanceNumber   = (instanceCounter++);
    Connection               targetConnection = null;

    /**
     * Constructor ProxyConnection
     *
     * @param targetConnection
     */
    public ProxyConnection(Connection targetConnection) {

        log("new ProxyConnection");

        this.targetConnection = targetConnection;

    }

    /**
     * Method log
     *
     * @param message .
     */
    void log(String message) {

        LOG.info("C" + instanceNumber + " " + message);

    }

    /**
     * Method clearWarnings
     *
     * @throws SQLException
     */
    public void clearWarnings() throws SQLException {

        log("clearWarnings()");
        targetConnection.clearWarnings();

    }

    /**
     * Method close
     *
     * @throws SQLException
     */
    public void close() throws SQLException {

        log("close()");
        targetConnection.close();

    }

    /**
     * Method commit
     *
     * @throws SQLException
     */
    public void commit() throws SQLException {

        log("commit()");
        targetConnection.commit();

    }

    /**
     * Method createStatement
     *
     * @return .
     *
     * @throws SQLException
     */
    public Statement createStatement() throws SQLException {

        log("createStatement()");

        return new ProxyStatement(targetConnection.createStatement(), this);

    }

    /**
     * Method createStatement
     *
     * @param resultSetType .
     * @param resultSetConcurrency .
     *
     * @return .
     *
     * @throws SQLException
     */
    public Statement createStatement(int resultSetType, int resultSetConcurrency)
        throws SQLException {

        log("createStatement(" + resultSetType + ", " + resultSetConcurrency + ")");

        return new ProxyStatement(targetConnection.createStatement(resultSetType, resultSetConcurrency), this);

    }

    /**
     * Method createStatement
     *
     * @param resultSetType .
     * @param resultSetConcurrency .
     * @param resultSetHoldability .
     *
     * @return .
     *
     * @throws SQLException
     */
    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
        throws SQLException {

        log("createStatement(" + resultSetType + ", " + resultSetConcurrency + ", " + resultSetHoldability + ")");

        return new ProxyStatement(targetConnection.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability), this);

    }

    /**
     * Method getAutoCommit
     *
     * @return .
     *
     * @throws SQLException
     */
    public boolean getAutoCommit() throws SQLException {

        boolean b = targetConnection.getAutoCommit();

        log("getAutoCommit() -> " + b);

        return b;

    }

    /**
     * Method getCatalog
     *
     * @return .
     *
     * @throws SQLException
     */
    public String getCatalog() throws SQLException {

        return targetConnection.getCatalog();

    }

    /**
     * Method getHoldability
     *
     * @return .
     *
     * @throws SQLException
     */
    public int getHoldability() throws SQLException {

        return targetConnection.getHoldability();

    }

    /**
     * Method getMetaData
     *
     * @return .
     *
     * @throws SQLException
     */
    public DatabaseMetaData getMetaData() throws SQLException {

        return targetConnection.getMetaData();

    }

    /**
     * Method getTransactionIsolation
     *
     * @return .
     *
     * @throws SQLException
     */
    public int getTransactionIsolation() throws SQLException {

        return targetConnection.getTransactionIsolation();

    }

    /**
     * Method getTypeMap
     *
     * @return .
     *
     * @throws SQLException
     */
    public Map getTypeMap() throws SQLException {

        return targetConnection.getTypeMap();

    }

    /**
     * Method getWarnings
     *
     * @return .
     *
     * @throws SQLException
     */
    public SQLWarning getWarnings() throws SQLException {

        return targetConnection.getWarnings();

    }

    /**
     * Method isClosed
     *
     * @return .
     *
     * @throws SQLException
     */
    public boolean isClosed() throws SQLException {

        return targetConnection.isClosed();

    }

    /**
     * Method isReadOnly
     *
     * @return .
     *
     * @throws SQLException
     */
    public boolean isReadOnly() throws SQLException {

        return targetConnection.isReadOnly();

    }

    /**
     * Method nativeSQL
     *
     * @param sql .
     *
     * @return .
     *
     * @throws SQLException
     */
    public String nativeSQL(String sql) throws SQLException {

        return targetConnection.nativeSQL(sql);

    }

    /**
     * Method prepareCall
     *
     * @param sql .
     *
     * @return .
     *
     * @throws SQLException
     */
    public CallableStatement prepareCall(String sql) throws SQLException {

        CallableStatement stmt;
        long              finish;
        long              start = 0;

        try {

            start     = System.currentTimeMillis();
            stmt      = targetConnection.prepareCall(sql);

        } finally {

            finish = System.currentTimeMillis();

            log("prepareCall(" + sql + ") took " + (finish - start) + "ms");

        }

        return new ProxyCallableStatement(stmt, this, sql);

    }

    /**
     * Method prepareCall
     *
     * @param sql .
     * @param resultSetType .
     * @param resultSetConcurrency .
     *
     * @return .
     *
     * @throws SQLException
     */
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency)
        throws SQLException {

        CallableStatement stmt;
        long              finish;
        long              start = 0;

        try {

            start     = System.currentTimeMillis();
            stmt      = targetConnection.prepareCall(sql, resultSetType, resultSetConcurrency);

        } finally {

            finish = System.currentTimeMillis();

            log("prepareCall(" + sql + ", " + resultSetType + ", " + resultSetConcurrency + ") took " + (finish - start) + "ms");

        }

        return new ProxyCallableStatement(stmt, this, sql);

    }

    /**
     * Method prepareCall
     *
     * @param sql .
     * @param resultSetType .
     * @param resultSetConcurrency .
     * @param resultSetHoldability .
     *
     * @return .
     *
     * @throws SQLException
     */
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability)
        throws SQLException {

        CallableStatement stmt;
        long              finish;
        long              start = 0;

        try {

            start     = System.currentTimeMillis();
            stmt      = targetConnection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);

        } finally {

            finish = System.currentTimeMillis();

            log("prepareCall(" + sql + ", " + resultSetType + ", " + resultSetConcurrency + ", " + resultSetHoldability + ") took " +
                (finish - start) + "ms");

        }

        return new ProxyCallableStatement(stmt, this, sql);

    }

    /**
     * Method prepareStatement
     *
     * @param sql .
     *
     * @return .
     *
     * @throws SQLException
     */
    public PreparedStatement prepareStatement(String sql)
        throws SQLException {

        PreparedStatement stmt;
        long              finish;
        long              start = 0;

        try {

            start     = System.currentTimeMillis();
            stmt      = targetConnection.prepareStatement(sql);

        } finally {

            finish = System.currentTimeMillis();

            log("prepareStatement(" + sql + ") took " + (finish - start) + "ms");

        }

        return new ProxyPreparedStatement(stmt, this, sql);

    }

    /**
     * Method prepareStatement
     *
     * @param sql .
     * @param autoGeneratedKeys .
     *
     * @return .
     *
     * @throws SQLException
     */
    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys)
        throws SQLException {

        PreparedStatement stmt;
        long              finish;
        long              start = 0;

        try {

            start     = System.currentTimeMillis();
            stmt      = targetConnection.prepareStatement(sql, autoGeneratedKeys);

        } finally {

            finish = System.currentTimeMillis();

            log("prepareStatement(" + sql + ", " + autoGeneratedKeys + ") took " + (finish - start) + "ms");

        }

        return new ProxyPreparedStatement(stmt, this, sql);

    }

    /**
     * Method prepareStatement
     *
     * @param sql .
     * @param columnIndexes .
     *
     * @return .
     *
     * @throws SQLException
     */
    public PreparedStatement prepareStatement(String sql, int[] columnIndexes)
        throws SQLException {

        PreparedStatement stmt;
        long              finish;
        long              start = 0;

        try {

            start     = System.currentTimeMillis();
            stmt      = targetConnection.prepareStatement(sql, columnIndexes);

        } finally {

            finish = System.currentTimeMillis();

            log("prepareStatement(" + sql + ", int[]) took " + (finish - start) + "ms");

        }

        return new ProxyPreparedStatement(stmt, this, sql);

    }

    /**
     * Method prepareStatement
     *
     * @param sql .
     * @param columnNames .
     *
     * @return .
     *
     * @throws SQLException
     */
    public PreparedStatement prepareStatement(String sql, String[] columnNames)
        throws SQLException {

        PreparedStatement stmt;
        long              finish;
        long              start = 0;

        try {

            start     = System.currentTimeMillis();
            stmt      = targetConnection.prepareStatement(sql, columnNames);

        } finally {

            finish = System.currentTimeMillis();

            log("prepareStatement(" + sql + ", String[]) took " + (finish - start) + "ms");

        }

        return new ProxyPreparedStatement(stmt, this, sql);

    }

    /**
     * Method prepareStatement
     *
     * @param sql .
     * @param resultSetType .
     * @param resultSetConcurrency .
     *
     * @return .
     *
     * @throws SQLException
     */
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
        throws SQLException {

        PreparedStatement stmt;
        long              finish;
        long              start = 0;

        try {

            start     = System.currentTimeMillis();
            stmt      = targetConnection.prepareStatement(sql, resultSetType, resultSetConcurrency);

        } finally {

            finish = System.currentTimeMillis();

            log("prepareStatement(" + sql + ", " + resultSetType + ", " + resultSetConcurrency + ") took " + (finish - start) + "ms");

        }

        return new ProxyPreparedStatement(stmt, this, sql);

    }

    /**
     * Method prepareStatement
     *
     * @param sql .
     * @param resultSetType .
     * @param resultSetConcurrency .
     * @param resultSetHoldability .
     *
     * @return .
     *
     * @throws SQLException
     */
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability)
        throws SQLException {

        PreparedStatement stmt;
        long              finish;
        long              start = 0;

        try {

            start     = System.currentTimeMillis();
            stmt      = targetConnection.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);

        } finally {

            finish = System.currentTimeMillis();

            log("prepareStatement(" + sql + ", " + resultSetType + ", " + resultSetConcurrency + ", " + resultSetHoldability + ") took " +
                (finish - start) + "ms");

        }

        return new ProxyPreparedStatement(stmt, this, sql);

    }

    /**
     * Method releaseSavepoint
     *
     * @param savepoint .
     *
     * @throws SQLException
     */
    public void releaseSavepoint(Savepoint savepoint) throws SQLException {

        log("releaseSavepoint(Savepoint)");
        targetConnection.releaseSavepoint(savepoint);

    }

    /**
     * Method rollback
     *
     * @throws SQLException
     */
    public void rollback() throws SQLException {

        log("rollback()");
        targetConnection.rollback();

    }

    /**
     * Method rollback
     *
     * @param savepoint .
     *
     * @throws SQLException
     */
    public void rollback(Savepoint savepoint) throws SQLException {

        log("rollback(Savepoint)");
        targetConnection.rollback(savepoint);

    }

    /**
     * Method setAutoCommit
     *
     * @param autoCommit .
     *
     * @throws SQLException
     */
    public void setAutoCommit(boolean autoCommit) throws SQLException {

        log("setAutoCommit(" + autoCommit + ")");
        targetConnection.setAutoCommit(autoCommit);

    }

    /**
     * Method setCatalog
     *
     * @param catalog .
     *
     * @throws SQLException
     */
    public void setCatalog(String catalog) throws SQLException {

        targetConnection.setCatalog(catalog);

    }

    /**
     * Method setHoldability
     *
     * @param holdability .
     *
     * @throws SQLException
     */
    public void setHoldability(int holdability) throws SQLException {

        targetConnection.setHoldability(holdability);

    }

    /**
     * Method setReadOnly
     *
     * @param readOnly .
     *
     * @throws SQLException
     */
    public void setReadOnly(boolean readOnly) throws SQLException {

        targetConnection.setReadOnly(readOnly);

    }

    /**
     * Method setSavepoint
     *
     * @return .
     *
     * @throws SQLException
     */
    public Savepoint setSavepoint() throws SQLException {

        log("setSavepoint()");

        return targetConnection.setSavepoint();

    }

    /**
     * Method setSavepoint
     *
     * @param name .
     *
     * @return .
     *
     * @throws SQLException
     */
    public Savepoint setSavepoint(String name) throws SQLException {

        log("setSavepoint(" + name + ")");

        return targetConnection.setSavepoint(name);

    }

    /**
     * Method setTransactionIsolation
     *
     * @param level .
     *
     * @throws SQLException
     */
    public void setTransactionIsolation(int level) throws SQLException {

        targetConnection.setTransactionIsolation(level);

    }

    /**
     * Method setTypeMap
     *
     * @param map .
     *
     * @throws SQLException
     */
    public void setTypeMap(Map map) throws SQLException {

        targetConnection.setTypeMap(map);

    }

    /**
     * Method equals
     *
     * @param obj .
     *
     * @return .
     */
    public boolean equals(Object obj) {

        return targetConnection.equals(obj);

    }

    /**
     * Method hashCode
     *
     * @return .
     */
    public int hashCode() {

        return targetConnection.hashCode();

    }

    /**
     * Method toString
     *
     * @return .
     */
    public String toString() {

        return targetConnection.toString();

    }

}
