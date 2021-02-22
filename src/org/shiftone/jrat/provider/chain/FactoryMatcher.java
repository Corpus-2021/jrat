package org.shiftone.jrat.provider.chain;

import org.shiftone.jrat.core.MethodKey;

import org.shiftone.jrat.core.spi.MethodHandlerFactory;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * Class FactoryMatcher holds a list of factories and positive and negative match criteria. This holds the data defined within
 * the &lt;handler&gt; tag of the configuration.
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.8 $
 */
public class FactoryMatcher
{
    private static final Log LOG               = LogFactory.getLogger(FactoryMatcher.class);
    private List             positiveMatchList = new ArrayList();
    private List             negativeMatchList = new ArrayList();
    private List             factoryList       = new ArrayList();

    /**
     * Constructor FactoryMatcher
     */
    public FactoryMatcher()
    {
    }

    /**
     * Method addFactory
     *
     * @param methodHandlerFactory .
     */
    public void addFactory(MethodHandlerFactory methodHandlerFactory)
    {
        factoryList.add(methodHandlerFactory);
    }

    /**
     * Method addPositiveMatchCriteria
     *
     * @param matchCriteria .
     */
    public void addPositiveMatchCriteria(MatchCriteria matchCriteria)
    {
        positiveMatchList.add(matchCriteria);
    }

    /**
     * Method addNegativeMatchCriteria
     *
     * @param matchCriteria .
     */
    public void addNegativeMatchCriteria(MatchCriteria matchCriteria)
    {
        negativeMatchList.add(matchCriteria);
    }

    /**
     * Method matches
     *
     * @param methodKey .
     *
     * @return .
     */
    public boolean matches(MethodKey methodKey)
    {
        return matches(methodKey.getClassName(), methodKey.getMethodName(), methodKey.getSignature());
    }

    /**
     * Method matches
     *
     * @param className .
     * @param methodName .
     * @param signature .
     *
     * @return .
     */
    public boolean matches(String className, String methodName, String signature)
    {
        MatchCriteria criteria = null;

        for (int i = 0; i < negativeMatchList.size(); i++)
        {
            criteria = (MatchCriteria) negativeMatchList.get(i);

            if (criteria.isMatch(className, methodName, signature))
            {
                return false;
            }
        }

        for (int i = 0; i < positiveMatchList.size(); i++)
        {
            criteria = (MatchCriteria) positiveMatchList.get(i);

            if (criteria.isMatch(className, methodName, signature))
            {
                return true;
            }
        }

        return (positiveMatchList.size() == 0);
    }

    /**
     * get list of factories
     *
     * @return a list of factories that this matcher will apply
     */
    public List getFactories()
    {
        return factoryList;
    }
}
