package org.shiftone.jrat.provider.tree;

import org.shiftone.jrat.core.MethodKey;

import org.shiftone.jrat.core.spi.AbstractMethodHandlerFactory;
import org.shiftone.jrat.core.spi.MethodHandler;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.io.OutputStream;
import java.io.PrintStream;


/**
 * Class TreeMethodHandlerFactory
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.20 $
 */
public class TreeMethodHandlerFactory extends AbstractMethodHandlerFactory {

    private static final Log          LOG                 = LogFactory.getLogger(TreeMethodHandlerFactory.class);
    private final StackNode           rootNode            = new StackNode();
    private final DelegateThreadLocal delegateThreadLocal = new DelegateThreadLocal(this);

    /**
     * Method createMethodHandler
     */
    public final MethodHandler createMethodHandler(MethodKey methodKey) {

        return new TreeMethodHandler(this, methodKey);

    }

    /**
     * Returns the current thread's delegate instance.  This delegate will operate on
     * this factory's call tree data structure when events are processed.
     */
    public final Delegate getDelegate() {

        return (Delegate) delegateThreadLocal.get();

    }

    /**
     * Method getRootNode
     */
    public final StackNode getRootNode() {

        return rootNode;

    }

    /**
     * Method shutdown
     */
    public void shutdown() {

        OutputStream outputStream = null;
        PrintStream  printStream = null;

        try {

            LOG.info("shutting down...");
            outputStream     = getContext().newOutputStream(getOutputFile() + ".xrat", isCompressOutput());
            printStream      = new PrintStream(outputStream);

            rootNode.printXML(printStream);
            printStream.flush();
            outputStream.flush();
            printStream.close();
            outputStream.close();

        } catch (Exception e) {

            LOG.error("Error writting to " + getOutputFile(), e);

        } finally {

            LOG.info("shutdown complete");

        }

    }

    public String toString() {

        return "Tree Handler Factory";

    }

}
