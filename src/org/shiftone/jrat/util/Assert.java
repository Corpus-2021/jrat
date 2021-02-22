package org.shiftone.jrat.util;


/**
 * Class Assert
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.2 $
 */
public class Assert {

    /**
     * method
     *
     * @param obj .
     */
    public static void assertNotNull(Object obj) {

        if (obj == null) {

            throw new AssertionFailedException("value is unexpectedly null");

        }

    }

    /**
     * method
     *
     * @param message .
     * @param obj .
     */
    public static void assertNotNull(String message, Object obj) {

        if (obj == null) {

            throw new AssertionFailedException("value of " + message + " is unexpectedly null");

        }

    }

}
