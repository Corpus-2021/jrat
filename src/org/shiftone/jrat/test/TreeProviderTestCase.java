package org.shiftone.jrat.test;

import junit.framework.TestCase;

import org.shiftone.jrat.core.HandlerFactory;
import org.shiftone.jrat.core.RootFactory;

import org.shiftone.jrat.core.spi.MethodHandler;

import org.shiftone.jrat.provider.tree.TreeMethodHandlerFactory;

import org.shiftone.jrat.proxy.MethInvocationHandler;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * Class TreeProviderTestCase
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.12 $
 */
public class TreeProviderTestCase extends TestCase {

    private static final Log LOG = LogFactory.getLogger(TreeProviderTestCase.class);

    /**
     * Method testTreeProvider
     *
     * @throws Exception
     */
    public void testTreeProvider() throws Exception {

        System.setProperty(RootFactory.SYS_PROP_FACTORY, TreeMethodHandlerFactory.class.getName());

        MethodHandler handler = HandlerFactory.getMethodHandler("java.util.ArrayList", "clear", "()V");
        List          list = (List) MethInvocationHandler.getTracedProxy(new ArrayList(), List.class);

        list.add("this");
        list.add("is");
        list.add("a");
        list.add("test");
        list.clear();
        list.add("again");
        handler.onMethodStart(null, null);
        list.clear();
        handler.onMethodFinish(null, null, null, 10, true);

    }

}
