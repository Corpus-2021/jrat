package org.shiftone.jrat.core;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;


/**
 * OutputViewerException is throw when there is an error creating a viewer for a JRat output file. This is probably the result of
 * the file format being corrupt.
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.5 $
 */
public class OutputViewerException extends JRatException {

    private static final Log LOG = LogFactory.getLogger(OutputViewerException.class);

    /**
     * Constructor OutputViewerException
     *
     * @param message
     */
    public OutputViewerException(String message) {
        super(message);

    }

    /**
     * Constructor OutputViewerException
     *
     * @param message
     * @param cause
     */
    public OutputViewerException(String message, Throwable cause) {
        super(message, cause);

    }

}
