package org.shiftone.jrat.core.spi;



import java.awt.Component;

import org.shiftone.jrat.core.OutputViewerException;


/**
 * Interface OutputViewerFactory is a UI viewer component factory that takes a raw
 * input stream as an input.
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.6 $
 */
public interface OutputViewerFactory
{

    /**
     * Method createViewerComponent
     *
     * @throws OutputViewerException
     */
    Component createViewerComponent(RuntimeOutput inputContext) throws OutputViewerException;
}
