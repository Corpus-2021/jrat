package org.shiftone.jrat.provider.rate;



import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;
import org.shiftone.jrat.core.Accumulator;

import java.util.TimerTask;


/**
 * Class RateTimerTask
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.4 $
 */
public class RateTimerTask extends TimerTask
{

    private static final Log         LOG            = LogFactory.getLogger(RateTimerTask.class);
    private Accumulator              accumulator    = new Accumulator();
    private RateMethodHandlerFactory handlerFactory = null;

    /**
     * Constructor RateTimerTask
     *
     *
     * @param handlerFactory
     */
    public RateTimerTask(RateMethodHandlerFactory handlerFactory)
    {
        this.handlerFactory = handlerFactory;
    }

    /**
     * Method run
     */
    public void run()
    {

        long    start   = System.currentTimeMillis();
        boolean success = false;

        accumulator.onMethodStart();

        try
        {
            handlerFactory.writeSample();

            success = true;
        }
        catch(Exception e) {
        	LOG.error("error writing sample",e);
        }
        finally
        {
            accumulator.onMethodFinish(start - System.currentTimeMillis(), success);
        }
    }

    /**
     * Method getTaskAccumulator
     */
    public Accumulator getTaskAccumulator()
    {
        return accumulator;
    }
	

	public boolean cancel()
	{
		LOG.info("Accumulator stats for rate timer task : " + Accumulator.toCSV(accumulator));
		return super.cancel();
	}

}
