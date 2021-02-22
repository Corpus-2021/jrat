package org.shiftone.jrat.util;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


/**
 * Class SignatureUtil
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.14 $
 */
public class SignatureUtil
{
    private static final Log LOG             = LogFactory.getLogger(SignatureUtil.class);
    private static Map       PRIMITIVE_TYPES = new HashMap();

    static
    {
        PRIMITIVE_TYPES.put(Boolean.TYPE, "Z");
        PRIMITIVE_TYPES.put(Byte.TYPE, "B");
        PRIMITIVE_TYPES.put(Character.TYPE, "C");
        PRIMITIVE_TYPES.put(Short.TYPE, "S");
        PRIMITIVE_TYPES.put(Integer.TYPE, "I");
        PRIMITIVE_TYPES.put(Long.TYPE, "J");
        PRIMITIVE_TYPES.put(Float.TYPE, "F");
        PRIMITIVE_TYPES.put(Double.TYPE, "D");
        PRIMITIVE_TYPES.put(Void.TYPE, "V");
        PRIMITIVE_TYPES.put(Float.TYPE, "F");
    }

    /**
     * Method getSignature - so silly
     *
     * @param klass .
     *
     * @return .
     */
    public static String getSignature(Class klass)
    {
        Assert.assertNotNull("class", klass);

        if (klass.isPrimitive())
        {
            return (String) PRIMITIVE_TYPES.get(klass);
        }
        else
        {
            String sig = klass.getName();

            sig = sig.replace('.', '/');

            if (klass.isArray() == false)
            {
                sig = 'L' + sig + ';';
            }

            return sig;
        }
    }

    /**
     * Method getSignature
     *
     * @param method .
     *
     * @return .
     */
    public static String getSignature(Method method)
    {
        Assert.assertNotNull("method", method);

        StringBuffer sb   = new StringBuffer();
        Class[]      args = method.getParameterTypes();
        Class        ret  = method.getReturnType();

        sb.append("(");

        for (int i = 0; i < args.length; i++)
        {
            sb.append(getSignature(args[i]));
        }

        sb.append(")");
        sb.append(getSignature(ret));

        return sb.toString();
    }

    /**
     * method
     *
     * @param constructor .
     *
     * @return .
     */
    public static String getSignature(Constructor constructor)
    {
        Assert.assertNotNull("constructor", constructor);

        StringBuffer sb   = new StringBuffer();
        Class[]      args = constructor.getParameterTypes();

        //Class        ret  = constructor.getReturnType();
        sb.append("(");

        for (int i = 0; i < args.length; i++)
        {
            sb.append(getSignature(args[i]));
        }

        sb.append(")");

        //sb.append(getSignature(ret));
        return sb.toString();
    }
}
