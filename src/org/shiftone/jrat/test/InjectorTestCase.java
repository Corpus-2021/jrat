package org.shiftone.jrat.test;



import java.io.FileOutputStream;
import java.io.OutputStream;

import java.lang.reflect.Method;

import junit.framework.TestCase;

import org.apache.bcel.Repository;
import org.apache.bcel.classfile.JavaClass;

import org.shiftone.jrat.inject.ClassInjector;
import org.shiftone.jrat.test.dummy.CrashTestDummy;
import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;


/**
 * Class InjectorTestCase
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.21 $
 */
public class InjectorTestCase extends TestCase {

    private static final Log   LOG        = LogFactory.getLogger(InjectorTestCase.class);
    public static final String CLASS_NAME = CrashTestDummy.class.getName();
    protected Class            clazz      = null;

    protected void setUp() throws Exception {

        JavaClass       oldClass  = null;
        JavaClass       newClass  = null;
        TestClassLoader loader    = null;
        byte[]          classData = null;

        if(clazz == null) {
            oldClass  = Repository.lookupClass(CLASS_NAME);
            newClass  = ClassInjector.injectClass(oldClass, null);
            loader    = new TestClassLoader();
            classData = newClass.getBytes();

            //save(CLASS_NAME, classData);
            loader.addOverrider(CLASS_NAME, classData);

            clazz = loader.loadClass(CLASS_NAME);
        }
    }

    private synchronized void save(String className, byte[] bytes) throws Exception {

        String shortName = className.substring(className.lastIndexOf('.') + 1);

        LOG.info("shortName = " + shortName);

        OutputStream out = new FileOutputStream(shortName + ".class");

        out.write(bytes);
        out.close();
    }

    public Object testMethodCall(String methodName, Class[] argTypes, Object[] argValues) throws Exception {

        Method method = clazz.getMethod(methodName, argTypes);
        Object o      = method.invoke(clazz.newInstance(), argValues);

        return o;
    }

    public void testNoArgs() throws Exception {

        testMethodCall("noArgs", new Class[]{}, new Object[]{});
        testMethodCall("noArgsSynchronized", new Class[]{}, new Object[]{});
        testMethodCall("noArgsStatic", new Class[]{}, new Object[]{});
        testMethodCall("callAdd", new Class[]{}, new Object[]{});
        testMethodCall("callRecurse", new Class[]{}, new Object[]{});
        testMethodCall("callRecurse", new Class[]{}, new Object[]{});
        testMethodCall("callInLoop_1000", new Class[]{}, new Object[]{});
        testMethodCall("callInLoop_10000", new Class[]{}, new Object[]{});
        testMethodCall("callInLoop_50000", new Class[]{}, new Object[]{});
        testMethodCall("callInLoop_100000", new Class[]{}, new Object[]{});
        testMethodCall("callInLoop_1000000", new Class[]{}, new Object[]{});

        try {
            testMethodCall("throwIOException", new Class[]{}, new Object[]{});
        } catch(Exception e) {}

        try {
            testMethodCall("throwRuntimeException", new Class[]{}, new Object[]{});
        } catch(Exception e) {}
    }

    public void testNoJRat() throws Exception {

        CrashTestDummy dummy = new CrashTestDummy();
        long           start = System.currentTimeMillis();

        dummy.callInLoop_1000();
        LOG.info("callInLoop_1000 took " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();

        dummy.callInLoop_10000();
        LOG.info("callInLoop_10000 took " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();

        dummy.callInLoop_50000();
        LOG.info("callInLoop_50000 took " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();

        dummy.callInLoop_100000();
        LOG.info("callInLoop_100000 took " + (System.currentTimeMillis() - start));
        dummy.callInLoop_1000000();
    }

    public void testOneArg() throws Exception {

        testMethodCall("oneBooleanArg", new Class[]{ Boolean.TYPE }, new Object[]{ Boolean.TRUE });
        testMethodCall("oneIntegerArg", new Class[]{ Integer.TYPE }, new Object[]{ new Integer(101) });
        testMethodCall("oneLongArg", new Class[]{ Long.TYPE }, new Object[]{ new Long(202) });
        testMethodCall("oneObjectArg", new Class[]{ Object.class }, new Object[]{ this });
        testMethodCall("oneStringArrayArg", new Class[]{ String[].class }, new Object[]{ new String[]{ "test", "123" } });
    }

    public void testReturnArg() throws Exception {

        testMethodCall("returnBooleanArg", new Class[]{ Boolean.TYPE }, new Object[]{ Boolean.TRUE });
        testMethodCall("returnIntegerArg", new Class[]{ Integer.TYPE }, new Object[]{ new Integer(101) });
        testMethodCall("returnLongArg", new Class[]{ Long.TYPE }, new Object[]{ new Long(202) });
        testMethodCall("returnObjectArg", new Class[]{ Object.class }, new Object[]{ this });
        testMethodCall("returnStringArrayArg", new Class[]{ String[].class }, new Object[]{ new String[]{ "one", "two", "three" } });
        testMethodCall("localVariableLength", new Class[]{ String.class }, new Object[]{ "test dummy string" });
    }

    public void testTwos() throws Exception {

        testMethodCall("twoIntegerArg", new Class[]{ Integer.TYPE, Integer.TYPE }, new Object[]{ new Integer(1), new Integer(2) });
        testMethodCall("twoLongArg", new Class[]{ Long.TYPE, Long.TYPE }, new Object[]{ new Long(1), new Long(2) });
        testMethodCall("twoObjectArg", new Class[]{ Object.class, Object.class }, new Object[]{ this, "that" });
        testMethodCall("twoStringArrayArg", new Class[]{ String[].class, String[].class }, new Object[]{ new String[]{ "one", "two", "three" }, new String[]{ "test" } });
    }

    public void testMoreArgs() throws Exception {

        boolean[] ba  = new boolean[5];
        Class     bac = ba.getClass();

        testMethodCall("argsByteBooleanArrayObject",                                    //
                       new Class[]{ Byte.TYPE, bac, Object.class },                     //
                       new Object[]{ new Byte((byte) 10), new boolean[]{ true, false }, this });
        testMethodCall("argsLongObjectLongLongArg",                                     //
                       new Class[]{ Long.TYPE, Object.class, Long.TYPE, Long.TYPE },    //
                       new Object[]{ new Long(1), this, new Long(2), new Long(123) });
    }
}
