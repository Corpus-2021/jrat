package org.shiftone.jrat.provider.rate.ui.graph;



import org.shiftone.jrat.core.Accumulator;
import org.shiftone.jrat.provider.rate.ui.RateModel;
import org.shiftone.jrat.ui.util.graph.AbstractGraph;


/**
 * Class MeanDurationGraph
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.5 $
 */
public class MeanDurationGraph extends AbstractGraph
{

    private RateModel rateModel    = null;
    private int       methodNumber = 0;

    /**
     * Constructor MeanDurationGraph
     *
     *
     * @param rateModel
     * @param methodNumber
     *
     */
    public MeanDurationGraph(RateModel rateModel, int methodNumber)
    {

        this.methodNumber = methodNumber;
        this.rateModel    = rateModel;

        setColor(rateModel.getMethodColor(methodNumber));
    }

    /**
     * Method getPointCount
     */
    public int getPointCount()
    {
        return rateModel.getSampleCount();
    }

    /**
     * Method getValue
     */
    public long getValue(int index)
    {

        Accumulator acc           = rateModel.getAccumulator(index, methodNumber);
        long        totalDuration = acc.getTotalDuration();
        long        totalExits    = acc.getTotalExits();

        return (totalExits != 0)
               ? totalDuration / totalExits
               : 0;
    }
}
