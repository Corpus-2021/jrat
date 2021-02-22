/*
 * User: jeff
 */
package org.shiftone.jrat.jda;

import com.sun.jdi.Bootstrap;
import com.sun.jdi.ThreadReference;
import com.sun.jdi.VMDisconnectedException;
import com.sun.jdi.VirtualMachine;

import com.sun.jdi.event.Event;
import com.sun.jdi.event.EventIterator;
import com.sun.jdi.event.EventQueue;
import com.sun.jdi.event.EventSet;
import com.sun.jdi.event.LocatableEvent;

import com.sun.jdi.request.EventRequest;
import com.sun.jdi.request.EventRequestManager;
import com.sun.jdi.request.MethodEntryRequest;

import org.shiftone.jrat.jda.config.VMTraceConfig;

import org.shiftone.jrat.jda.config.connect.ConnectorConfig;

import org.shiftone.jrat.jda.config.filter.FilterConfig;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Class VMTracer
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 */
public class VMTracer {

    private static final Log    LOG           = LogFactory.getLogger(VMTracer.class);
    private VMTraceConfig       vmc           = null;
    private VirtualMachine      vm            = null;
    private EventRequestManager erm           = null;
    private EventQueue          eventQueue    = null;
    private Map                 threadTracers = new HashMap();

    /**
     * Constructor VMTracer
     *
     * @param vmc
     */
    public VMTracer(VMTraceConfig vmc) {

        this.vmc = vmc;

    }

    /**
     * Method initThreads
     */
    private void initThreads() {

        Map             newThreadTracers = new HashMap();
        List            list            = vm.allThreads();
        ThreadReference threadReference = null;

        for (int i = 0; i < list.size(); i++) {

            threadReference = (ThreadReference) list.get(i);

            newThreadTracers.put(threadReference, new ThreadTracer(threadReference));

        }

        threadTracers = newThreadTracers;

    }

    /**
     * Method subscribeToMethodEvents
     *
     * @param erm .
     */
    private void subscribeToMethodEvents(EventRequestManager erm) {

        FilterConfig[]     fc           = vmc.getFilterConfigs();
        MethodEntryRequest enterRequest = null;

        //MethodExitRequest exitRequest = null;
        for (int i = 0; i < fc.length; i++) {

            String inc = fc[i].getInclude();
            String exc = fc[i].getExclude();

            if (inc != null) {

                enterRequest = erm.createMethodEntryRequest();

                //exitRequest = erm.createMethodExitRequest();
                enterRequest.addClassFilter(inc);

                //exitRequest.addClassFilter(inc);
                if (exc != null) {

                    enterRequest.addClassExclusionFilter(exc);

                    //exitRequest.addClassExclusionFilter(exc);
                }

                enterRequest.setSuspendPolicy(EventRequest.SUSPEND_NONE);

                //exitRequest.setSuspendPolicy(EventRequest.SUSPEND_NONE);
                enterRequest.enable();

                //exitRequest.enable();
            }

        }

    }

    /**
     * Method init
     *
     * @throws TraceException
     */
    private void init() throws TraceException {

        ConnectorConfig cc = null;

        cc     = vmc.getConnectorConfig();
        vm     = cc.connect(Bootstrap.virtualMachineManager());

        initThreads();
        subscribeToMethodEvents(vm.eventRequestManager());

        eventQueue = vm.eventQueue();

        vm.resume();

    }

    /**
     * Method getThreadTracer
     *
     * @param threadReference .
     *
     * @return .
     */
    private ThreadTracer getThreadTracer(ThreadReference threadReference) {

        ThreadTracer threadTracer = (ThreadTracer) threadTracers.get(threadReference);

        if (threadTracer == null) {

            threadTracer = new ThreadTracer(threadReference);

            threadTracers.put(threadReference, threadTracer);

        }

        return threadTracer;

    }

    /**
     * Method jda
     *
     * @throws TraceException
     */
    public void trace() throws TraceException {

        init();

        Event         event    = null;
        EventSet      eventSet = null;
        EventIterator iterator = null;

        try {

            while (true) {

                eventSet     = eventQueue.remove();
                iterator     = eventSet.eventIterator();

                while (iterator.hasNext()) {

                    event = iterator.nextEvent();

                    if (event instanceof LocatableEvent) {

                        LocatableEvent locEvent     = (LocatableEvent) event;
                        ThreadTracer   threadTracer = getThreadTracer(locEvent.thread());

                        if (threadTracer != null) {

                            threadTracer.processEvent(locEvent);

                        } else {

                            LOG.debug(event);

                        }

                    }

                }

            }

        } catch (VMDisconnectedException e) {

            LOG.info("target VM exited");

        } catch (Exception e) {

            LOG.error("error reading event queue", e);

        }

    }

}
