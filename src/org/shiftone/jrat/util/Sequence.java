package org.shiftone.jrat.util;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;


/**
 * Class Sequence
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.7 $
 */
public class Sequence {

    private static final Log LOG       = LogFactory.getLogger(Sequence.class);
    private long             increment = 1;
    private long             value;

    /**
     * Method Sequence
     */
    public Sequence() {

        value = 0;

    }

    /**
     * Method Sequence
     *
     * @param initialValue .
     */
    public Sequence(long initialValue) {

        value = initialValue;

    }

    /**
     * Method getCurrentValue
     *
     * @return .
     */
    public long getCurrentValue() {

        return value;

    }

    /**
     * Method getNextValue
     *
     * @return .
     */
    public synchronized long getNextValue() {

        value += increment;

        return value;

    }

}
