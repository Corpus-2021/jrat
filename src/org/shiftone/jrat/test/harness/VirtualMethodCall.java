package org.shiftone.jrat.test.harness;



import org.shiftone.jrat.core.HandlerFactory;
import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.core.spi.MethodHandler;
import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;


/**
 * Class VirtualMethodCall
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.6 $
 */
public class VirtualMethodCall
{

    private static final Log LOG        = LogFactory.getLogger(VirtualMethodCall.class);
    private static Random    random     = new Random();
    private List             memoryLeak = new LinkedList();
    private List             children   = new ArrayList();
    private MethodKey        methodKey  = null;
    private MethodHandler    handler    = null;
    private long             childCalls;
    private long             minDuration;
    private long             randDuration;

    /**
     * Constructor VirtualMethodCall
     *
     *
     * @param className
     * @param methodName
     * @param signature
     */
    public VirtualMethodCall(String className, String methodName, String signature)
    {

        this.methodKey    = new MethodKey(className, methodName, signature);
        this.handler      = HandlerFactory.getMethodHandler(methodKey);
        this.childCalls   = positiveRand(20);
        this.minDuration  = positiveRand(10);
        this.randDuration = positiveRand(10) + 1;
    }

    /**
     * Method positiveRand
     */
    private static long positiveRand(long max)
    {

        // System.out.println("max = " + max);
        return (Math.abs(random.nextLong()) % max);
    }

    /**
     * Method addChildMethodCall
     */
    public void addChildMethodCall(VirtualMethodCall virtualMethodCall)
    {
        children.add(virtualMethodCall);
    }

    /**
     * Method simulateCall
     */
    public void simulateCall()
    {

        long start = System.currentTimeMillis();

        //System.out.println("begin : " + methodKey);
        handler.onMethodStart(this, null);
        simulateWork();
        simulateCallChildren();
        handler.onMethodFinish(this, null, null, System.currentTimeMillis() - start, true);

        // System.out.println("end : " + methodKey);
    }

    /**
     * Method simulateWork
     */
    private void simulateWork()
    {

        long time = minDuration + (positiveRand(randDuration));

        try
        {
            Thread.sleep(time);
            memoryLeak.add(new String("leak"));
        }
        catch (Exception e) {}
    }

    /**
     * Method simulateCallChildren
     */
    private void simulateCallChildren()
    {

        VirtualMethodCall call = null;

        if (children.size() > 0)
        {
            for (int i = 0; i < childCalls; i++)
            {
                call = (VirtualMethodCall) children.get((int) positiveRand(children.size()));

                call.simulateCall();
            }
        }
    }
}
