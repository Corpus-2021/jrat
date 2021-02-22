package org.shiftone.jrat.proxy.jdbc;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;

import javax.naming.NamingException;
import javax.naming.Reference;
import javax.naming.Referenceable;
import javax.sql.XAConnection;
import javax.sql.XADataSource;

import org.shiftone.jrat.util.IntrospectionUtil;
import org.shiftone.jrat.util.StringUtil;
import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;


/**
 * JDBCConnectionPool DriverName="oracle.jdbc.xa.client.OracleXADataSource" InitialCapacity="20" MaxCapacity="40" Name="CMS XA
 * Connection Pool" Properties="user=mcmapp;url=jdbc:oracle:oci8:
 */
public class ProxyXADataSource implements XADataSource, Referenceable {

    private static final Log LOG                   = LogFactory.getLogger(ProxyXADataSource.class);
    private Properties       properties            = new Properties();
    private String           targetDriverClassName = null;
    private String           targetProperties      = null;
    XADataSource             targetDataSource      = null;

    /**
     * Constructor ProxyXADataSource
     */
    public ProxyXADataSource() {

        LOG.info("new ProxyXADataSource");

    }

    /**
     * Method getXAConnection
     *
     * @return .
     *
     * @throws SQLException
     */
    public XAConnection getXAConnection() throws SQLException {

        LOG.info("getXAConnection()");

        return new ProxyXAConnection(getTargetDataSource().getXAConnection());

    }

    /**
     * Method getXAConnection
     *
     * @param user .
     * @param password .
     *
     * @return .
     *
     * @throws SQLException
     */
    public XAConnection getXAConnection(String user, String password)
        throws SQLException {

        LOG.info("getXAConnection(" + user + ", " + password + ")");

        return new ProxyXAConnection(getTargetDataSource().getXAConnection(user, password));

    }

    /**
     * Method getTargetDataSource
     *
     * @return .
     *
     * @throws SQLException
     */
    private synchronized XADataSource getTargetDataSource()
        throws SQLException {

        if (targetDataSource == null) {

            targetDataSource = newXADataSourceInstance(targetDriverClassName, properties);

        }

        return targetDataSource;

    }

    /**
     * Method newXADataSourceInstance
     *
     * @param driverName .
     * @param properties .
     *
     * @return .
     *
     * @throws SQLException
     */
    public static XADataSource newXADataSourceInstance(String driverName, Properties properties)
        throws SQLException {

        Class       klass    = null;
        Object      instance = null;
        Enumeration keys     = null;
        String      key      = null;
        String      value    = null;
        Method      method   = null;
        Object      param    = null;

        try {

            LOG.info("new XADataSource : " + driverName);

            klass        = Class.forName(driverName);
            instance     = klass.newInstance();

        } catch (Exception e) {

            throw new SQLException("unable to create instance of driver : " + driverName + " - " + e.getMessage());

        }

        keys = properties.keys();

        while (keys.hasMoreElements()) {

            key       = (String) keys.nextElement();
            value     = properties.getProperty(key);

            try {

                method     = IntrospectionUtil.getMethod(klass, "set" + key, 1);
                param      = IntrospectionUtil.convert(value, method.getParameterTypes()[0]);

                LOG.info("calling " + method.getName() + "( " + value + " )");
                method.invoke(instance, new Object[] { param });

            } catch (Exception e) {

                LOG.warn("error setting driver property : " + key + " to " + value + " - " + e.getMessage());

            }

        }

        return (XADataSource) instance;

    }

    //===========================================================================================

    /**
     * Method getLoginTimeout
     *
     * @return .
     *
     * @throws SQLException
     */
    public int getLoginTimeout() throws SQLException {

        XADataSource ds           = getTargetDataSource();
        int          loginTimeout = ds.getLoginTimeout();

        LOG.info("getLoginTimeout() -> " + loginTimeout);

        return loginTimeout;

    }

    /**
     * Method getLogWriter
     *
     * @return .
     *
     * @throws SQLException
     */
    public PrintWriter getLogWriter() throws SQLException {

        LOG.debug("getLogWriter()");

        return getTargetDataSource().getLogWriter();

    }

    /**
     * Method setLoginTimeout
     *
     * @param seconds .
     *
     * @throws SQLException
     */
    public void setLoginTimeout(int seconds) throws SQLException {

        LOG.info("setLoginTimeout(" + seconds + ")");
        getTargetDataSource().setLoginTimeout(seconds);

    }

    /**
     * Method setLogWriter
     *
     * @param out .
     *
     * @throws SQLException
     */
    public void setLogWriter(PrintWriter out) throws SQLException {

        LOG.info("setLogWriter(.)");
        getTargetDataSource().setLogWriter(out);

    }

    //===========================================================================================

    /**
     * Method getReference
     *
     * @return .
     *
     * @throws NamingException
     * @throws UnsupportedOperationException
     */
    public Reference getReference() throws NamingException {

        Reference reference = null;

        LOG.info("getReference()");

        try {

            XADataSource ds = getTargetDataSource();

            if (ds instanceof Referenceable) {

                reference = ((Referenceable) ds).getReference();

            }

        } catch (Exception e) {

        }

        if (reference == null) {

            throw new UnsupportedOperationException("getReference() failed for : " + targetDriverClassName);

        }

        return reference;

    }

    //===========================================================================================

    /**
     * Method setTargetDriver
     *
     * @param targetDriverClassName .
     */
    public void setTargetDriver(String targetDriverClassName) {

        this.targetDriverClassName = targetDriverClassName;

    }

    /**
     * Method getTargetDriver
     *
     * @return .
     */
    public String getTargetDriver() {

        return this.targetDriverClassName;

    }

    /**
     * Method setTargetProperties
     *
     * @param targetProperties .
     */
    public void setTargetProperties(String targetProperties) {

        this.targetProperties = targetProperties;

        StringUtil.parsePropertiesString(targetProperties, properties);

    }

    /**
     * Method getTargetProperties
     *
     * @return .
     */
    public String getTargetProperties() {

        return this.targetProperties;

    }

    //===========================================================================================

    /**
     * Method setProperty
     *
     * @param name .
     * @param value .
     */
    private void setProperty(String name, String value) {

        LOG.info("setProperty(" + name + " , " + value + ")");
        properties.setProperty(name, value);

    }

    /**
     * Method getProperty
     *
     * @param name .
     *
     * @return .
     */
    private String getProperty(String name) {

        return properties.getProperty(name);

    }

    //===========================================================================================

    /**
     * Method setUser
     *
     * @param s .
     */
    public void setUser(String s) {

        setProperty("user", s);

    }

    /**
     * Method getDatabaseName
     *
     * @return .
     */
    public String getDatabaseName() {

        return properties.getProperty("databaseName");

    }

    /**
     * Method getDataSourceName
     *
     * @return .
     */
    public String getDataSourceName() {

        return properties.getProperty("dataSourceName");

    }

    /**
     * Method getDescription
     *
     * @return .
     */
    public String getDescription() {

        return properties.getProperty("description");

    }

    /**
     * Method getDriverType
     *
     * @return .
     */
    public String getDriverType() {

        return properties.getProperty("driverType");

    }

    /**
     * Method getExplicitCachingEnabled
     *
     * @return .
     *
     * @throws SQLException
     */
    public String getExplicitCachingEnabled() throws SQLException {

        return properties.getProperty("explicitCachingEnabled");

    }

    /**
     * Method getImplicitCachingEnabled
     *
     * @return .
     *
     * @throws SQLException
     */
    public String getImplicitCachingEnabled() throws SQLException {

        return properties.getProperty("implicitCachingEnabled");

    }

    /**
     * Method getMaxStatements
     *
     * @return .
     *
     * @throws SQLException
     */
    public String getMaxStatements() throws SQLException {

        return getProperty("maxStatements");

    }

    /**
     * Method getNetworkProtocol
     *
     * @return .
     */
    public String getNetworkProtocol() {

        return getProperty("networkProtocol");

    }

    /**
     * Method getPassword
     *
     * @return .
     */
    protected String getPassword() {

        return getProperty("password");

    }

    /**
     * Method getPortNumber
     *
     * @return .
     */
    public String getPortNumber() {

        return getProperty("portNumber");

    }

    /**
     * Method getServerName
     *
     * @return .
     */
    public String getServerName() {

        return getProperty("serverName");

    }

    /**
     * Method getServiceName
     *
     * @return .
     */
    public String getServiceName() {

        return getProperty("serviceName");

    }

    /**
     * Method getTNSEntryName
     *
     * @return .
     */
    public String getTNSEntryName() {

        return getProperty("TNSEntryName");

    }

    /**
     * Method getURL
     *
     * @return .
     *
     * @throws SQLException
     */
    public String getURL() throws SQLException {

        return getProperty("URL");

    }

    /**
     * Method getUser
     *
     * @return .
     */
    public String getUser() {

        return getProperty("user");

    }

    /**
     * Method setDatabaseName
     *
     * @param s .
     */
    public void setDatabaseName(String s) {

        setProperty("databaseName", s);

    }

    /**
     * Method setDataSourceName
     *
     * @param s .
     */
    public void setDataSourceName(String s) {

        setProperty("dataSourceName", s);

    }

    /**
     * Method setDescription
     *
     * @param s .
     */
    public void setDescription(String s) {

        setProperty("description", s);

    }

    /**
     * Method setDriverType
     *
     * @param s .
     */
    public void setDriverType(String s) {

        setProperty("driverType", s);

    }

    /**
     * Method setExplicitCachingEnabled
     *
     * @param b .
     *
     * @throws SQLException
     */
    public void setExplicitCachingEnabled(String b) throws SQLException {

        setProperty("explicitCachingEnabled", b);

    }

    /**
     * Method setImplicitCachingEnabled
     *
     * @param b .
     *
     * @throws SQLException
     */
    public void setImplicitCachingEnabled(String b) throws SQLException {

        setProperty("implicitCachingEnabled", b);

    }

    /**
     * Method setMaxStatements
     *
     * @param i .
     *
     * @throws SQLException
     */
    public void setMaxStatements(String i) throws SQLException {

        setProperty("maxStatements", i);

    }

    /**
     * Method setNetworkProtocol
     *
     * @param s .
     */
    public void setNetworkProtocol(String s) {

        setProperty("networkProtocol", s);

    }

    /**
     * Method setPassword
     *
     * @param s .
     */
    public void setPassword(String s) {

        setProperty("password", s);

    }

    /**
     * Method setPortNumber
     *
     * @param i .
     */
    public void setPortNumber(String i) {

        setProperty("portNumber", i);

    }

    /**
     * Method setServerName
     *
     * @param s .
     */
    public void setServerName(String s) {

        setProperty("serverName", s);

    }

    /**
     * Method setServiceName
     *
     * @param s .
     */
    public void setServiceName(String s) {

        setProperty("serviceName", s);

    }

    /**
     * Method setTNSEntryName
     *
     * @param s .
     */
    public void setTNSEntryName(String s) {

        setProperty("TNSEntryName", s);

    }

    /**
     * Method setURL
     *
     * @param s .
     */
    public void setURL(String s) {

        setProperty("url", s);

    }

}
