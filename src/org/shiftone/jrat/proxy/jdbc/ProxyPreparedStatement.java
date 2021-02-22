package org.shiftone.jrat.proxy.jdbc;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;


/**
 * Class ProxyPreparedStatement
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 */
public class ProxyPreparedStatement extends ProxyStatement implements PreparedStatement {

    private static final Log LOG                     = LogFactory.getLogger(ProxyPreparedStatement.class);
    PreparedStatement        targetPreparedStatement = null;
    String                   preparedSQL             = null;

    /**
     * Constructor ProxyPreparedStatement
     *
     * @param targetPreparedStatement
     * @param proxyConnection
     * @param preparedSQL
     */
    public ProxyPreparedStatement(PreparedStatement targetPreparedStatement, ProxyConnection proxyConnection, String preparedSQL) {
        super(targetPreparedStatement, proxyConnection);

        log("new ProxyPreparedStatement : " + preparedSQL);

        this.targetPreparedStatement     = targetPreparedStatement;
        this.preparedSQL                 = preparedSQL;

    }

    /**
     * Method addBatch
     *
     * @throws SQLException
     */
    public void addBatch() throws SQLException {

        targetPreparedStatement.addBatch();

    }

    /**
     * Method clearParameters
     *
     * @throws SQLException
     */
    public void clearParameters() throws SQLException {

        targetPreparedStatement.clearParameters();

    }

    /**
     * Method execute
     *
     * @return .
     *
     * @throws SQLException
     */
    public boolean execute() throws SQLException {

        boolean b;
        long    finish;
        long    start = 0;

        try {

            start     = System.currentTimeMillis();
            b         = targetPreparedStatement.execute();

        } finally {

            finish = System.currentTimeMillis();

            log("execute() [" + preparedSQL + "] took " + (finish - start) + "ms");

        }

        return b;

    }

    /**
     * Method executeQuery
     *
     * @return .
     *
     * @throws SQLException
     */
    public ResultSet executeQuery() throws SQLException {

        ResultSet rs;
        long      finish;
        long      start = 0;

        try {

            start     = System.currentTimeMillis();
            rs        = targetPreparedStatement.executeQuery();

        } finally {

            finish = System.currentTimeMillis();

            log("executeQuery() [" + preparedSQL + "] took " + (finish - start) + "ms");

        }

        return rs;

    }

    /**
     * Method executeUpdate
     *
     * @return .
     *
     * @throws SQLException
     */
    public int executeUpdate() throws SQLException {

        int  i      = -1;
        long finish;
        long start = 0;

        try {

            start     = System.currentTimeMillis();
            i         = targetPreparedStatement.executeUpdate();

        } finally {

            finish = System.currentTimeMillis();

            log("executeUpdate() [" + preparedSQL + "] -> " + i + "took " + (finish - start) + "ms");

        }

        return i;

    }

    /**
     * Method getMetaData
     *
     * @return .
     *
     * @throws SQLException
     */
    public ResultSetMetaData getMetaData() throws SQLException {

        return targetPreparedStatement.getMetaData();

    }

    /**
     * Method getParameterMetaData
     *
     * @return .
     *
     * @throws SQLException
     */
    public ParameterMetaData getParameterMetaData() throws SQLException {

        return targetPreparedStatement.getParameterMetaData();

    }

    /**
     * Method setArray
     *
     * @param i .
     * @param x .
     *
     * @throws SQLException
     */
    public void setArray(int i, Array x) throws SQLException {

        targetPreparedStatement.setArray(i, x);

    }

    /**
     * Method setAsciiStream
     *
     * @param parameterIndex .
     * @param x .
     * @param length .
     *
     * @throws SQLException
     */
    public void setAsciiStream(int parameterIndex, InputStream x, int length)
        throws SQLException {

        targetPreparedStatement.setAsciiStream(parameterIndex, x, length);

    }

    /**
     * Method setBigDecimal
     *
     * @param parameterIndex .
     * @param x .
     *
     * @throws SQLException
     */
    public void setBigDecimal(int parameterIndex, BigDecimal x)
        throws SQLException {

        targetPreparedStatement.setBigDecimal(parameterIndex, x);

    }

    /**
     * Method setBinaryStream
     *
     * @param parameterIndex .
     * @param x .
     * @param length .
     *
     * @throws SQLException
     */
    public void setBinaryStream(int parameterIndex, InputStream x, int length)
        throws SQLException {

        targetPreparedStatement.setBinaryStream(parameterIndex, x, length);

    }

    /**
     * Method setBlob
     *
     * @param i .
     * @param x .
     *
     * @throws SQLException
     */
    public void setBlob(int i, Blob x) throws SQLException {

        targetPreparedStatement.setBlob(i, x);

    }

    /**
     * Method setBoolean
     *
     * @param parameterIndex .
     * @param x .
     *
     * @throws SQLException
     */
    public void setBoolean(int parameterIndex, boolean x)
        throws SQLException {

        targetPreparedStatement.setBoolean(parameterIndex, x);

    }

    /**
     * Method setByte
     *
     * @param parameterIndex .
     * @param x .
     *
     * @throws SQLException
     */
    public void setByte(int parameterIndex, byte x) throws SQLException {

        targetPreparedStatement.setByte(parameterIndex, x);

    }

    /**
     * Method setBytes
     *
     * @param parameterIndex .
     * @param x .
     *
     * @throws SQLException
     */
    public void setBytes(int parameterIndex, byte[] x)
        throws SQLException {

        targetPreparedStatement.setBytes(parameterIndex, x);

    }

    /**
     * Method setCharacterStream
     *
     * @param parameterIndex .
     * @param reader .
     * @param length .
     *
     * @throws SQLException
     */
    public void setCharacterStream(int parameterIndex, Reader reader, int length)
        throws SQLException {

        targetPreparedStatement.setCharacterStream(parameterIndex, reader, length);

    }

    /**
     * Method setClob
     *
     * @param i .
     * @param x .
     *
     * @throws SQLException
     */
    public void setClob(int i, Clob x) throws SQLException {

        targetPreparedStatement.setClob(i, x);

    }

    /**
     * Method setDate
     *
     * @param parameterIndex .
     * @param x .
     *
     * @throws SQLException
     */
    public void setDate(int parameterIndex, Date x) throws SQLException {

        targetPreparedStatement.setDate(parameterIndex, x);

    }

    /**
     * Method setDate
     *
     * @param parameterIndex .
     * @param x .
     * @param cal .
     *
     * @throws SQLException
     */
    public void setDate(int parameterIndex, Date x, Calendar cal)
        throws SQLException {

        targetPreparedStatement.setDate(parameterIndex, x, cal);

    }

    /**
     * Method setDouble
     *
     * @param parameterIndex .
     * @param x .
     *
     * @throws SQLException
     */
    public void setDouble(int parameterIndex, double x)
        throws SQLException {

        targetPreparedStatement.setDouble(parameterIndex, x);

    }

    /**
     * Method setFloat
     *
     * @param parameterIndex .
     * @param x .
     *
     * @throws SQLException
     */
    public void setFloat(int parameterIndex, float x) throws SQLException {

        targetPreparedStatement.setFloat(parameterIndex, x);

    }

    /**
     * Method setInt
     *
     * @param parameterIndex .
     * @param x .
     *
     * @throws SQLException
     */
    public void setInt(int parameterIndex, int x) throws SQLException {

        targetPreparedStatement.setInt(parameterIndex, x);

    }

    /**
     * Method setLong
     *
     * @param parameterIndex .
     * @param x .
     *
     * @throws SQLException
     */
    public void setLong(int parameterIndex, long x) throws SQLException {

        targetPreparedStatement.setLong(parameterIndex, x);

    }

    /**
     * Method setNull
     *
     * @param parameterIndex .
     * @param sqlType .
     *
     * @throws SQLException
     */
    public void setNull(int parameterIndex, int sqlType)
        throws SQLException {

        targetPreparedStatement.setNull(parameterIndex, sqlType);

    }

    /**
     * Method setNull
     *
     * @param paramIndex .
     * @param sqlType .
     * @param typeName .
     *
     * @throws SQLException
     */
    public void setNull(int paramIndex, int sqlType, String typeName)
        throws SQLException {

        targetPreparedStatement.setNull(paramIndex, sqlType, typeName);

    }

    /**
     * Method setObject
     *
     * @param parameterIndex .
     * @param x .
     *
     * @throws SQLException
     */
    public void setObject(int parameterIndex, Object x)
        throws SQLException {

        targetPreparedStatement.setObject(parameterIndex, x);

    }

    /**
     * Method setObject
     *
     * @param parameterIndex .
     * @param x .
     * @param targetSqlType .
     *
     * @throws SQLException
     */
    public void setObject(int parameterIndex, Object x, int targetSqlType)
        throws SQLException {

        targetPreparedStatement.setObject(parameterIndex, x, targetSqlType);

    }

    /**
     * Method setObject
     *
     * @param parameterIndex .
     * @param x .
     * @param targetSqlType .
     * @param scale .
     *
     * @throws SQLException
     */
    public void setObject(int parameterIndex, Object x, int targetSqlType, int scale)
        throws SQLException {

        targetPreparedStatement.setObject(parameterIndex, x, targetSqlType, scale);

    }

    /**
     * Method setRef
     *
     * @param i .
     * @param x .
     *
     * @throws SQLException
     */
    public void setRef(int i, Ref x) throws SQLException {

        targetPreparedStatement.setRef(i, x);

    }

    /**
     * Method setShort
     *
     * @param parameterIndex .
     * @param x .
     *
     * @throws SQLException
     */
    public void setShort(int parameterIndex, short x) throws SQLException {

        targetPreparedStatement.setShort(parameterIndex, x);

    }

    /**
     * Method setString
     *
     * @param parameterIndex .
     * @param x .
     *
     * @throws SQLException
     */
    public void setString(int parameterIndex, String x)
        throws SQLException {

        targetPreparedStatement.setString(parameterIndex, x);

    }

    /**
     * Method setTime
     *
     * @param parameterIndex .
     * @param x .
     *
     * @throws SQLException
     */
    public void setTime(int parameterIndex, Time x) throws SQLException {

        targetPreparedStatement.setTime(parameterIndex, x);

    }

    /**
     * Method setTime
     *
     * @param parameterIndex .
     * @param x .
     * @param cal .
     *
     * @throws SQLException
     */
    public void setTime(int parameterIndex, Time x, Calendar cal)
        throws SQLException {

        targetPreparedStatement.setTime(parameterIndex, x, cal);

    }

    /**
     * Method setTimestamp
     *
     * @param parameterIndex .
     * @param x .
     *
     * @throws SQLException
     */
    public void setTimestamp(int parameterIndex, Timestamp x)
        throws SQLException {

        targetPreparedStatement.setTimestamp(parameterIndex, x);

    }

    /**
     * Method setTimestamp
     *
     * @param parameterIndex .
     * @param x .
     * @param cal .
     *
     * @throws SQLException
     */
    public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal)
        throws SQLException {

        targetPreparedStatement.setTimestamp(parameterIndex, x, cal);

    }

    /**
     * Method setUnicodeStream
     *
     * @param parameterIndex .
     * @param x .
     * @param length .
     *
     * @throws SQLException
     */
    public void setUnicodeStream(int parameterIndex, InputStream x, int length)
        throws SQLException {

        targetPreparedStatement.setUnicodeStream(parameterIndex, x, length);

    }

    /**
     * Method setURL
     *
     * @param parameterIndex .
     * @param x .
     *
     * @throws SQLException
     */
    public void setURL(int parameterIndex, URL x) throws SQLException {

        targetPreparedStatement.setURL(parameterIndex, x);

    }

}
