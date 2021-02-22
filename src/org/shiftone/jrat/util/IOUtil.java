package org.shiftone.jrat.util;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;


/**
 * Class IOUtil
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.17 $
 */
public class IOUtil {

    private static final Log LOG                 = LogFactory.getLogger(IOUtil.class);
    public static final int  DEFAULT_BUFFER_SIZE = 1024 * 4;

    /**
     * Method delete
     *
     * @param file .
     *
     * @throws IOException
     */
    public static void delete(File file) throws IOException {

        Assert.assertNotNull("file", file);
        LOG.debug("delete(" + file + ")");

        if (!file.delete()) {

            if (file.exists()) {

                throw new IOException("unable to delete file : " + file.getAbsolutePath());

            } else {

                throw new IOException("unable to delete non-existant file : " + file.getAbsolutePath());

            }

        }

    }

    /**
     * Method mkdir
     *
     * @param dir .
     *
     * @throws IOException
     */
    public static void mkdir(File dir) throws IOException {

        Assert.assertNotNull("dir", dir);
        LOG.info("mkdir(" + dir.getAbsolutePath() + ")");

        if (dir.exists()) {

            if (dir.isDirectory()) {

                return;

            } else {

                throw new IOException("unable to create directory because file with same name exists " + dir);

            }

        }

        if (!dir.mkdirs()) {

            throw new IOException("unable to create directory : " + dir);

        }

    }

    /**
     * Method rename
     *
     * @param source .
     * @param target .
     * @param replace .
     *
     * @throws IOException
     */
    public static void rename(File source, File target, boolean replace)
        throws IOException {

        Assert.assertNotNull("source", source);
        Assert.assertNotNull("target", target);

        if (!source.exists()) {

            throw new IOException("source file does not exist : " + source);

        }

        if ((target.exists()) && (replace == true)) {

            LOG.debug("rename.delete(" + target + ")");

            if (!target.delete()) {

                throw new IOException("unable to delete file : " + target.getAbsolutePath());

            }

        }

        LOG.debug("rename(" + source + " , " + target + ")");

        if (!source.renameTo(target)) {

            throw new IOException("unable to rename " + source.getAbsolutePath() + " to " + target.getAbsolutePath());

        }

    }

    /**
     * Method copy
     *
     * @param sourceStream .
     * @param targetStream .
     * @param bufferSize .
     *
     * @throws IOException
     */
    public static void copy(InputStream sourceStream, OutputStream targetStream, int bufferSize)
        throws IOException {

        byte[] buffer = new byte[bufferSize];
        int    b = 0;

        Assert.assertNotNull("sourceStream", sourceStream);
        Assert.assertNotNull("targetStream", targetStream);

        for (b = 0; b >= 0; b = sourceStream.read(buffer)) {

            if (b != 0) {

                targetStream.write(buffer, 0, b);

            }

        }

    }

    /**
     * Method copy
     *
     * @param sourceStream .
     * @param targetStream .
     *
     * @throws IOException
     */
    public static void copy(InputStream sourceStream, OutputStream targetStream)
        throws IOException {

        copy(sourceStream, targetStream, DEFAULT_BUFFER_SIZE);

    }

    /**
     * Method copy
     *
     * @param source .
     * @param target .
     *
     * @return .
     *
     * @throws IOException
     */
    public static boolean copy(File source, File target)
        throws IOException {

        Assert.assertNotNull("source", source);
        Assert.assertNotNull("target", target);

        LOG.debug("copy(" + source.getAbsolutePath() + " , " + target.getAbsolutePath() + ")");

        if (source.equals(target)) {

            LOG.debug("copy doing nothing, source and target are same");

            return false;

        } else {

            InputStream  inputStream  = null;
            OutputStream outputStream = null;
            int          bufferSize   = (int) Math.min(DEFAULT_BUFFER_SIZE, source.length());

            inputStream      = new BufferedInputStream(new FileInputStream(source), bufferSize);
            outputStream     = new BufferedOutputStream(new FileOutputStream(target), bufferSize);

            copy(inputStream, outputStream);
            close(outputStream);
            close(inputStream);

            return true;

        }

    }

    /**
     * method
     *
     * @param reader .
     */
    public static void close(Reader reader) {

        try {

            if (reader != null) {

                reader.close();

            }

        } catch (Exception e) {

            LOG.warn("close Reader failes", e);

        }

    }

    /**
     * Method close
     *
     * @param inputStream .
     */
    public static void close(InputStream inputStream) {

        try {

            if (inputStream != null) {

                inputStream.close();

            }

        } catch (Exception e) {

            LOG.warn("close InputStream failes", e);

        }

    }

    /**
     * Method close
     *
     * @param outputStream .
     */
    public static void close(OutputStream outputStream) {

        try {

            if (outputStream != null) {

                outputStream.close();

            }

        } catch (Exception e) {

            LOG.warn("close OutputStream failes", e);

        }

    }

    /**
     * Method getExtention
     *
     * @param file .
     *
     * @return .
     */
    public static String getExtention(File file) {

        Assert.assertNotNull("file", file);

        String name    = file.getName();
        int    lastDot = name.lastIndexOf('.');

        return (lastDot == -1) ? "" : name.substring(lastDot + 1);

    }

    /**
     * Method getNearestExistingParent
     *
     * @param file .
     *
     * @return .
     */
    public static File getNearestExistingParent(File file) {

        Assert.assertNotNull("file", file);

        File p = file.getParentFile();

        while ((p != null) && (p.exists() == false)) {

            p = p.getParentFile();

        }

        return p;

    }

}
