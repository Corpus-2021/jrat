package org.shiftone.jrat.inject.criteria;



import org.apache.bcel.classfile.JavaClass;

import org.shiftone.jrat.util.GlobMatcher;
import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;


/**
 * Class ClassCriterion
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.3 $
 */
public class ClassCriterion
{

    private static final Log LOG            = LogFactory.getLogger(ClassCriterion.class);
    private Boolean          wherePublic    = null;
    private Boolean          wherePrivate   = null;
    private Boolean          whereProtected = null;
    private Boolean          whereAbstract  = null;
    private Boolean          whereFinal     = null;
    private GlobMatcher      classMatcher   = GlobMatcher.INCLUDE_ALL;

    /**
     * Method setClass
     */
    public void setClass(String classPattern)
    {
        classMatcher = new GlobMatcher(classPattern);
    }

    /**
     * Method isMatch
     */
    public boolean isMatch(JavaClass javaClass)
    {
        return classMatcher.isMatch(javaClass.getClassName()) && modifiersMatch(javaClass);
    }

    /**
     * Method modifiersMatch
     */
    private boolean modifiersMatch(JavaClass javaClass)
    {

        return (((wherePublic == null) || (javaClass.isPublic() == wherePublic.booleanValue()))                //
                && ((wherePrivate == null) || (javaClass.isPrivate() == wherePrivate.booleanValue()))          //
                && ((whereProtected == null) || (javaClass.isProtected() == whereProtected.booleanValue()))    //
                && ((whereAbstract == null) || (javaClass.isStatic() == whereAbstract.booleanValue()))         //
                && ((whereFinal == null) || (javaClass.isFinal() == whereFinal.booleanValue()))                //
                    );
    }
}
