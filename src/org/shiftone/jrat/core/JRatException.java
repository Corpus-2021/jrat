package org.shiftone.jrat.core;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;


/**
 * Class JRatException
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.6 $
 */
public class JRatException extends Exception {

    private static final Log LOG = LogFactory.getLogger(JRatException.class);

    /**
     * Constructor JRatException
     *
     * @param message
     */
    public JRatException(String message) {
        super(message);

    }

    /**
     * Constructor JRatException
     *
     * @param message
     * @param cause
     */
    public JRatException(String message, Throwable cause) {
        super(message, cause);

    }

}
