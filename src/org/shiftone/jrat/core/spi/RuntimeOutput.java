package org.shiftone.jrat.core.spi;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;


/**
 * .
 *
 * @author $author$
 * @version $Revision: 1.2 $
 */
public interface RuntimeOutput
{
    /**
     * method
     *
     * @return .
     */
    File getInputFile();

    /**
     * method
     *
     * @return .
     */
    InputStream openInputStream() throws IOException;

    /**
     * method
     *
     * @return .
     *
     * @throws IOException . 
     */
    Reader openReader() throws IOException;
}
