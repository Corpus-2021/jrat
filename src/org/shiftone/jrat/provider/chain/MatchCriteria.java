package org.shiftone.jrat.provider.chain;



import org.shiftone.jrat.util.GlobMatcher;
import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;


/**
 * Class MatchCriteria is a tripple GlobMatcher to perform matching of method signatures.
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.2 $
 */
public class MatchCriteria
{

    private static final Log LOG               = LogFactory.getLogger(MatchCriteria.class);
    private GlobMatcher      classNameMatcher  = null;
    private GlobMatcher      methodNameMatcher = null;
    private GlobMatcher      signatureMatcher  = null;

    /**
     * Constructor MatchCriteria
     *
     *
     * @param classNamePattern
     * @param methodNamePattern
     * @param signaturePattern
     */
    public MatchCriteria(String classNamePattern, String methodNamePattern, String signaturePattern)
    {

        this.classNameMatcher  = newGlobMatcher(classNamePattern);
        this.methodNameMatcher = newGlobMatcher(methodNamePattern);
        this.signatureMatcher  = newGlobMatcher(signaturePattern);
    }

    /**
     * defaults null or zero length patterns to "*"
     */
    private static GlobMatcher newGlobMatcher(String pattern)
    {

        if ((pattern == null) || (pattern.length() == 0))
        {
            pattern = "*";
        }

        return new GlobMatcher(pattern);
    }

    /**
     * Method matches
     */
    public boolean isMatch(String className, String methodName, String signature)
    {

        return classNameMatcher.isMatch(className)         //
               && methodNameMatcher.isMatch(methodName)    //
               && signatureMatcher.isMatch(signature);
    }
}
