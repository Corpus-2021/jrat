package org.shiftone.jrat.ui.util;



import org.shiftone.jrat.util.Percent;
import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import java.text.DecimalFormat;


/**
 * Class PercentTableCellRenderer
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.2 $
 */
public class PercentTableCellRenderer extends DefaultTableCellRenderer
{

    private static final Log   LOG                 = LogFactory.getLogger(PercentTableCellRenderer.class);
    private Object             value               = null;
    private DecimalFormat      floatDecimalFormat  = new DecimalFormat("#,##0.00");
    private DecimalFormat      doubleDecimalFormat = new DecimalFormat("#,##0.0000");
    private DecimalFormat      longDecimalFormat   = new DecimalFormat("###,###,###");
    private static final Color COLOR_XOR           = new Color(0xccccFF ^ 0xFFFFFF);

    /**
     * Method getTableCellRendererComponent
     */
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {

        this.value = value;

        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }

    /**
     * Method paint
     */
    public void paint(Graphics g)
    {

        super.paint(g);

        if (value instanceof Percent)
        {
            double pct = ((Percent) value).doubleValue();

            if (pct > 100.0)
            {
                pct = 100.0;
            }

            int w = (int) ((getWidth() * pct) / 100.0);

            g.setXORMode(COLOR_XOR);
            g.fillRect(0, 0, w, getHeight());
        }
    }

    /**
     * Method setValue.
     * synchronized because DecimalFormat is not thread safe
     */
    protected synchronized void setValue(Object value)
    {

        if (value == null)
        {
            value = "";
        }
        else if (value instanceof Number)
        {
            setHorizontalAlignment(SwingConstants.RIGHT);

            Number num = (Number) value;

            if ((value instanceof Integer) || (value instanceof Long))
            {
                value = longDecimalFormat.format(num);
            }
            else if (value instanceof Float)
            {
                value = floatDecimalFormat.format(num);
            }
            else if (value instanceof Double)
            {
                value = doubleDecimalFormat.format(num);
            }
        }

        super.setValue(value);
    }
}
