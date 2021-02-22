package org.shiftone.jrat.util;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import java.util.Timer;
import java.util.TimerTask;


/**
 * class
 * @author $author$
 * @version $Revision: 1.5 $
 */
public class WeakScheduler {

    static final Log             LOG       = LogFactory.getLogger(WeakScheduler.class);
    private static WeakScheduler scheduler = null;
    private Timer                timer     = new Timer();

    /**
     * method
     *
     * @return .
     */
    public synchronized WeakScheduler getInstance() {

        if (scheduler == null) {

            scheduler = new WeakScheduler();

        }

        return scheduler;

    }

    /**
     * method
     *
     * @param runnable .
     * @param period .
     */
    public void schedule(Runnable runnable, long period) {

        timer.scheduleAtFixedRate(new WeakTimerTask(runnable), period, period);

    }

    //--------------------------------------------------------------------
    class WeakTimerTask extends TimerTask {

        private Reference reference = null;
        private String    name = null;

        /**
         * Constructor for WeakTimerTask
         *
         * @param runnable .
         */
        WeakTimerTask(Runnable runnable) {

            reference     = new WeakReference(runnable);
            name          = String.valueOf(runnable);

        }

        /**
         * method
         */
        public void run() {

            Runnable target = (Runnable) reference.get();

            if (target == null) {

                LOG.info("task completed");
                cancel();

            } else {

                try {

                    target.run();

                } catch (Exception e) {

                    LOG.warn("task failed to run : " + target, e);

                }

            }

        }

    }

}
