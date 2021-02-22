package org.shiftone.jrat.test;

import junit.framework.TestCase;

import org.shiftone.jrat.util.IOUtil;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.io.File;


/**
 * Class IOTestCase
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.8 $
 */
public class IOTestCase extends TestCase {

    private static final Log LOG = LogFactory.getLogger(IOTestCase.class);

    /**
     * Method testExt
     *
     * @throws Exception
     */
    public void testExt() throws Exception {

        String ext = IOUtil.getExtention(new File("abc.txt"));

        assertEquals(ext, "txt");

    }

    /**
     * Method testNoExt
     *
     * @throws Exception
     */
    public void testNoExt() throws Exception {

        String ext = IOUtil.getExtention(new File("abc"));

        assertEquals(ext, "");

    }

    /**
     * Method testEndWithDot
     *
     * @throws Exception
     */
    public void testEndWithDot() throws Exception {

        String ext = IOUtil.getExtention(new File("abc."));

        assertEquals(ext, "");

    }

    /**
     * Method testNearestExistingParent1
     */
    public void testNearestExistingParent1() {

        File a1 = new File("C:/Program Files/x/s/ed/f");
        File a2 = new File("C:/Program Files");

        assertEquals(IOUtil.getNearestExistingParent(a1), a2);

    }

    /**
     * Method testNearestExistingParent2
     */
    public void testNearestExistingParent2() {

        File a1 = new File("C:/x");

        System.out.println(IOUtil.getNearestExistingParent(a1));

    }

}
