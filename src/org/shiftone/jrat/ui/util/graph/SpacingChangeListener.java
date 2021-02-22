package org.shiftone.jrat.ui.util.graph;



import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import javax.swing.BoundedRangeModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 * This is a bridge between a BoundedRangeModel and a GraphComponent.  Changes to the value of the
 * BoundedRangeModel cause the "pointGap" of the GraphComponent component to be updated.
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.3 $
 */
public class SpacingChangeListener implements ChangeListener
{

    private static final Log     LOG               = LogFactory.getLogger(SpacingChangeListener.class);
    private static final boolean CONTINUOUS_UPDATE = true;
    private GraphComponent       graphComponent    = null;

    /**
     * Constructor SpacingChangeListener
     *
     *
     * @param graphComponent
     */
    public SpacingChangeListener(GraphComponent graphComponent)
    {
        this.graphComponent = graphComponent;
    }

    /**
     * Method stateChanged
     */
    public void stateChanged(ChangeEvent e)
    {

        Object            source     = e.getSource();
        BoundedRangeModel rangeModel = null;

        if (source instanceof BoundedRangeModel)
        {
            rangeModel = (BoundedRangeModel) source;

            if ((CONTINUOUS_UPDATE) || (rangeModel.getValueIsAdjusting() == false))
            {
                graphComponent.setPointGap(rangeModel.getValue() + 1);
            }
        }
    }
}
