package org.shiftone.jrat.ui.util.graph;



import java.awt.Color;


/**
 * Interface GraphModel
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.1 $
 */
public interface GraphModel
{

    /**
     * Method getName
     */
    String getName();

    /**
     * Method getColor
     */
    Color getColor();

    /**
     * Method getPointCount
     */
    int getPointCount();

    /**
     * Method getMaxValue
     */
    long getMaxValue();

    /**
     * Method getMinValue
     */
    long getMinValue();

    /**
     * Method getValue
     */
    long getValue(int index);
}
