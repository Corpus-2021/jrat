package org.shiftone.jrat.provider.tree;



/**
 * Class DelegateThreadLocal
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.6 $
 */
public class DelegateThreadLocal extends ThreadLocal
{

    private TreeMethodHandlerFactory factory = null;

    /**
     * Constructor DelegateThreadLocal
     *
     *
     * @param factory
     */
    public DelegateThreadLocal(TreeMethodHandlerFactory factory)
    {

        if (factory == null)
        {
            throw new NullPointerException("factory is null");
        }

        this.factory = factory;
    }

    /**
     * Method initialValue
     */
    protected final Object initialValue()
    {
        return new Delegate(factory.getRootNode());
    }
}
