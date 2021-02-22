package org.shiftone.jrat.util;

import org.shiftone.jrat.core.ConfigurationException;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;


/**
 * Class ResourceUtil
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.11 $
 */
public class ResourceUtil {

    private static final Log   LOG           = LogFactory.getLogger(ResourceUtil.class);
    private static ClassLoader CLASS_LOADER  = ResourceUtil.class.getClassLoader();
    private static Map         resourceCache = new Hashtable();

    /**
     * Method newInstance
     *
     * @param className .
     *
     * @return .
     *
     * @throws ConfigurationException
     */
    public static Object newInstance(String className)
        throws ConfigurationException {

        Class  klass    = null;
        Object instance = null;

        Assert.assertNotNull("className", className);
        LOG.debug("newInstance(" + className + ")");

        try {

            klass = CLASS_LOADER.loadClass(className);

        } catch (Exception e) {

            throw new ConfigurationException("unable to load class '" + className + "'", e);

        }

        try {

            instance = klass.newInstance();

        } catch (Exception e) {

            throw new ConfigurationException("unable to instantiate '" + className + "'", e);

        }

        return instance;

    }

    /**
     * Method loadResourceAsStream
     *
     * @param resourceName .
     *
     * @return .
     *
     * @throws ConfigurationException
     */
    public static InputStream loadResourceAsStream(String resourceName)
        throws ConfigurationException {

        InputStream inputStream = null;

        Assert.assertNotNull("resourceName", resourceName);

        inputStream = CLASS_LOADER.getResourceAsStream(resourceName);

        if (inputStream == null) {

            throw new ConfigurationException("unable to locate resource : " + resourceName);

        }

        return inputStream;

    }

    /**
     * Method fetchResource
     *
     * @param name .
     *
     * @return .
     *
     * @throws ConfigurationException
     */
    private static String fetchResource(String name) throws ConfigurationException {

        Reader       reader      = null;
        StringBuffer sb          = null;
        InputStream  inputStream = null;
        int          c           = 0;
        char[]       buffer      = new char[1025 * 1];

        Assert.assertNotNull("name", name);
        LOG.debug("fetchResource : " + name);

        inputStream     = loadResourceAsStream(name);
        reader          = new InputStreamReader(inputStream);
        sb              = new StringBuffer();

        try {

            for (c = 0; c >= 0; c = reader.read(buffer)) {

                sb.append(buffer, 0, c);

            }

        } catch (IOException e) {

            throw new ConfigurationException("unable to read resource data : " + name, e);

        }

        return sb.toString();

    }

    /**
     * Method loadResource
     *
     * @param name .
     *
     * @return .
     *
     * @throws ConfigurationException
     */
    public static String loadResource(String name) throws ConfigurationException {

        String resource = null;

        Assert.assertNotNull("name", name);
        resource = (String) resourceCache.get(name);

        if (resource == null) {

            LOG.info("loading and caching resource : " + name);

            resource = fetchResource(name);

            resourceCache.put(name, resource);

        }

        return resource;

    }

    /**
     * Method getResourceAsProperties
     *
     * @param name .
     *
     * @return .
     *
     * @throws ConfigurationException
     */
    public static Properties getResourceAsProperties(String name)
        throws ConfigurationException {

        InputStream inputStream = null;
        Properties  props = null;

        Assert.assertNotNull("name", name);

        LOG.debug("getResourceAsProperties : " + name);

        inputStream     = loadResourceAsStream(name);
        props           = new Properties();

        try {

            props.load(inputStream);

        } catch (Exception e) {

            throw new ConfigurationException("unable to load properties from resource : " + name, e);

        }

        return props;

    }

}
