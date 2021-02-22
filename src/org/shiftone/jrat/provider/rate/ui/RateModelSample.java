package org.shiftone.jrat.provider.rate.ui;



import org.shiftone.jrat.core.Accumulator;
import org.shiftone.jrat.core.ParseException;
import org.shiftone.jrat.util.StringUtil;
import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;


/**
 * Data structure holds the data for a sample.
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.6 $
 */
public class RateModelSample
{

    private static final Log         LOG              = LogFactory.getLogger(RateModelSample.class);
    private static final Accumulator ZERO_ACCUMULATOR = new Accumulator();
    private long                     upTimeMs         = 0;
    private long                     freeMemory       = 0;
    private long                     maxMemory        = 0;
    private Accumulator[]            accumulators     = null;

    /**
     * Constructor RateModelSample
     *
     *
     * @param tokens
     *
     * @throws ParseException
     */
    RateModelSample(String[] tokens) throws ParseException
    {
        load(tokens);
    }

    /**
     * Method load
     *
     * @throws ParseException
     */
    void load(String[] tokens) throws ParseException
    {

        String[] headerTokens = StringUtil.tokenize(tokens[1], ",", false);

        upTimeMs     = Long.parseLong(headerTokens[0]);
        freeMemory   = Long.parseLong(headerTokens[1]);
        maxMemory    = Long.parseLong(headerTokens[2]);
        accumulators = new Accumulator[tokens.length - 1];

        for (int i = 0; i < accumulators.length - 2; i++)
        {
            accumulators[i] = Accumulator.fromCSV(tokens[i + 2]);
        }
    }

    /**
     * Method getAccumulator
     */
    public Accumulator getAccumulator(int index)
    {

        return (index < accumulators.length)
               ? accumulators[index]
               : ZERO_ACCUMULATOR;
    }

    /**
     * Method getFreeMemory
     */
    public long getFreeMemory()
    {
        return freeMemory;
    }

    /**
     * Method getMaxMemory
     */
    public long getMaxMemory()
    {
        return maxMemory;
    }

    /**
     * Method getUpTimeMs
     */
    public long getUpTimeMs()
    {
        return upTimeMs;
    }
}
