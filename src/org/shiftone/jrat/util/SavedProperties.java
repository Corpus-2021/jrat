package org.shiftone.jrat.util;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.Map;
import java.util.Properties;


/**
 * Class SavedProperties extends Properties and does write throughs to a designated file to persist property values.  This is not
 * a relable way to store anything critical.  This class silently failes if there is an error reading or writing the file.
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.8 $
 */
public class SavedProperties extends Properties {

    private static final Log       LOG                  = LogFactory.getLogger(SavedProperties.class);
    private static final String    VERSION              = StringUtil.revision("$Revision: 1.8 $");
    private static final String    DEFAULT_HEADER       = "ShiftOne JRat SavedProperties " + VERSION;
    private static final String    USER_HOME_DIR        = System.getProperty("user.home");
    private static final String    PROPERTIES_FILE_NAME = ".jrat.properties";
    private static final String    PROPERTIES_FILE_PATH = USER_HOME_DIR + File.separator + PROPERTIES_FILE_NAME;
    public static final Properties USER_PROPERTIES      = new SavedProperties(PROPERTIES_FILE_PATH);

    //
    private String header    = DEFAULT_HEADER;
    private File   storeFile = null;

    /**
     * Constructor SavedProperties
     *
     * @param storeFileName
     */
    public SavedProperties(String storeFileName) {
        this(new File(storeFileName));

    }

    /**
     * Constructor SavedProperties
     *
     * @param storeFile
     */
    public SavedProperties(File storeFile) {

        Assert.assertNotNull("storeFile", storeFile);
        this.storeFile = storeFile;

        load();

    }

    /**
     * Method store
     */
    private synchronized void store() {

        OutputStream outputStream = null;

        try {

            outputStream = new FileOutputStream(storeFile);

            super.store(outputStream, header);

        } catch (Exception e) {

            LOG.warn("unable to store properties to file : " + storeFile);
            IOUtil.close(outputStream);

        }

    }

    /**
     * Method load
     */
    private synchronized void load() {

        InputStream inputStream = null;

        try {

            inputStream = new FileInputStream(storeFile);

            super.load(inputStream);

        } catch (Exception e) {

            super.clear();
            LOG.warn("unable to load properties from file : " + storeFile);

        } finally {

            IOUtil.close(inputStream);

        }

    }

    /**
     * Method put
     *
     * @param key .
     * @param value .
     *
     * @return .
     */
    public synchronized Object put(Object key, Object value) {

        Assert.assertNotNull("key", key);
        Assert.assertNotNull("value", value);

        Object o = super.put(key, value);

        store();

        return o;

    }

    /**
     * Method putAll
     *
     * @param t .
     */
    public synchronized void putAll(Map t) {

        Assert.assertNotNull("map", t);
        super.putAll(t);
        store();

    }

    /**
     * Method setProperty
     *
     * @param key .
     * @param value .
     *
     * @return .
     */
    public synchronized Object setProperty(String key, String value) {

        Assert.assertNotNull("key", key);
        Assert.assertNotNull("value", value);

        Object o = super.setProperty(key, value);

        store();

        return o;

    }

    /**
     * Method remove
     *
     * @param key .
     *
     * @return .
     */
    public synchronized Object remove(Object key) {

        Assert.assertNotNull("key", key);

        Object o = super.remove(key);

        store();

        return o;

    }

}
