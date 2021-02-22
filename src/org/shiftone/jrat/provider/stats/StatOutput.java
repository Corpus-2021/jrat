package org.shiftone.jrat.provider.stats;

import org.shiftone.jrat.ui.viewer.tsv.SimpleTsvOutputViewerFactory;
import org.shiftone.jrat.ui.viewer.tsv.TsvTableModel;

import java.io.OutputStream;
import java.io.PrintStream;


/**
 * class
 *
 * @author $author$
 * @version $Revision: 1.5 $
 */
public class StatOutput extends PrintStream
{
    private static final String TAB = "\t";

    /**
     * Constructor for StatOutput
     *
     * @param outputStream .
     */
    public StatOutput(OutputStream outputStream)
    {
        super(outputStream);

        print("viewer=\"");
        print(SimpleTsvOutputViewerFactory.class.getName());
        println("\"");

        print("Class\t");
        print("Method\t");
        print("Sig\t");
        print(TsvTableModel.PREFIX_LONG + "Enters\t");
        print(TsvTableModel.PREFIX_LONG + "Exits\t");
        print(TsvTableModel.PREFIX_LONG + "Errors\t");
        print(TsvTableModel.PREFIX_LONG + "Duration\t");
        print(TsvTableModel.PREFIX_LONG + "Min\t");
        print(TsvTableModel.PREFIX_LONG + "Max\t");
        print(TsvTableModel.PREFIX_DOUBLE + "Mean\t");
        println(TsvTableModel.PREFIX_DOUBLE + "StdDev");
    }

    /**
     * method
     *
     * @param handlers .
     * @param recordUnused .
     */
    public synchronized void printStats(StatMethodHandler[] handlers, boolean recordUnused)
    {
        for (int i = 0; i < handlers.length; i++)
        {
            if ((recordUnused) || (handlers[i].getTotalEnters() > 0))
            {
                printStat(handlers[i]);
            }
        }
    }

    /**
     * method
     *
     * @param handler .
     */
    private synchronized void printStat(StatMethodHandler handler)
    {
        print(handler.getClassName() + TAB);
        print(handler.getMethodName() + TAB);
        print(handler.getSignature() + TAB);
        print(handler.getTotalEnters() + TAB);
        print(handler.getTotalExits() + TAB);
        print(handler.getTotalErrors() + TAB);
        print(handler.getTotalDuration() + TAB);

        printIfNonNull(handler.getMinDuration());
        print(TAB);
        printIfNonNull(handler.getMaxDuration());
        print(TAB);
        printIfNonNull(handler.getAverageDuration());
        print(TAB);
        printIfNonNull(handler.getStdDeviation());
        println();
    }

    /**
     * method
     *
     * @param obj .
     */
    private void printIfNonNull(Object obj)
    {
        if (obj != null)
        {
            print(obj);
        }
    }
}
