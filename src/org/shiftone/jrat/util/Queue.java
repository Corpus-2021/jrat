package org.shiftone.jrat.util;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.util.LinkedList;


/**
 * Class Queue
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.7 $
 */
public class Queue {

    private static final Log LOG        = LogFactory.getLogger(Queue.class);
    private LinkedList       linkedList = new LinkedList();

    /**
     * Method enqueue
     *
     * @param object .
     */
    public void enqueue(Object object) {

        Assert.assertNotNull("object", object);
        linkedList.addLast(object);

    }

    /**
     * Method dequeue
     *
     * @return .
     */
    public Object dequeue() {

        return (linkedList.size() > 0) ? linkedList.removeFirst() : null;

    }

    /**
     * Method isEmpty
     *
     * @return .
     */
    public boolean isEmpty() {

        return (linkedList.size() == 0);

    }

    /**
     * Method size
     *
     * @return .
     */
    public int size() {

        return linkedList.size();

    }

}
