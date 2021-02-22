package org.shiftone.jrat.ui.util;



import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;


/**
 * Class ColorTableCellRenderer renders a Color int a table by displaying a round rectangular swatch.
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.4 $
 */
public class ColorTableCellRenderer extends DefaultTableCellRenderer
{

    private static final Log LOG   = LogFactory.getLogger(ColorTableCellRenderer.class);
    private Object           value = null;

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
    public void paintComponent(Graphics g)
    {

        if (value instanceof Color)
        {
            int x = 4;
            int y = 4;
            int w = getWidth() - 8;
            int h = getHeight() - 9;

            g.setColor(Color.DARK_GRAY);
            g.fillRoundRect(x - 2, y - 2, w + 4, h + 4, 5, 5);
            g.setColor((Color) value);
            g.fillRoundRect(x, y, w, h, 5, 5);
        }
    }
}
