package org.shiftone.jrat.test.harness;



import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;


/**
 * Class HarnessRunnable
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.1 $
 */
public class HarnessRunnable implements Runnable
{

    private static final Log  LOG               = LogFactory.getLogger(HarnessRunnable.class);
    private int               calls             = 1;
    private VirtualMethodCall virtualMethodCall = null;

    /**
     * Constructor HarnessRunnable
     *
     *
     * @param calls
     * @param virtualMethodCall
     */
    public HarnessRunnable(int calls, VirtualMethodCall virtualMethodCall)
    {
        this.calls             = calls;
        this.virtualMethodCall = virtualMethodCall;
    }

    /**
     * Method run
     */
    public void run()
    {

        for (int i = 0; i < calls; i++)
        {
            virtualMethodCall.simulateCall();
        }
    }
}
