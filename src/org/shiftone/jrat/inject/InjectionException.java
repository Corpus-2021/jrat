package org.shiftone.jrat.inject;

import org.shiftone.jrat.core.JRatException;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;


/**
 * Class InjectionException
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.8 $
 */
public class InjectionException extends JRatException {

    private static final Log LOG = LogFactory.getLogger(InjectionException.class);

    /**
     * Constructor InjectionException
     *
     * @param message
     */
    public InjectionException(String message) {
        super(message);

    }

    /**
     * Constructor InjectionException
     *
     * @param message
     * @param cause
     */
    public InjectionException(String message, Throwable cause) {
        super(message, cause);

    }

}
