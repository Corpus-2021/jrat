package org.shiftone.jrat.core;

import org.shiftone.jrat.core.spi.ShutdownListener;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;


/**
 * Class ShutdownHook
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.18 $
 */
public class ShutdownHook extends Thread {

    private static final Log     LOG           = LogFactory.getLogger(ShutdownHook.class);
    private static final Runtime RUNTIME       = Runtime.getRuntime();
    private Stack                shutdownStack = new Stack();
    private Set                  listenerSet   = new HashSet();
    private boolean              installed     = false;
    private boolean              shutDown      = false;

    /**
     * Method run
     */
    public void run() {

        Thread.currentThread().setName("JRat-Shutdown");

        try {

            shutdownNow();

        } catch (Exception e) {

            LOG.error("shutdown error", e);

        }

    }

    /**
     * Method shutdown
     */
    public synchronized void shutdownNow() {

        long             start;
        ShutdownListener listener = null;

        this.shutDown        = true;
        this.listenerSet     = null; // not needed

        LOG.info("JRat shutting down...");

        while (shutdownStack.isEmpty() == false) {

            listener = (ShutdownListener) shutdownStack.pop();

            LOG.info(size() + " " + listener + " shutting down...");

            try {

                start = System.currentTimeMillis();

                listener.shutdown();
                LOG.info(size() + " " + listener + " shutdown in " + (System.currentTimeMillis() - start) + "ms");

            } catch (Exception e) {

                System.err.println(size() + " " + listener + " failed to shutdown");
                e.printStackTrace(System.err);
                LOG.error(size() + " " + listener + " failed to shutdown", e);

            }

        }

        LOG.info("JRat shutdown complete.");

    }

    /**
     * Method addShutdownListener
     *
     * @param listener .
     */
    public void addShutdownListener(ShutdownListener listener) {

        if (listenerSet.contains(listener) == false) {

            install();
            shutdownStack.push(listener);
            listenerSet.add(listener);
            LOG.info(size() + " added for shutdown : " + shutdownStack.size() + " " + listener);

        } else {

            LOG.info("shutdown listener already registered : " + listener);

        }

    }

    /**
     * method
     *
     * @return .
     */
    public int size() {

        return shutdownStack.size();

    }

    /**
     * Method install
     */
    public void install() {

        if (installed == false) {

            RUNTIME.addShutdownHook(this);

            installed = true;

            LOG.debug("shutdown hook installed");

        }

    }

    /**
     * Method uninstall
     */
    public void uninstall() {

        if (installed == true) {

            RUNTIME.removeShutdownHook(this);

            installed = false;

            LOG.debug("shutdown hook removed");

        }

    }

    /**
     * Method isInstalled
     *
     * @return .
     */
    public boolean isInstalled() {

        return installed;

    }

}
