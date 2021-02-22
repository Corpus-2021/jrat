package org.shiftone.jrat.core;

import org.shiftone.jrat.core.spi.RuntimeContext;
import org.shiftone.jrat.core.spi.ShutdownListener;

import org.shiftone.jrat.ui.viewer.SimpleTextOutputViewerFactory;

import org.shiftone.jrat.util.Assert;
import org.shiftone.jrat.util.IOUtil;
import org.shiftone.jrat.util.NullOutputStream;
import org.shiftone.jrat.util.Sequence;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;
import org.shiftone.jrat.util.log.SimpleLog;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.GZIPOutputStream;


/**
 * Class RuntimeContextImpl
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.22 $
 */
class RuntimeContextImpl implements RuntimeContext {

    public static final int     BUFFER_SIZE       = 1024 * 4;
    public static final String  OUTPUT_DIR_NAME   = "JRatOutput";
    public static final String  LOG_FILE_NAME     = "JRat-LOG.jrat";
    public static final String  DIR_DATE_FORMAT   = "yyyy-MM-dd_a-hh-mm-ss";
    public static final String  FILE_NUM_FORMAT   = "000";
    public static final long    MAX_OUTPUT_FILES  = 300;
    public static final long    MAX_OPEN_FILES    = 300;
    public static final String  SYS_PROP_APP      = "jrat.app";
    private static final String JRAT_APP          = System.getProperty(SYS_PROP_APP);
    protected static final Log  LOG               = LogFactory.getLogger(RuntimeContextImpl.class);
    protected final Set         openOutputStreams;
    private final NumberFormat  fileSeqFormat;
    private final Sequence      sequence;
    private final Sequence      fileSequence;
    private final long          startTimeMs;
    private final Date          startDate;
    private final File          outputDir;
    private final ShutdownHook  shutdownHook;

    /**
     * Constructor RuntimeContextImpl
     *
     * @param shutdownHook .
     */
    RuntimeContextImpl(ShutdownHook shutdownHook) {

        LOG.debug("new RuntimeContextImpl");

        this.startTimeMs           = System.currentTimeMillis();
        this.fileSeqFormat         = new DecimalFormat(FILE_NUM_FORMAT);
        this.sequence              = new Sequence();
        this.fileSequence          = new Sequence();
        this.startDate             = new Date(startTimeMs);
        this.shutdownHook          = shutdownHook;
        this.openOutputStreams     = new HashSet();
        this.outputDir             = createOutputDir();

        redirectLogStream();
        registerForShutdown();

    }

    /**
     * Method createLogStream
     */
    private void redirectLogStream() {

        OutputStream outputStream;
        PrintStream  printStream;

        try {

            outputStream     = newOutputStream(LOG_FILE_NAME);
            printStream      = new PrintStream(outputStream);

            printStream.print("viewer=\"");
            printStream.print(SimpleTextOutputViewerFactory.class.getName());
            printStream.println("\"");
            SimpleLog.setPrintStream(printStream);
            LOG.info("logfile created");

        } catch (Exception e) {

            LOG.warn("unable to redirect LOG to file", e);

        }

    }

    /**
     * Method createOutputDir
     *
     * @return .
     */
    private File createOutputDir() {

        Format       format     = new SimpleDateFormat(DIR_DATE_FORMAT);
        File         cwd        = new File("");
        StringBuffer sb         = new StringBuffer();
        File         desiredDir;
        File         outputDir;

        sb.append(cwd.getAbsolutePath());
        sb.append(File.separator);
        sb.append(OUTPUT_DIR_NAME);
        sb.append(File.separator);

        if (JRAT_APP != null) {

            sb.append(JRAT_APP);
            sb.append(File.separator);

        }

        sb.append(format.format(startDate));

        desiredDir     = new File(sb.toString());
        outputDir      = desiredDir;

        while (outputDir.exists()) {

            outputDir = new File(desiredDir.getAbsolutePath() + "-" + randomInt());

        }

        try {

            IOUtil.mkdir(outputDir);

        } catch (Exception e) {

            LOG.error("error creating desired output directory", e);

            outputDir = cwd;

        }

        LOG.info("output DIR = " + outputDir);

        return outputDir;

    }

    /**
     * Method registerForShutdown
     */
    public void registerForShutdown() {

        ShutdownListener listener = new ShutdownListener() {

                public void shutdown() {

                    SimpleLog.close();

                }

            };

        addShutdownListener(listener);

    }

    /**
     * method
     *
     * @param fileName .
     *
     * @return .
     *
     * @throws IOException .
     */
    public OutputStream newOutputStream(String fileName)
        throws IOException {

        return newOutputStream(fileName, false);

    }

    /**
     * method
     *
     * @param name .
     * @param fileNumber .
     * @param compress .
     *
     * @return .
     */
    private String getFileName(String name, long fileNumber, boolean compress) {

        String path = outputDir.getAbsolutePath() + File.separator + fileSeqFormat.format(fileNumber) + "_" + name;

        if (compress) {

            path += ".gz";

        }

        return path;

    }

    /**
     * method
     *
     * @param file .
     * @param compress .
     *
     * @return .
     */
    private OutputStream createFileOutputStream(File file, boolean compress) {

        OutputStream stream = null;

        LOG.info("createFileOutputStream " + file);

        if (openOutputStreams.size() > MAX_OPEN_FILES) {

            LOG.info("max number of open output files exceeded : " + MAX_OPEN_FILES);

        } else {

            try {

                stream = new SafeFileOutputStream(file);
                openOutputStreams.add(stream);

                if (compress) {

                    stream = new GZIPOutputStream(stream, BUFFER_SIZE);

                } else {

                    stream = new BufferedOutputStream(stream, BUFFER_SIZE);

                }

            } catch (Exception e) {

                LOG.error("unable to create output stream", e);

            }

        }

        if (stream == null) {

            LOG.info("the requested file was not created : " + file);
            LOG.info("returning a null output stream (/dev/null) for file");
            LOG.info("output will be sent to the abyss");
            stream = NullOutputStream.INSTANCE;

        }

        return stream;

    }

    /**
     * method
     *
     * @param fileName .
     * @param compress .
     *
     * @return .
     *
     * @throws IOException .
     */
    public OutputStream newOutputStream(String fileName, boolean compress)
        throws IOException {

        String       filePath;
        File         file;
        OutputStream outputStream;
        long         fileNumber;

        Assert.assertNotNull("fileName", fileName);

        fileNumber     = fileSequence.getNextValue();

        filePath         = getFileName(fileName, fileNumber, compress);
        file             = new File(filePath);
        outputStream     = createFileOutputStream(file, compress);

        return outputStream;

    }

    /**
     * Method seqNumber
     *
     * @return .
     */
    public synchronized long uniqNumber() {

        return sequence.getNextValue();

    }

    /**
     * Method getStartupDate
     *
     * @return .
     */
    public Date getStartupDate() {

        return startDate;

    }

    /**
     * Method getStartTimeMs
     *
     * @return .
     */
    public long getStartTimeMs() {

        return startTimeMs;

    }

    /**
     * Method getUpTimeMs
     *
     * @return .
     */
    public long getUpTimeMs() {

        return System.currentTimeMillis() - getStartTimeMs();

    }

    /**
     * Method randomInt
     *
     * @return .
     */
    private static int randomInt() {

        return (int) (Math.random() * Integer.MAX_VALUE);

    }

    /**
     * Method addShutdownListener
     *
     * @param listener .
     */
    public void addShutdownListener(ShutdownListener listener) {

        Assert.assertNotNull("listener", listener);
        shutdownHook.addShutdownListener(listener);

    }

    /**
     * class
     *
     * @author $author$
     * @version $Revision: 1.22 $
     */
    class SafeFileOutputStream extends FileOutputStream {

        private boolean closed = false;
        private File    file = null;

        /**
         * Constructor for SafeFileOutputStream
         *
         * @param file .
         *
         * @throws FileNotFoundException .
         */
        SafeFileOutputStream(File file) throws FileNotFoundException {
            super(file);
            this.file = file;

        }

        /**
         * method
         *
         * @throws IOException .
         */
        public void close() throws IOException {

            openOutputStreams.remove(this);

            try {

                super.close();
                LOG.info("file closed successfully : " + file);

            } catch (IOException e) {

                LOG.error("error closing file : " + file, e);
                throw e;

            }

        }

    }

}
