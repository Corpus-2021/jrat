package org.shiftone.jrat.ui;

import org.shiftone.jrat.util.SavedProperties;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.io.File;

import java.util.Properties;


/**
 * Class UserSettings
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.10 $
 */
public class UserSettings {

    private static final Log         LOG                    = LogFactory.getLogger(UserSettings.class);
    private static final Properties  PROPS                  = SavedProperties.USER_PROPERTIES;
    private static final String      PROP_LAST_OPENED_FILE  = "ui.lastOpenedOutputFile";
    private static final String      PROP_LAST_INJECTED_JAR = "ui.lastInjectedJar";
    private static final String      PROP_LAST_INJECTED_DIR = "ui.lastInjectedDir";
    private static final String      PROP_BCEL_JAR_FILE     = "ui.jar.bcel";
    public static final UserSettings INSTANCE               = new UserSettings();

    /**
     * Constructor UserSettings
     */
    private UserSettings() {

    }

    /**
     * Method getBcelJarFile
     *
     * @return .
     */
    public File getBcelJarFile() {

        return toFile(PROPS.getProperty(PROP_BCEL_JAR_FILE));

    }

    /**
     * Method setLastOpenedOutputFile
     *
     * @param fileName .
     */
    public void setBcelJarFile(File fileName) {

        PROPS.setProperty(PROP_BCEL_JAR_FILE, fileName.getAbsolutePath());

    }

    /**
     * Method getLastOpenedOutputFile
     *
     * @return .
     */
    public File getLastOpenedOutputFile() {

        return toFile(PROPS.getProperty(PROP_LAST_OPENED_FILE));

    }

    /**
     * Method setLastOpenedOutputFile
     *
     * @param fileName .
     */
    public void setLastOpenedOutputFile(File fileName) {

        PROPS.setProperty(PROP_LAST_OPENED_FILE, fileName.getAbsolutePath());

    }

    /**
     * Method getLastInjectedJar
     *
     * @return .
     */
    public File getLastInjectedJar() {

        return toFile(PROPS.getProperty(PROP_LAST_INJECTED_JAR));

    }

    /**
     * Method setLastOpenedOutputFile
     *
     * @param fileName .
     */
    public void setLastInjectedJar(File fileName) {

        PROPS.setProperty(PROP_LAST_INJECTED_JAR, fileName.getAbsolutePath());

    }

    /**
     * Method getLastInjectedDir
     *
     * @return .
     */
    public File getLastInjectedDir() {

        return toFile(PROPS.getProperty(PROP_LAST_INJECTED_DIR));

    }

    /**
     * Method setLastInjectedDir
     *
     * @param fileName .
     */
    public void setLastInjectedDir(File fileName) {

        PROPS.setProperty(PROP_LAST_INJECTED_DIR, fileName.getAbsolutePath());

    }

    /**
     * Method toFile
     *
     * @param fileName .
     *
     * @return .
     */
    private File toFile(String fileName) {

        return (fileName == null) ? null : new File(fileName);

    }

}
