package org.shiftone.jrat.inject.criteria;



import org.apache.bcel.classfile.JavaClass;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * Class ClassCriteria
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.4 $
 */
public class ClassCriteria
{

    private static final Log LOG               = LogFactory.getLogger(ClassCriteria.class);
    private List             positiveMatchList = new ArrayList();
    private List             negativeMatchList = new ArrayList();

    /**
     * Method isMatch returns true if one of the criterion is meet
     */
    public boolean isMatch(JavaClass javaClass)
    {

        ClassCriterion criteria = null;

        // return false if any negative criterion match
        for (int i = 0; i < negativeMatchList.size(); i++)
        {
            criteria = (ClassCriterion) negativeMatchList.get(i);

            if (criteria.isMatch(javaClass))
            {
                return false;
            }
        }

        // return true if any positive criterion match
        for (int i = 0; i < positiveMatchList.size(); i++)
        {
            criteria = (ClassCriterion) positiveMatchList.get(i);

            if (criteria.isMatch(javaClass))
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
    public ClassCriterion newPositiveCriterion()
    {

        ClassCriterion criterion = new ClassCriterion();

        positiveMatchList.add(criterion);

        return criterion;
    }

    /**
     * Method newNegativeCriterion
     */
    public ClassCriterion newNegativeCriterion()
    {

        ClassCriterion criterion = new ClassCriterion();

        negativeMatchList.add(criterion);

        return criterion;
    }
}
