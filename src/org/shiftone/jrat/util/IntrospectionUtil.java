package org.shiftone.jrat.util;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.io.File;

import java.lang.reflect.Method;

import java.text.ParseException;

import java.util.Date;


/**
 * Class IntrospectionUtil
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.13 $
 */
public class IntrospectionUtil {

    public static final Log LOG = LogFactory.getLogger(IntrospectionUtil.class);

    /**
     * Method finds a method of the provided name with the provided number of parameters. Note that this method does not take the
     * method types.  It is possible for a class to have more than one method with the same name and parameter count.
     *
     * <p>
     * Method will be found based on case insentive name.
     * </p>
     *
     * @param klass
     * @param methodName
     * @param paramCount
     *
     * @return .
     *
     * @throws NoSuchMethodException
     */
    public static Method getMethod(Class klass, String methodName, int paramCount)
        throws NoSuchMethodException {

        Method[] methods          = null;
        Method   method           = null;
        Method   foundMethod      = null;
        int      foundMethodCount = 0;

        LOG.debug("getMethod(" + klass.getName() + " , " + methodName + " , " + paramCount + ")");

        Assert.assertNotNull("klass", klass);
        Assert.assertNotNull("methodName", methodName);

        methods = klass.getMethods();

        for (int i = 0; i < methods.length; i++) {

            method = methods[i];

            if ((method.getName().equalsIgnoreCase(methodName)) && (method.getParameterTypes().length == paramCount)) {

                foundMethod = method;

                foundMethodCount++;

            }

        }

        if (foundMethodCount == 0) {

            throw new NoSuchMethodException(klass.getName() + " has no method " + methodName + " with " + paramCount + " parameter(s)");

        } else if (foundMethodCount > 1) {

            throw new NoSuchMethodException(klass.getName() + " has " + foundMethodCount + " method " + methodName + " with " + paramCount +
                " parameter(s)");

        }

        return foundMethod;

    }

    /**
     * Method convertSafe
     *
     * @param text .
     * @param targetType .
     *
     * @return .
     */
    public static Object convertSafe(String text, Class targetType) {

        try {

            return convert(text, targetType);

        } catch (Exception e) {

            return text;

        }

    }

    /**
     * Method convert
     *
     * @param text .
     * @param targetType .
     *
     * @return .
     *
     * @throws ParseException
     */
    public static Object convert(String text, Class targetType)
        throws ParseException {

        Object result = null;

        LOG.debug("convert(" + text + " , " + targetType.getName() + ")");

        try {

            if (targetType.equals(String.class)) {

                result = text;

            } else if (Short.class.equals(targetType) || Short.TYPE.equals(targetType)) {

                result = new Short(text);

            } else if (Integer.class.equals(targetType) || Integer.TYPE.equals(targetType)) {

                result = new Integer(text);

            } else if (Long.class.equals(targetType) || Long.TYPE.equals(targetType)) {

                result = new Long(text);

            } else if (Float.class.equals(targetType) || Float.TYPE.equals(targetType)) {

                result = new Float(text);

            } else if (Double.class.equals(targetType) || Double.TYPE.equals(targetType)) {

                result = new Double(text);

            } else if (Boolean.class.equals(targetType) || Boolean.TYPE.equals(targetType)) {

                result = new Boolean(text);

            } else if (Date.class.equals(targetType)) {

                result = new Date(text);

            } else if (File.class.equals(targetType)) {

                File file = new File(text);

                result = file.getAbsoluteFile();

            } else if (Class.class.equals(targetType)) {

                result = Class.forName(text);

            } else {

                throw new ParseException("conversion to " + targetType.getName() + " is not supported", 0);

            }

        } catch (Exception e) {

            throw new ParseException("unable to convert " + text + " into a " + targetType.getName(), 0);

        }

        return result;

    }

}
