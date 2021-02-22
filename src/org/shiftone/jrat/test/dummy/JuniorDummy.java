package org.shiftone.jrat.test.dummy;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.io.Serializable;


/**
 * Class JuniorDummy
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.3 $
 */
public class JuniorDummy implements Serializable, Cloneable
{
    private static int    privateInt = 0;
    static final int      x       = 1;
    private static String stringA = "test";

    static
    {
        stringA = JuniorDummy.class.getName();
    }

    private Log LOG = LogFactory.getLogger(CrashTestDummy.class);
    public String string1            = stringA;
    private final String stringC     = stringA;
    private transient String stringD = stringA;
    public int  a;
    public int  b;
    public int  c;

    /**
     * Constructor JuniorDummy
     */
    public JuniorDummy()
    {
    }

    /**
     * Method x
     *
     * @return 1 + CrashTestDummy.class.getName().length()
     */
    public int x()
    {
        return 1 + CrashTestDummy.class.getName().length();
    }

    /**
     * Method y
     *
     * @return 1
     */
    public int y()
    {
        return 1;
    }
}
