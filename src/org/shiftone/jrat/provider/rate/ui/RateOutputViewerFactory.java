package org.shiftone.jrat.provider.rate.ui;



import java.awt.Component;

import org.shiftone.jrat.core.OutputViewerException;
import org.shiftone.jrat.core.spi.OutputViewerFactory;
import org.shiftone.jrat.core.spi.RuntimeOutput;
import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;


/**
 * Class RateOutputViewerFactory
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.7 $
 */
public class RateOutputViewerFactory implements OutputViewerFactory
{

    private static final Log LOG = LogFactory.getLogger(RateOutputViewerFactory.class);

    /**
     * Method createViewerComponent
     *
     * @throws OutputViewerException
     */
    public Component createViewerComponent(RuntimeOutput runtimeOutput) throws OutputViewerException
    {

        RateModel model = new RateModel();

        try
        {
            model.load(runtimeOutput.openInputStream());
        }
        catch (Exception e)
        {
            throw new OutputViewerException("unable to load input", e);
        }

        return new RateViewerPanel(model);
    }
}
