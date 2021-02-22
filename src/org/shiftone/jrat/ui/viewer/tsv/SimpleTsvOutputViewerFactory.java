package org.shiftone.jrat.ui.viewer.tsv;

import org.shiftone.jrat.core.OutputViewerException;

import org.shiftone.jrat.core.spi.OutputViewerFactory;
import org.shiftone.jrat.core.spi.RuntimeOutput;

import org.shiftone.jrat.ui.util.PercentTableCellRenderer;

import org.shiftone.jrat.util.IOUtil;
import org.shiftone.jrat.util.Percent;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.awt.Component;

import java.io.IOException;
import java.io.InputStream;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;


/**
 * Class SimpleTsvOutputViewerFactory
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.6 $
 */
public class SimpleTsvOutputViewerFactory implements OutputViewerFactory {

    private static final Log LOG = LogFactory.getLogger(SimpleTsvOutputViewerFactory.class);

    /**
     * Method createViewerComponent
     *
     * @throws OutputViewerException
     */
    public Component createViewerComponent(RuntimeOutput runtimeOutput)
        throws OutputViewerException {

        InputStream   inputStream = null;
        TsvTableModel model = new TsvTableModel();

        try {

            inputStream = runtimeOutput.openInputStream();
            model.load(inputStream);

        } catch (IOException e) {

            throw new OutputViewerException("error loading tab separated file (tsv)", e);

        } finally {

            IOUtil.close(inputStream);

        }

        JTable       table      = new JTable(model);
        JScrollPane  scrollPane = new JScrollPane(table);
        JTableHeader header     = table.getTableHeader();

        table.setDefaultRenderer(Percent.class, new PercentTableCellRenderer());
        table.setDefaultRenderer(Integer.class, new PercentTableCellRenderer());
        table.setDefaultRenderer(Long.class, new PercentTableCellRenderer());
        table.setDefaultRenderer(Double.class, new PercentTableCellRenderer());
        table.setDefaultRenderer(Float.class, new PercentTableCellRenderer());

        //
        table.setColumnSelectionAllowed(false);
        header.addMouseListener(new TsvMouseAdapter(table));

        return scrollPane;

    }

}
