package org.shiftone.jrat.inject.criteria;



import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * Class MethodCriteria
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.4 $
 */
public class MethodCriteria
{

    private static final Log LOG               = LogFactory.getLogger(MethodCriteria.class);
    private List             positiveMatchList = new ArrayList();
    private List             negativeMatchList = new ArrayList();

    /**
     * Method isMatch returns true if one of the criterion is meet
     */
    public boolean isMatch(JavaClass javaClass, Method method)
    {

        MethodCriterion criteria = null;

        // return false if any negative criterion match
        for (int i = 0; i < negativeMatchList.size(); i++)
        {
            criteria = (MethodCriterion) negativeMatchList.get(i);

            if (criteria.isMatch(javaClass, method))
            {
                return false;
            }
        }

        // return true if any positive criterion match
        for (int i = 0; i < positiveMatchList.size(); i++)
        {
            criteria = (MethodCriterion) positiveMatchList.get(i);

            if (criteria.isMatch(javaClass, method))
            {
                return true;
            }
        }

        // return true only if there are no positive criteria
        return (positiveMatchList.size() == 0);
    }

    /**
     * Method newPositiveCriterion
     */
    public MethodCriterion newPositiveCriterion()
    {

        MethodCriterion criterion = new MethodCriterion();

        positiveMatchList.add(criterion);

        return criterion;
    }

    /**
     * Method newNegativeCriterion
     */
    public MethodCriterion newNegativeCriterion()
    {

        MethodCriterion criterion = new MethodCriterion();

        negativeMatchList.add(criterion);

        return criterion;
    }
}
