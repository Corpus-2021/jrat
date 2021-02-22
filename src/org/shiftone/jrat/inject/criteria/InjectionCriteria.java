package org.shiftone.jrat.inject.criteria;



import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;


/**
 * Class InjectionCriteria
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.4 $
 */
public class InjectionCriteria
{

    private static final Log LOG            = LogFactory.getLogger(InjectionCriteria.class);
    private ClassCriteria    classCriteria  = new ClassCriteria();
    private MethodCriteria   methodCriteria = new MethodCriteria();

    /**
     * Method addIncludeClass
     */
    public ClassCriterion createIncludeClass()
    {
        return classCriteria.newPositiveCriterion();
    }

    /**
     * Method addExcludeClass
     */
    public ClassCriterion createExcludeClass()
    {
        return classCriteria.newNegativeCriterion();
    }

    //----------------------------------------------------------

    /**
     * Method addIncludeMethod
     */
    public MethodCriterion createIncludeMethod()
    {
        return methodCriteria.newPositiveCriterion();
    }

    /**
     * Method addExcludeClass
     */
    public MethodCriterion createExcludeMethod()
    {
        return methodCriteria.newNegativeCriterion();
    }

    //----------------------------------------------------------

    /**
     *  <b>to be used by ClassInjector and UnitTests ONLY</b>
     */
    public boolean isMatch(JavaClass javaClass)
    {
        return classCriteria.isMatch(javaClass);
    }

    /**
     *  <b>to be used by ClassInjector and UnitTests ONLY</b>
     */
    public boolean isMatch(JavaClass javaClass, Method method)
    {
        return methodCriteria.isMatch(javaClass, method);
    }
}
