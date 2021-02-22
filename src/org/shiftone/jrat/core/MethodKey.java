package org.shiftone.jrat.core;

import org.shiftone.jrat.util.Assert;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;


/**
 * Immutable object that can be used to uniquely identify a method - suitable for use as a Map key.
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.9 $
 */
public class MethodKey {

    private static final Log LOG           = LogFactory.getLogger(MethodKey.class);
    private String           className     = null;
    private String           methodName    = null;
    private String           signature     = null;
    private int              hashCode      = 0;
    private String           toStringValue = null;

    /**
     * Constructor MethodKey
     *
     * @param className
     * @param methodName
     * @param signature
     */
    public MethodKey(String className, String methodName, String signature) {

        Assert.assertNotNull("className", className);
        Assert.assertNotNull("methodName", methodName);
        Assert.assertNotNull("signature", signature);

        this.className      = className;
        this.methodName     = methodName;
        this.signature      = signature;
        hashCode            = className.hashCode();
        hashCode            = (29 * hashCode) + methodName.hashCode();
        hashCode            = (29 * hashCode) + signature.hashCode();

    }

    /**
     * Method getMethodName
     *
     * @return .
     */
    public final String getMethodName() {

        return methodName;

    }

    /**
     * Method getSignature
     *
     * @return .
     */
    public final String getSignature() {

        return signature;

    }

    /**
     * Method getClassName
     *
     * @return .
     */
    public final String getClassName() {

        return className;

    }

    /**
     * Method equals
     *
     * @param o .
     *
     * @return .
     */
    public final boolean equals(Object o) {

        if (this == o) {

            return true;

        }

        if (!(o instanceof MethodKey)) {

            return false;

        }

        final MethodKey methodKey = (MethodKey) o;

        if (!className.equals(methodKey.className)) {

            return false;

        }

        if (!methodName.equals(methodKey.methodName)) {

            return false;

        }

        if (!signature.equals(methodKey.signature)) {

            return false;

        }

        return true;

    }

    /**
     * Method toString
     *
     * @return .
     */
    public final String toString() {

        if (toStringValue == null) {

            toStringValue = className + '.' + methodName + signature;

        }

        return toStringValue;

    }

    /**
     * Method hashCode
     *
     * @return .
     */
    public final int hashCode() {

        return hashCode;

    }

    /**
     * method
     *
     * @param methodKey .
     *
     * @return .
     */
    public static String toTSV(MethodKey methodKey) {

        return methodKey.getClassName() + "\t" + methodKey.getMethodName() + "\t" + methodKey.getSignature();

    }

}
