package org.shiftone.jrat.test;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.shiftone.jrat.core.Monitor;


/**
 * Class MonitorApiTestCase
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.2 $
 */
public class MonitorApiTestCase extends TestCase {

    public void testOne() throws Exception {

        Monitor monitor = Monitor.start(this, "testOne", "()V");

        Thread.sleep(100);
        monitor.stop();

    }

    public void testStopStop() throws Exception {

        Monitor monitor = Monitor.start(this, "testStopStop", "()V");

        Thread.sleep(100);
        monitor.stop();

        try {

            monitor.stop();

        } catch (Exception e) {

            return;

        }

        Assert.fail("stop did not cause exception");

    }

    private void doNest(int countDown) throws Exception {

        Monitor monitor = null;

        if (countDown <= 0) {

            return;

        }

        monitor = Monitor.start(this, "testStopStop", "()V");

        Thread.sleep(5);
        doNest(countDown - 1);
        Thread.sleep(5);

        monitor.stop();

    }

    public void testNest() throws Exception {

        doNest(200);

    }

}
