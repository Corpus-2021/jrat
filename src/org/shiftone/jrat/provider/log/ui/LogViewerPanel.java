package org.shiftone.jrat.provider.log.ui;

import org.shiftone.jrat.core.spi.RuntimeOutput;

import org.shiftone.jrat.provider.log.io.LogFileFormatException;
import org.shiftone.jrat.provider.log.io.RandomAccessLogFile;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.awt.BorderLayout;

import java.io.EOFException;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;


/**
 * class
 *
 * @author $author$
 * @version $Revision: 1.6 $
 */
public class LogViewerPanel extends JPanel {

    private static final Log    LOG                 = LogFactory.getLogger(LogViewerPanel.class);
    private static final int[]  TABLE_COLUMN_WIDTHS = { 40, 80, 120, 150, 150, 10, 100 };
    private RandomAccessLogFile logFile;
    private LogFileTableModel   tableModel;
    private JTable              table;
    private JScrollPane         scrollPane;

    /**
     * Constructor for LogViewerPanel
     *
     * @param runtimeOutput .
     */
    public LogViewerPanel(RuntimeOutput runtimeOutput)
        throws IOException {

        logFile = new RandomAccessLogFile(runtimeOutput.getInputFile());

        try {

            logFile.initialize();

        } catch (EOFException e) {

            LOG.error("reached EOF unexpectedly", e);

        } catch (LogFileFormatException e) {

            LOG.error("Error with log file format", e);

        }

        tableModel     = new LogFileTableModel(logFile);
        table          = new JTable(tableModel);
        scrollPane     = new JScrollPane(table);

        setLayout(new BorderLayout());

        add(scrollPane, BorderLayout.CENTER);

        TableColumnModel columnModel = table.getColumnModel();

        for (int i = 0; i < TABLE_COLUMN_WIDTHS.length; i++) {

            TableColumn column = columnModel.getColumn(i);

            column.setPreferredWidth(TABLE_COLUMN_WIDTHS[i]);

        }

    }

}
