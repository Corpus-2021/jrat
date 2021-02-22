package org.shiftone.jrat.test;

import junit.framework.TestCase;

import org.apache.bcel.Repository;

import org.apache.bcel.classfile.JavaClass;

import org.shiftone.jrat.inject.BcelUtil;

import org.shiftone.jrat.test.dummy.CrashTestDummy;
import org.shiftone.jrat.test.dummy.JuniorDummy;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.io.ObjectStreamClass;
import java.io.Serializable;


/**
 * Class SerialVersionUIDTestCase
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.11 $
 */
public class SerialVersionUIDTestCase extends TestCase implements Serializable {

    private static final Log LOG = LogFactory.getLogger(SerialVersionUIDTestCase.class);

    /**
     * Method testJuniorDummy
     *
     * @param klass .
     *
     * @throws Exception
     */
    public void runTest(Class klass) throws Exception {

        JavaClass         jlass = Repository.lookupClass(klass.getName());
        ObjectStreamClass osc  = ObjectStreamClass.lookup(klass);
        long              goal = osc.getSerialVersionUID();

        LOG.info(osc);

        long result = BcelUtil.computeSerialVersionUID(jlass);

        assertEquals(klass.getName() + " serialVersionUID", goal, result);

    }

    /**
     * Method testJuniorDummy
     *
     * @throws Exception
     */
    public void testJuniorDummy() throws Exception {

        runTest(JuniorDummy.class);

    }

    /**
     * Method testCrashTestDummy
     *
     * @throws Exception
     */
    public void testCrashTestDummy() throws Exception {

        runTest(CrashTestDummy.class);

    }

    /**
     * Method testThisTestCase
     *
     * @throws Exception
     */
    public void testThisTestCase() throws Exception {

        runTest(this.getClass());

    }

    /**
     * Method testStringUtilTestCase
     *
     * @throws Exception
     */
    public void testStringUtilTestCase() throws Exception {

        runTest(StringUtilTestCase.class);

    }

}
