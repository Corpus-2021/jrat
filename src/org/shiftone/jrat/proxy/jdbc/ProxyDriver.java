package org.shiftone.jrat.proxy.jdbc;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.util.Properties;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;


/**
 * Class ProxyDriver
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 */
public class ProxyDriver implements Driver {

    private static final Log   LOG                     = LogFactory.getLogger(ProxyDriver.class);
    public static final String URL_PREFIX              = "LOG:";
    public static final String REAL_DRIVER_PROPERTY    = "realdriver";
    public static final String REAL_DRIVER_DESCRIPTION = "the class name of the real driver";

    static {

        ProxyDriver driver = new ProxyDriver();

        try {

            DriverManager.registerDriver(driver);

        } catch (SQLException e) {

            LOG.error("Error registering LoggingJDBCDriver with DriverManager", e);

        }

    }

    /**
     * Method connect
     *
     * @param url .
     * @param info .
     *
     * @return .
     *
     * @throws SQLException
     */
    public Connection connect(String url, Properties info)
        throws SQLException {

        if (!url.startsWith(URL_PREFIX)) {

            throw new SQLException("url '" + url + "' does not begin with '" + URL_PREFIX + "'");

        }

        String     realUrl        = url.substring(URL_PREFIX.length());
        String     realDriver     = info.getProperty(REAL_DRIVER_PROPERTY);
        Connection realConnection = null;

        try {

            Class  driverClass = Class.forName(realDriver);
            Driver driver = (Driver) driverClass.newInstance();

            info.remove(REAL_DRIVER_PROPERTY);

            realConnection = driver.connect(realUrl, info);

        } catch (SQLException e) {

            throw e;

        } catch (Exception e) {

            throw new SQLException("unable to load target driver '" + realDriver + "'");

        }

        return new ProxyConnection(realConnection);

    }

    /**
     * Method acceptsURL
     *
     * @param url .
     *
     * @return .
     *
     * @throws SQLException
     */
    public boolean acceptsURL(String url) throws SQLException {

        return url.startsWith(URL_PREFIX);

    }

    /**
     * Method getPropertyInfo
     *
     * @param url .
     * @param info .
     *
     * @return .
     *
     * @throws SQLException
     */
    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info)
        throws SQLException {

        DriverPropertyInfo driverPropertyInfo = null;

        driverPropertyInfo                 = new DriverPropertyInfo(REAL_DRIVER_PROPERTY, null);
        driverPropertyInfo.required        = true;
        driverPropertyInfo.description     = REAL_DRIVER_DESCRIPTION;

        return new DriverPropertyInfo[] { driverPropertyInfo };

    }

    /**
     * Method getMajorVersion
     *
     * @return .
     */
    public int getMajorVersion() {

        return 1;

    }

    /**
     * Method getMinorVersion
     *
     * @return .
     */
    public int getMinorVersion() {

        return 1;

    }

    /**
     * Method jdbcCompliant
     *
     * @return .
     */
    public boolean jdbcCompliant() {

        return true;

    }

    //-------------------------------------------------------------------------

    /**
     * Method main
     *
     * @param args .
     */
    public static void main(String[] args) {

        try {

            Class.forName(ProxyDriver.class.getName());

        } catch (java.lang.ClassNotFoundException e) {

            System.out.println("Error loading driver");

        }

        Properties info = new Properties();

        info.setProperty(REAL_DRIVER_PROPERTY, "oracle.jdbc.driver.OracleDriver");
        info.setProperty("user", "scott");
        info.setProperty("password", "tiger");

        try {

            Connection connection = DriverManager.getConnection("LOG:jdbc:oracle:thin:@localhost:1521:orcl", info);

            System.out.println("Connected");
            connection.createStatement();
            connection.getAutoCommit();
            connection.setAutoCommit(false);
            connection.getAutoCommit();
            connection.getMetaData();
            connection.getTransactionIsolation();
            connection.isClosed();
            connection.isReadOnly();
            connection.nativeSQL("select sysdate from dual");
            connection.close();
            connection.isClosed();

        } catch (SQLException e) {

            System.out.println("Error getting connection.");
            e.printStackTrace();

        }

    }

}
