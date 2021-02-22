package org.shiftone.jrat.test.harness;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;


/**
 * Class TestHarness
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.7 $
 */
public class TestHarness
{
    private static final Log               LOG    = LogFactory.getLogger(TestHarness.class);
    private static final VirtualMethodCall CALL_A = new VirtualMethodCall("a.b.c", "This", "()");
    private static final VirtualMethodCall CALL_B = new VirtualMethodCall("a.b.c", "Is", "()");
    private static final VirtualMethodCall CALL_C = new VirtualMethodCall("a.b.c", "Another", "()");
    private static final VirtualMethodCall CALL_D = new VirtualMethodCall("a.b.c", "Test", "()");
    private static final VirtualMethodCall CALL_E = new VirtualMethodCall("a.b.c", "Harness", "()");
    private static final VirtualMethodCall CALL_F = new VirtualMethodCall("a.b.c", "Method", "()");
    private static final VirtualMethodCall CALL_G = new VirtualMethodCall("a.b.c", "Runner", "()");

    static
    {
        CALL_A.addChildMethodCall(CALL_B);
        CALL_A.addChildMethodCall(CALL_C);
        CALL_A.addChildMethodCall(CALL_D);
        CALL_A.addChildMethodCall(CALL_G);

        //
        CALL_B.addChildMethodCall(CALL_C);
        CALL_B.addChildMethodCall(CALL_D);
        CALL_B.addChildMethodCall(CALL_E);
        CALL_B.addChildMethodCall(CALL_F);
        CALL_B.addChildMethodCall(CALL_G);

        //
        CALL_C.addChildMethodCall(CALL_D);
        CALL_C.addChildMethodCall(CALL_E);
        CALL_C.addChildMethodCall(CALL_F);
        CALL_C.addChildMethodCall(CALL_G);

        //
        CALL_D.addChildMethodCall(CALL_E);
        CALL_D.addChildMethodCall(CALL_F);
        CALL_D.addChildMethodCall(CALL_G);

        //
        CALL_E.addChildMethodCall(CALL_F);
        CALL_E.addChildMethodCall(CALL_G);

        //
        CALL_F.addChildMethodCall(CALL_G);
    }

    /**
     * Method main
     *
     * @param args .
     */
    public static void main(String[] args)
    {
        Runnable runnableA = new HarnessRunnable(10, CALL_A);
        Runnable runnableB = new HarnessRunnable(200, CALL_B);
        Runnable runnableC = new HarnessRunnable(300, CALL_C);

        System.out.println("testing " + System.getProperty("jrat.factory"));
        new Thread(runnableA).start();
        new Thread(runnableB).start();
        new Thread(runnableC).start();
    }
}
