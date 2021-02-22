/*
 * User: jeff
 */
package org.shiftone.jrat.jda;

import com.sun.jdi.Method;
import com.sun.jdi.ThreadReference;

import com.sun.jdi.event.LocatableEvent;
import com.sun.jdi.event.MethodEntryEvent;
import com.sun.jdi.event.MethodExitEvent;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.util.List;


/**
 * Class ThreadTracer
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 */
public class ThreadTracer {

    private static final Log LOG    = LogFactory.getLogger(ThreadTracer.class);
    private ThreadReference  thread = null;
    private int              depth  = 0;

    /**
     * Constructor ThreadTracer
     *
     * @param threadReference
     */
    public ThreadTracer(ThreadReference threadReference) {

        this.thread = threadReference;

        try {

            thread.suspend();

            depth = thread.frameCount();

            //
            List frames = thread.frames();

            log(frames.size() + " =? " + thread.frameCount());

            for (int i = 0; i < frames.size(); i++) {

                log(frames.get(i));

            }

            //
            thread.resume();

        } catch (Exception e) {

            LOG.warn("", e);

        }

    }

    /**
     * Method indent
     *
     * @return .
     */
    private String indent() {

        String x = depth + ":";

        //for (int i = 0 ; i < depth ; i ++) {
        //        }
        return x;

    }

    /**
     * Method log
     *
     * @param message .
     */
    private void log(Object message) {

        LOG.info(indent() + " " + thread.name() + " - " + message);

    }

    /**
     * Method processMethodEntryEvent
     *
     * @param methodEntryEvent .
     *
     * @throws Exception
     */
    public void processMethodEntryEvent(MethodEntryEvent methodEntryEvent)
        throws Exception {

        //log(methodEntryEvent);
        depth++;

        if (depth < 50) {

            Method method = methodEntryEvent.method();

            log(">" + method.declaringType().name() + ":" + method.name());

        }

    }

    /**
     * Method processMethodExitEvent
     *
     * @param methodExitEvent .
     *
     * @throws Exception
     */
    public void processMethodExitEvent(MethodExitEvent methodExitEvent)
        throws Exception {

        if (depth < 50) {

            Method method = methodExitEvent.method();

            log("<" + method.declaringType().name() + ":" + method.name());

        }

        depth--;

    }

    /**
     * Method processEvent
     *
     * @param locatableEvent .
     *
     * @throws Exception
     */
    public void processEvent(LocatableEvent locatableEvent)
        throws Exception {

        if (locatableEvent instanceof MethodEntryEvent) {

            processMethodEntryEvent((MethodEntryEvent) locatableEvent);

        } else if (locatableEvent instanceof MethodExitEvent) {

            processMethodExitEvent((MethodExitEvent) locatableEvent);

        }

    }

}
