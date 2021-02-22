package org.shiftone.jrat.proxy.jdbc;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Date;
import java.sql.Ref;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;


/**
 * Class ProxyCallableStatement
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 */
public class ProxyCallableStatement extends ProxyPreparedStatement implements CallableStatement {

    private static final Log  LOG                     = LogFactory.getLogger(ProxyCallableStatement.class);
    private CallableStatement targetCallableStatement = null;

    /**
     * Constructor ProxyCallableStatement
     *
     * @param targetCallableStatement
     * @param proxyConnection
     * @param preparedSQL
     */
    public ProxyCallableStatement(CallableStatement targetCallableStatement, ProxyConnection proxyConnection, String preparedSQL) {
        super(targetCallableStatement, proxyConnection, preparedSQL);

        log("new ProxyCallableStatement");

        this.targetCallableStatement = targetCallableStatement;

    }

    /**
     * Method getArray
     *
     * @param i .
     *
     * @return .
     *
     * @throws SQLException
     */
    public Array getArray(int i) throws SQLException {

        return targetCallableStatement.getArray(i);

    }

    /**
     * Method getArray
     *
     * @param parameterName .
     *
     * @return .
     *
     * @throws SQLException
     */
    public Array getArray(String parameterName) throws SQLException {

        return targetCallableStatement.getArray(parameterName);

    }

    /**
     * Method getBigDecimal
     *
     * @param parameterIndex .
     *
     * @return .
     *
     * @throws SQLException
     */
    public BigDecimal getBigDecimal(int parameterIndex)
        throws SQLException {

        return targetCallableStatement.getBigDecimal(parameterIndex);

    }

    /**
     * Method getBigDecimal
     *
     * @param parameterIndex .
     * @param scale .
     *
     * @return .
     *
     * @throws SQLException
     */
    public BigDecimal getBigDecimal(int parameterIndex, int scale)
        throws SQLException {

        return targetCallableStatement.getBigDecimal(parameterIndex, scale);

    }

    /**
     * Method getBigDecimal
     *
     * @param parameterName .
     *
     * @return .
     *
     * @throws SQLException
     */
    public BigDecimal getBigDecimal(String parameterName)
        throws SQLException {

        return targetCallableStatement.getBigDecimal(parameterName);

    }

    /**
     * Method getBlob
     *
     * @param i .
     *
     * @return .
     *
     * @throws SQLException
     */
    public Blob getBlob(int i) throws SQLException {

        return targetCallableStatement.getBlob(i);

    }

    /**
     * Method getBlob
     *
     * @param parameterName .
     *
     * @return .
     *
     * @throws SQLException
     */
    public Blob getBlob(String parameterName) throws SQLException {

        return targetCallableStatement.getBlob(parameterName);

    }

    /**
     * Method getBoolean
     *
     * @param parameterIndex .
     *
     * @return .
     *
     * @throws SQLException
     */
    public boolean getBoolean(int parameterIndex) throws SQLException {

        return targetCallableStatement.getBoolean(parameterIndex);

    }

    /**
     * Method getBoolean
     *
     * @param parameterName .
     *
     * @return .
     *
     * @throws SQLException
     */
    public boolean getBoolean(String parameterName) throws SQLException {

        return targetCallableStatement.getBoolean(parameterName);

    }

    /**
     * Method getByte
     *
     * @param parameterIndex .
     *
     * @return .
     *
     * @throws SQLException
     */
    public byte getByte(int parameterIndex) throws SQLException {

        return targetCallableStatement.getByte(parameterIndex);

    }

    /**
     * Method getByte
     *
     * @param parameterName .
     *
     * @return .
     *
     * @throws SQLException
     */
    public byte getByte(String parameterName) throws SQLException {

        return targetCallableStatement.getByte(parameterName);

    }

    /**
     * Method getBytes
     *
     * @param parameterIndex .
     *
     * @return .
     *
     * @throws SQLException
     */
    public byte[] getBytes(int parameterIndex) throws SQLException {

        return targetCallableStatement.getBytes(parameterIndex);

    }

    /**
     * Method getBytes
     *
     * @param parameterName .
     *
     * @return .
     *
     * @throws SQLException
     */
    public byte[] getBytes(String parameterName) throws SQLException {

        return targetCallableStatement.getBytes(parameterName);

    }

    /**
     * Method getClob
     *
     * @param i .
     *
     * @return .
     *
     * @throws SQLException
     */
    public Clob getClob(int i) throws SQLException {

        return targetCallableStatement.getClob(i);

    }

    /**
     * Method getClob
     *
     * @param parameterName .
     *
     * @return .
     *
     * @throws SQLException
     */
    public Clob getClob(String parameterName) throws SQLException {

        return targetCallableStatement.getClob(parameterName);

    }

    /**
     * Method getDate
     *
     * @param parameterIndex .
     *
     * @return .
     *
     * @throws SQLException
     */
    public Date getDate(int parameterIndex) throws SQLException {

        return targetCallableStatement.getDate(parameterIndex);

    }

    /**
     * Method getDate
     *
     * @param parameterIndex .
     * @param cal .
     *
     * @return .
     *
     * @throws SQLException
     */
    public Date getDate(int parameterIndex, Calendar cal)
        throws SQLException {

        return targetCallableStatement.getDate(parameterIndex, cal);

    }

    /**
     * Method getDate
     *
     * @param parameterName .
     *
     * @return .
     *
     * @throws SQLException
     */
    public Date getDate(String parameterName) throws SQLException {

        return targetCallableStatement.getDate(parameterName);

    }

    /**
     * Method getDate
     *
     * @param parameterName .
     * @param cal .
     *
     * @return .
     *
     * @throws SQLException
     */
    public Date getDate(String parameterName, Calendar cal)
        throws SQLException {

        return targetCallableStatement.getDate(parameterName, cal);

    }

    /**
     * Method getDouble
     *
     * @param parameterIndex .
     *
     * @return .
     *
     * @throws SQLException
     */
    public double getDouble(int parameterIndex) throws SQLException {

        return targetCallableStatement.getDouble(parameterIndex);

    }

    /**
     * Method getDouble
     *
     * @param parameterName .
     *
     * @return .
     *
     * @throws SQLException
     */
    public double getDouble(String parameterName) throws SQLException {

        return targetCallableStatement.getDouble(parameterName);

    }

    /**
     * Method getFloat
     *
     * @param parameterIndex .
     *
     * @return .
     *
     * @throws SQLException
     */
    public float getFloat(int parameterIndex) throws SQLException {

        return targetCallableStatement.getFloat(parameterIndex);

    }

    /**
     * Method getFloat
     *
     * @param parameterName .
     *
     * @return .
     *
     * @throws SQLException
     */
    public float getFloat(String parameterName) throws SQLException {

        return targetCallableStatement.getFloat(parameterName);

    }

    /**
     * Method getInt
     *
     * @param parameterIndex .
     *
     * @return .
     *
     * @throws SQLException
     */
    public int getInt(int parameterIndex) throws SQLException {

        return targetCallableStatement.getInt(parameterIndex);

    }

    /**
     * Method getInt
     *
     * @param parameterName .
     *
     * @return .
     *
     * @throws SQLException
     */
    public int getInt(String parameterName) throws SQLException {

        return targetCallableStatement.getInt(parameterName);

    }

    /**
     * Method getLong
     *
     * @param parameterIndex .
     *
     * @return .
     *
     * @throws SQLException
     */
    public long getLong(int parameterIndex) throws SQLException {

        return targetCallableStatement.getLong(parameterIndex);

    }

    /**
     * Method getLong
     *
     * @param parameterName .
     *
     * @return .
     *
     * @throws SQLException
     */
    public long getLong(String parameterName) throws SQLException {

        return targetCallableStatement.getLong(parameterName);

    }

    /**
     * Method getObject
     *
     * @param i .
     * @param map .
     *
     * @return .
     *
     * @throws SQLException
     */
    public Object getObject(int i, Map map) throws SQLException {

        return targetCallableStatement.getObject(i, map);

    }

    /**
     * Method getObject
     *
     * @param parameterIndex .
     *
     * @return .
     *
     * @throws SQLException
     */
    public Object getObject(int parameterIndex) throws SQLException {

        return targetCallableStatement.getObject(parameterIndex);

    }

    /**
     * Method getObject
     *
     * @param parameterName .
     *
     * @return .
     *
     * @throws SQLException
     */
    public Object getObject(String parameterName) throws SQLException {

        return targetCallableStatement.getObject(parameterName);

    }

    /**
     * Method getObject
     *
     * @param parameterName .
     * @param map .
     *
     * @return .
     *
     * @throws SQLException
     */
    public Object getObject(String parameterName, Map map)
        throws SQLException {

        return targetCallableStatement.getObject(parameterName, map);

    }

    /**
     * Method getRef
     *
     * @param i .
     *
     * @return .
     *
     * @throws SQLException
     */
    public Ref getRef(int i) throws SQLException {

        return targetCallableStatement.getRef(i);

    }

    /**
     * Method getRef
     *
     * @param parameterName .
     *
     * @return .
     *
     * @throws SQLException
     */
    public Ref getRef(String parameterName) throws SQLException {

        return targetCallableStatement.getRef(parameterName);

    }

    /**
     * Method getShort
     *
     * @param parameterIndex .
     *
     * @return .
     *
     * @throws SQLException
     */
    public short getShort(int parameterIndex) throws SQLException {

        return targetCallableStatement.getShort(parameterIndex);

    }

    /**
     * Method getShort
     *
     * @param parameterName .
     *
     * @return .
     *
     * @throws SQLException
     */
    public short getShort(String parameterName) throws SQLException {

        return targetCallableStatement.getShort(parameterName);

    }

    /**
     * Method getString
     *
     * @param parameterIndex .
     *
     * @return .
     *
     * @throws SQLException
     */
    public String getString(int parameterIndex) throws SQLException {

        return targetCallableStatement.getString(parameterIndex);

    }

    /**
     * Method getString
     *
     * @param parameterName .
     *
     * @return .
     *
     * @throws SQLException
     */
    public String getString(String parameterName) throws SQLException {

        return targetCallableStatement.getString(parameterName);

    }

    /**
     * Method getTime
     *
     * @param parameterIndex .
     *
     * @return .
     *
     * @throws SQLException
     */
    public Time getTime(int parameterIndex) throws SQLException {

        return targetCallableStatement.getTime(parameterIndex);

    }

    /**
     * Method getTime
     *
     * @param parameterIndex .
     * @param cal .
     *
     * @return .
     *
     * @throws SQLException
     */
    public Time getTime(int parameterIndex, Calendar cal)
        throws SQLException {

        return targetCallableStatement.getTime(parameterIndex, cal);

    }

    /**
     * Method getTime
     *
     * @param parameterName .
     *
     * @return .
     *
     * @throws SQLException
     */
    public Time getTime(String parameterName) throws SQLException {

        return targetCallableStatement.getTime(parameterName);

    }

    /**
     * Method getTime
     *
     * @param parameterName .
     * @param cal .
     *
     * @return .
     *
     * @throws SQLException
     */
    public Time getTime(String parameterName, Calendar cal)
        throws SQLException {

        return targetCallableStatement.getTime(parameterName, cal);

    }

    /**
     * Method getTimestamp
     *
     * @param parameterIndex .
     *
     * @return .
     *
     * @throws SQLException
     */
    public Timestamp getTimestamp(int parameterIndex) throws SQLException {

        return targetCallableStatement.getTimestamp(parameterIndex);

    }

    /**
     * Method getTimestamp
     *
     * @param parameterIndex .
     * @param cal .
     *
     * @return .
     *
     * @throws SQLException
     */
    public Timestamp getTimestamp(int parameterIndex, Calendar cal)
        throws SQLException {

        return targetCallableStatement.getTimestamp(parameterIndex, cal);

    }

    /**
     * Method getTimestamp
     *
     * @param parameterName .
     *
     * @return .
     *
     * @throws SQLException
     */
    public Timestamp getTimestamp(String parameterName)
        throws SQLException {

        return targetCallableStatement.getTimestamp(parameterName);

    }

    /**
     * Method getTimestamp
     *
     * @param parameterName .
     * @param cal .
     *
     * @return .
     *
     * @throws SQLException
     */
    public Timestamp getTimestamp(String parameterName, Calendar cal)
        throws SQLException {

        return targetCallableStatement.getTimestamp(parameterName, cal);

    }

    /**
     * Method getURL
     *
     * @param parameterIndex .
     *
     * @return .
     *
     * @throws SQLException
     */
    public URL getURL(int parameterIndex) throws SQLException {

        return targetCallableStatement.getURL(parameterIndex);

    }

    /**
     * Method getURL
     *
     * @param parameterName .
     *
     * @return .
     *
     * @throws SQLException
     */
    public URL getURL(String parameterName) throws SQLException {

        return targetCallableStatement.getURL(parameterName);

    }

    /**
     * Method registerOutParameter
     *
     * @param parameterIndex .
     * @param sqlType .
     *
     * @throws SQLException
     */
    public void registerOutParameter(int parameterIndex, int sqlType)
        throws SQLException {

        targetCallableStatement.registerOutParameter(parameterIndex, sqlType);

    }

    /**
     * Method registerOutParameter
     *
     * @param parameterIndex .
     * @param sqlType .
     * @param scale .
     *
     * @throws SQLException
     */
    public void registerOutParameter(int parameterIndex, int sqlType, int scale)
        throws SQLException {

        targetCallableStatement.registerOutParameter(parameterIndex, sqlType, scale);

    }

    /**
     * Method registerOutParameter
     *
     * @param parameterName .
     * @param sqlType .
     *
     * @throws SQLException
     */
    public void registerOutParameter(String parameterName, int sqlType)
        throws SQLException {

        targetCallableStatement.registerOutParameter(parameterName, sqlType);

    }

    /**
     * Method registerOutParameter
     *
     * @param parameterName .
     * @param sqlType .
     * @param scale .
     *
     * @throws SQLException
     */
    public void registerOutParameter(String parameterName, int sqlType, int scale)
        throws SQLException {

        targetCallableStatement.registerOutParameter(parameterName, sqlType, scale);

    }

    /**
     * Method registerOutParameter
     *
     * @param parameterName .
     * @param sqlType .
     * @param typeName .
     *
     * @throws SQLException
     */
    public void registerOutParameter(String parameterName, int sqlType, String typeName)
        throws SQLException {

        targetCallableStatement.registerOutParameter(parameterName, sqlType, typeName);

    }

    /**
     * Method registerOutParameter
     *
     * @param paramIndex .
     * @param sqlType .
     * @param typeName .
     *
     * @throws SQLException
     */
    public void registerOutParameter(int paramIndex, int sqlType, String typeName)
        throws SQLException {

        targetCallableStatement.registerOutParameter(paramIndex, sqlType, typeName);

    }

    /**
     * Method setAsciiStream
     *
     * @param parameterName .
     * @param x .
     * @param length .
     *
     * @throws SQLException
     */
    public void setAsciiStream(String parameterName, InputStream x, int length)
        throws SQLException {

        targetCallableStatement.setAsciiStream(parameterName, x, length);

    }

    /**
     * Method setBigDecimal
     *
     * @param parameterName .
     * @param x .
     *
     * @throws SQLException
     */
    public void setBigDecimal(String parameterName, BigDecimal x)
        throws SQLException {

        targetCallableStatement.setBigDecimal(parameterName, x);

    }

    /**
     * Method setBinaryStream
     *
     * @param parameterName .
     * @param x .
     * @param length .
     *
     * @throws SQLException
     */
    public void setBinaryStream(String parameterName, InputStream x, int length)
        throws SQLException {

        targetCallableStatement.setBinaryStream(parameterName, x, length);

    }

    /**
     * Method setBoolean
     *
     * @param parameterName .
     * @param x .
     *
     * @throws SQLException
     */
    public void setBoolean(String parameterName, boolean x)
        throws SQLException {

        targetCallableStatement.setBoolean(parameterName, x);

    }

    /**
     * Method setByte
     *
     * @param parameterName .
     * @param x .
     *
     * @throws SQLException
     */
    public void setByte(String parameterName, byte x) throws SQLException {

        targetCallableStatement.setByte(parameterName, x);

    }

    /**
     * Method setBytes
     *
     * @param parameterName .
     * @param x .
     *
     * @throws SQLException
     */
    public void setBytes(String parameterName, byte[] x)
        throws SQLException {

        targetCallableStatement.setBytes(parameterName, x);

    }

    /**
     * Method setCharacterStream
     *
     * @param parameterName .
     * @param reader .
     * @param length .
     *
     * @throws SQLException
     */
    public void setCharacterStream(String parameterName, Reader reader, int length)
        throws SQLException {

        targetCallableStatement.setCharacterStream(parameterName, reader, length);

    }

    /**
     * Method setDate
     *
     * @param parameterName .
     * @param x .
     *
     * @throws SQLException
     */
    public void setDate(String parameterName, Date x) throws SQLException {

        targetCallableStatement.setDate(parameterName, x);

    }

    /**
     * Method setDate
     *
     * @param parameterName .
     * @param x .
     * @param cal .
     *
     * @throws SQLException
     */
    public void setDate(String parameterName, Date x, Calendar cal)
        throws SQLException {

        targetCallableStatement.setDate(parameterName, x, cal);

    }

    /**
     * Method setDouble
     *
     * @param parameterName .
     * @param x .
     *
     * @throws SQLException
     */
    public void setDouble(String parameterName, double x)
        throws SQLException {

        targetCallableStatement.setDouble(parameterName, x);

    }

    /**
     * Method setFloat
     *
     * @param parameterName .
     * @param x .
     *
     * @throws SQLException
     */
    public void setFloat(String parameterName, float x)
        throws SQLException {

        targetCallableStatement.setFloat(parameterName, x);

    }

    /**
     * Method setInt
     *
     * @param parameterName .
     * @param x .
     *
     * @throws SQLException
     */
    public void setInt(String parameterName, int x) throws SQLException {

        targetCallableStatement.setInt(parameterName, x);

    }

    /**
     * Method setLong
     *
     * @param parameterName .
     * @param x .
     *
     * @throws SQLException
     */
    public void setLong(String parameterName, long x) throws SQLException {

        targetCallableStatement.setLong(parameterName, x);

    }

    /**
     * Method setNull
     *
     * @param parameterName .
     * @param sqlType .
     *
     * @throws SQLException
     */
    public void setNull(String parameterName, int sqlType)
        throws SQLException {

        targetCallableStatement.setNull(parameterName, sqlType);

    }

    /**
     * Method setNull
     *
     * @param parameterName .
     * @param sqlType .
     * @param typeName .
     *
     * @throws SQLException
     */
    public void setNull(String parameterName, int sqlType, String typeName)
        throws SQLException {

        targetCallableStatement.setNull(parameterName, sqlType, typeName);

    }

    /**
     * Method setObject
     *
     * @param parameterName .
     * @param x .
     *
     * @throws SQLException
     */
    public void setObject(String parameterName, Object x)
        throws SQLException {

        targetCallableStatement.setObject(parameterName, x);

    }

    /**
     * Method setObject
     *
     * @param parameterName .
     * @param x .
     * @param targetSqlType .
     *
     * @throws SQLException
     */
    public void setObject(String parameterName, Object x, int targetSqlType)
        throws SQLException {

        targetCallableStatement.setObject(parameterName, x, targetSqlType);

    }

    /**
     * Method setObject
     *
     * @param parameterName .
     * @param x .
     * @param targetSqlType .
     * @param scale .
     *
     * @throws SQLException
     */
    public void setObject(String parameterName, Object x, int targetSqlType, int scale)
        throws SQLException {

        targetCallableStatement.setObject(parameterName, x, targetSqlType, scale);

    }

    /**
     * Method setShort
     *
     * @param parameterName .
     * @param x .
     *
     * @throws SQLException
     */
    public void setShort(String parameterName, short x)
        throws SQLException {

        targetCallableStatement.setShort(parameterName, x);

    }

    /**
     * Method setString
     *
     * @param parameterName .
     * @param x .
     *
     * @throws SQLException
     */
    public void setString(String parameterName, String x)
        throws SQLException {

        targetCallableStatement.setString(parameterName, x);

    }

    /**
     * Method setTime
     *
     * @param parameterName .
     * @param x .
     *
     * @throws SQLException
     */
    public void setTime(String parameterName, Time x) throws SQLException {

        targetCallableStatement.setTime(parameterName, x);

    }

    /**
     * Method setTime
     *
     * @param parameterName .
     * @param x .
     * @param cal .
     *
     * @throws SQLException
     */
    public void setTime(String parameterName, Time x, Calendar cal)
        throws SQLException {

        targetCallableStatement.setTime(parameterName, x, cal);

    }

    /**
     * Method setTimestamp
     *
     * @param parameterName .
     * @param x .
     *
     * @throws SQLException
     */
    public void setTimestamp(String parameterName, Timestamp x)
        throws SQLException {

        targetCallableStatement.setTimestamp(parameterName, x);

    }

    /**
     * Method setTimestamp
     *
     * @param parameterName .
     * @param x .
     * @param cal .
     *
     * @throws SQLException
     */
    public void setTimestamp(String parameterName, Timestamp x, Calendar cal)
        throws SQLException {

        targetCallableStatement.setTimestamp(parameterName, x, cal);

    }

    /**
     * Method setURL
     *
     * @param parameterName .
     * @param val .
     *
     * @throws SQLException
     */
    public void setURL(String parameterName, URL val) throws SQLException {

        targetCallableStatement.setURL(parameterName, val);

    }

    /**
     * Method wasNull
     *
     * @return .
     *
     * @throws SQLException
     */
    public boolean wasNull() throws SQLException {

        return targetCallableStatement.wasNull();

    }

}
