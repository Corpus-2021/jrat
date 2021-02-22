package org.shiftone.jrat.provider.log.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.provider.log.io.RandomAccessLogFile;
import org.shiftone.jrat.provider.log.io.message.Message;
import org.shiftone.jrat.provider.log.io.message.MethodMessage;
import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;


/**
 * Class LogFileTableModel
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.4 $
 */
public class LogFileTableModel implements TableModel {

    private static final Log          LOG          = LogFactory.getLogger(LogFileTableModel.class);
    private static final String       DATE_FORMAT  = "yy.MM.dd HH:mm:ss,SSS";
    private static final String[]     COLUMN_NAMES = { "Seq ID", "Thread", "Time", "Class", "Method", "Depth", "Message" };
    private final DateFormat          format       = new SimpleDateFormat(DATE_FORMAT);
    private final RandomAccessLogFile logFile;

    /**
     * Constructor for LogFileTableModel
     *
     * @param logFile .
     */
    LogFileTableModel(RandomAccessLogFile logFile) {

        this.logFile = logFile;

    }

    /**
     * method
     *
     * @param columnIndex .
     *
     * @return .
     */
    public Class getColumnClass(int columnIndex) {

        return String.class;

    }

    /**
     * method
     *
     * @return .
     */
    public int getColumnCount() {

        return 7;

    }

    /**
     * method
     *
     * @param columnIndex .
     *
     * @return .
     */
    public String getColumnName(int columnIndex) {

        return COLUMN_NAMES[columnIndex];

    }

    /**
     * method
     *
     * @return .
     */
    public int getRowCount() {

        return (int) logFile.getMethodMessageCount();

    }

    /**
     * method
     *
     * @param rowIndex .
     * @param columnIndex .
     *
     * @return .
     */
    public Object getValueAt(int rowIndex, int columnIndex) {

        try {

            Message message = logFile.getMessage(rowIndex);

            if (message == null) {

                return "";

            } else if (columnIndex == 0) {

                return "0x" + Long.toHexString(message.getMessageId());

            } else if (columnIndex == 2) {

                return format.format(new Date(message.getTimeMs()));

            } else if (message instanceof MethodMessage) {

                MethodMessage methodMessage = (MethodMessage) message;
                MethodKey     method = methodMessage.getMethodKey();

                switch (columnIndex) {

                case 1:
                    return methodMessage.getThreadName();

                case 3:
                    return method.getClassName();

                case 4:
                    return method.getMethodName() + " " + method.getSignature();

                case 5:
                    return String.valueOf(methodMessage.getThreadDepth());

                case 6:
                    return String.valueOf(methodMessage.getText());

                default:
                    return "";

                }

            } else {

                return rowIndex + "x" + columnIndex;

            }

        } catch (Exception e) {

            LOG.info("error getValueAt", e);

            return "ERROR:" + e.getClass().getName() + " " + e.getMessage();

        }

    }

    /**
     * method
     *
     * @param rowIndex .
     * @param columnIndex .
     *
     * @return .
     */
    public boolean isCellEditable(int rowIndex, int columnIndex) {

        return false;

    }

    /**
     * method
     *
     * @param l .
     */
    public void addTableModelListener(TableModelListener l) {

    }

    /**
     * method
     *
     * @param l .
     */
    public void removeTableModelListener(TableModelListener l) {

    }

    /**
     * method
     *
     * @param aValue .
     * @param rowIndex .
     * @param columnIndex .
     */
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

    }

}
