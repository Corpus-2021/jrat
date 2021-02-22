package org.shiftone.jrat.inject.criteria;



import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;

import org.shiftone.jrat.util.GlobMatcher;
import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;


/**
 * Class MethodCriterion
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.4 $
 */
public class MethodCriterion
{

    private static final Log LOG               = LogFactory.getLogger(MethodCriterion.class);
    private Boolean          wherePublic       = null;
    private Boolean          wherePrivate      = null;
    private Boolean          whereProtected    = null;
    private Boolean          whereSynchronized = null;
    private Boolean          whereStatic       = null;
    private Boolean          whereFinal        = null;
    private GlobMatcher      classMatcher      = GlobMatcher.INCLUDE_ALL;
    private GlobMatcher      methodMatcher     = GlobMatcher.INCLUDE_ALL;
    private GlobMatcher      signatureMatcher  = GlobMatcher.INCLUDE_ALL;

    /**
     * Method setWherePublic
     */
    public void setWherePublic(Boolean wherePublic)
    {
        this.wherePublic = wherePublic;
    }

    /**
     * Method setWherePrivate
     */
    public void setWherePrivate(Boolean wherePrivate)
    {
        this.wherePrivate = wherePrivate;
    }

    /**
     * Method setWhereProtected
     */
    public void setWhereProtected(Boolean whereProtected)
    {
        this.whereProtected = whereProtected;
    }

    /**
     * Method setWhereSynchronized
     */
    public void setWhereSynchronized(Boolean whereSynchronized)
    {
        this.whereSynchronized = whereSynchronized;
    }

    /**
     * Method setWhereStatic
     */
    public void setWhereStatic(Boolean whereStatic)
    {
        this.whereStatic = whereStatic;
    }

    /**
     * Method setWhereFinal
     */
    public void setWhereFinal(Boolean whereFinal)
    {
        this.whereFinal = whereFinal;
    }

    //----------------------------------------------------------

    /**
     * Method setClass
     */
    public void setClass(String classPattern)
    {
        this.classMatcher = new GlobMatcher(classPattern);
    }

    /**
     * Method setMethod
     */
    public void setMethod(String methodPattern)
    {
        this.methodMatcher = new GlobMatcher(methodPattern);
    }

    /**
     * Method setSignature
     */
    public void setSignature(String signaturePattern)
    {
        this.signatureMatcher = new GlobMatcher(signaturePattern);
    }

    //----------------------------------------------------------

    /**
     * Method isMatch
     */
    public boolean isMatch(JavaClass javaClass, Method method)
    {

        return classMatcher.isMatch(javaClass.getClassName())        //
               && methodMatcher.isMatch(method.getName())            //
               && signatureMatcher.isMatch(method.getSignature())    //
               && modifiersMatch(method);
    }

    /**
     * Method modifiersMatch
     */
    private boolean modifiersMatch(Method method)
    {

        return (((wherePublic == null) || (method.isPublic() == wherePublic.booleanValue()))                         //
                && ((wherePrivate == null) || (method.isPrivate() == wherePrivate.booleanValue()))                   //
                && ((whereProtected == null) || (method.isProtected() == whereProtected.booleanValue()))             //
                && ((whereSynchronized == null) || (method.isSynchronized() == whereSynchronized.booleanValue()))    //
                && ((whereStatic == null) || (method.isStatic() == whereStatic.booleanValue()))                      //
                && ((whereFinal == null) || (method.isFinal() == whereFinal.booleanValue()))                         //
                    );
    }
}
