package org.shiftone.jrat.provider.log.io;

import org.shiftone.jrat.provider.log.io.message.CloseMessage;
import org.shiftone.jrat.provider.log.io.message.Message;
import org.shiftone.jrat.provider.log.io.message.MethodMessage;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import java.util.ArrayList;
import java.util.List;


/**
 * Class RandomAccessLogFile
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.1 $
 */
public class RandomAccessLogFile {

    private static final Log LOG                = LogFactory.getLogger(RandomAccessLogFile.class);
    private File             file               = null;
    private RandomAccessFile raf                = null;
    private LogInput         logInput           = null;
    private List             pageList           = null;
    private int              messageCount       = 0;
    private int              pageSizeInMessages = 0;

    /**
     * Constructor for RandomAccessLogFile
     *
     * @param file .
     *
     * @throws IOException .
     */
    public RandomAccessLogFile(File file) throws IOException {

        this.file         = file;
        this.raf          = new RandomAccessFile(file, "r");
        this.logInput     = new LogInput(raf);
        

    }

    public void initialize() throws IOException {

        byte lastByte;

        raf.seek(raf.length() - 1);

        lastByte = raf.readByte();

        if (lastByte == LogOutput.EOF) {

            try {

                initializeTheEasyWay();

            } catch (Exception e) {

				LOG.warn("the easy way failed.. trying the hard way");
                initializeTheHardWay();

            }

        } else {

            initializeTheHardWay();

        }
		LOG.info("messageCount = " + messageCount);
    }

    private void initializeTheEasyWay() throws IOException {

        Message message;
        int     footerStartOffset;
        byte    b;

        int     pages;

        LOG.info("initializeTheEasyWay");

        // 1 byte = EOF char
        // 4 bytes = footerStartOffset
        raf.seek(raf.length() - (1 + 4));

        footerStartOffset = raf.readInt();

        raf.seek(footerStartOffset);

        // here, I want the logInput to just read thread and method defs
        // the readMessages method reads records from the file until
        // it comes to a records that represents a log message.
        message = logInput.readMessage();

        if (!(message instanceof CloseMessage)) {

            throw new LogFileFormatException("message footer is badly formatted");

        }

        b = raf.readByte();

        if (b != LogOutput.PREFIX_FOOTER_OFFSET_TABLE) {

            throw new LogFileFormatException("message footer offset table is badly formatted " + b);

        }

        pageSizeInMessages     = raf.readInt();
        messageCount           = raf.readInt();
        pages                  = raf.readInt();
        pageList               = new ArrayList(pages);

        for (int i = 0; i < pages; i++) {

            Page page = new Page();

            page.fileOffset = raf.readInt();
            pageList.add(page);

        }

        LOG.info("initialized page table the easy way " + pageList);

    }

    /**
     * method
     *
     * @param inputStream .
     *
     * @throws IOException .
     */
    private void initializeTheHardWay() throws IOException {

        Message message;

        LOG.info("initializeTheHardWay");

        pageList = new ArrayList();

        raf.seek(1);
        logInput.readPreamble();

        pageSizeInMessages = LogOutput.PAGE_SIZE_IN_MESSAGES;

        while (((message = logInput.readMessage()) != null) //
                 &&(!(message instanceof CloseMessage))) {

            if ((messageCount % pageSizeInMessages) == 0) {

                Page page = new Page();

                page.fileOffset = (int) raf.getFilePointer();
                pageList.add(page);

            }

            Thread.yield();

            messageCount++;

        }

        LOG.info("initialized page table the hard way " + pageList);

    }

    /**
     * method
     */
    private static void sleep() {

        try {

            Thread.sleep(100);

        } catch (Exception e) {

        }

    }

    /**
     * method
     *
     * @return .
     */
    public long getMethodMessageCount() {

        return messageCount;

    }

    /**
     * method
     *
     * @param index .
     *
     * @return .
     *
     * @throws IOException .
     */
    public Message getMessage(long index) throws IOException {

        MethodMessage message = null;
        Page          page = null;

        int           pageNumber = (int) (index / pageSizeInMessages);
        long          pageStart  = pageNumber * pageSizeInMessages;
        int           pageIndex  = (int) (index - pageStart);

        page = getPageWithMessages(pageNumber);

        return page.messages[pageIndex];

    }

    /**
     * method
     *
     * @param pageNumber .
     *
     * @return .
     *
     * @throws IOException .
     */
    private synchronized Page getPageWithMessages(int pageNumber)
        throws IOException {

        Message message;
        Page    page = (Page) pageList.get(pageNumber);

        if (page.messages == null) {

            LOG.info("getPageWithMessages " + pageNumber);

            page.messages = new Message[pageSizeInMessages];

            raf.seek(page.fileOffset);

            int i = 0;

            while (((message = logInput.readMessage()) != null) //
                     &&(!(message instanceof CloseMessage)) //
                     &&(i < pageSizeInMessages)) //
             {

                page.messages[i] = message;
                i++;

            }

        }

        return page;

    }

    /**
     * class
     *
     * @author $author$
     * @version $Revision: 1.1 $
     */
    private class Page {

        int       fileOffset;
        Message[] messages;

    }

}
