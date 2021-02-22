package org.shiftone.jrat.core;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;


/**
 * Class ParseException
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.5 $
 */
public class ParseException extends JRatException {

    private static final Log LOG = LogFactory.getLogger(ParseException.class);

    /**
     * Constructor ParseException
     *
     * @param message
     * @param cause
     */
    public ParseException(String message, Throwable cause) {
        super(message, cause);

    }

    /**
     * Constructor ParseException
     *
     * @param message
     */
    public ParseException(String message) {
        super(message);

    }

}
