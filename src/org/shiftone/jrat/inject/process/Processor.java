package org.shiftone.jrat.inject.process;



import org.shiftone.jrat.inject.InjectionException;
import org.shiftone.jrat.inject.Injector;

import java.io.File;
import java.io.IOException;


/**
 * Interface Processor
 *
 *
 * @author Jeff Drost
 */
public interface Processor
{

    /**
     * Method process
     *
     * @throws IOException
     * @throws InjectionException
     */
    public boolean process(Injector injector, File source, File target) throws InjectionException, IOException;
}
