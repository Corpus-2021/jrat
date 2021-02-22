package org.shiftone.jrat.core;


/**
 * Class ConfigurationException
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.4 $
 */
public class ConfigurationException extends JRatException {

    /**
     * Constructor ConfigurationException
     *
     * @param message
     */
    public ConfigurationException(String message) {
        super(message);

    }

    /**
     * Constructor ConfigurationException
     *
     * @param message
     * @param cause
     */
    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);

    }

}
