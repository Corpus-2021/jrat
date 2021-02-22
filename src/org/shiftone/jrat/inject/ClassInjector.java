package org.shiftone.jrat.inject;

import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;

import org.apache.bcel.generic.ACONST_NULL;
import org.apache.bcel.generic.ALOAD;
import org.apache.bcel.generic.ASTORE;
import org.apache.bcel.generic.ATHROW;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.FieldGen;
import org.apache.bcel.generic.InstructionFactory;
import org.apache.bcel.generic.InstructionHandle;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.InvokeInstruction;
import org.apache.bcel.generic.JSR;
import org.apache.bcel.generic.LSUB;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.NOP;
import org.apache.bcel.generic.PUSH;
import org.apache.bcel.generic.RET;
import org.apache.bcel.generic.RETURN;
import org.apache.bcel.generic.Type;

import org.shiftone.jrat.inject.criteria.InjectionCriteria;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.util.Date;


/**
 * This class is for "one time use".  It's much easier to make all operations on this class private, than to add the necessary
 * logic to prevent a calling program from putting the instramented class in an unexpected state.
 *
 * <p>
 * There are quite a few objects that are handly to keep around from method to method when instramenting a class. Instances of
 * this class hold that state for the period of time in which the class is being "injected".  The instance is not expected to be
 * used to instrament a class more than once, or be used on more than one class.
 * </p>
 *
 * <p>
 * While this is not typical OO design, it seems to be the simplest approach.
 * </p>
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.28 $
 */
public class ClassInjector implements InjectConstants {

    //
    private static Log                     LOG                        = LogFactory.getLogger(ClassInjector.class);
    private static final InjectionCriteria DEFAULT_INJECTION_CRITERIA = new InjectionCriteria();
    private String                         className                  = null;
    private ClassGen                       classGen                   = null;
    private JavaClass                      javaClass                  = null;
    private long                           classSerialVersion         = 0;
    private ConstantPoolGen                constantPool               = null;
    private InstructionList                addedStaticInit            = null;
    private InstructionFactory             factory                    = null;
    private InvokeInstruction              onBegin                    = null;
    private InvokeInstruction              onDone                     = null;
    private InvokeInstruction              getTime                    = null;
    private InvokeInstruction              onCatch                    = null;
    private int                            handlerCount               = 0;

    /**
     * Constructor ClassInjector
     *
     * @param javaClass
     */
    private ClassInjector(JavaClass javaClass) {

        this.javaClass              = javaClass;
        this.className              = javaClass.getClassName();
        this.classGen               = new ClassGen(javaClass);
        this.classSerialVersion     = BcelUtil.computeSerialVersionUID(javaClass);
        this.constantPool           = classGen.getConstantPool();
        this.addedStaticInit        = new InstructionList();
        this.factory                = new InstructionFactory(constantPool);
        this.onBegin                = factory.createInvoke(CLASS_NAME_METHOD_HANDLER, METHOD_NAME_BEGIN, Type.VOID, ARGS_METHOD_BEGIN, INVOKEINTERFACE);
        this.onCatch                = factory.createInvoke(CLASS_NAME_METHOD_HANDLER, METHOD_NAME_ERROR, Type.VOID, ARGS_METHOD_ERROR, INVOKEINTERFACE);
        this.onDone                 = factory.createInvoke(CLASS_NAME_METHOD_HANDLER, METHOD_NAME_DONE, Type.VOID, ARGS_METHOD_DONE, INVOKEINTERFACE);
        this.getTime                = factory.createInvoke("java.lang.System", "currentTimeMillis", Type.LONG, NO_ARGS, INVOKESTATIC);

    }

    /**
     * checks for the presence of the static final string added in addClassComment()
     *
     * @return .
     */
    private boolean wasAlreadyInjected() {

        return (classGen.containsField(COMMENT_FIELD_NAME) != null);

    }

    /**
     * Adds a comment in a static final string.  This comment is used to determine if the class has already been instramented
     * once.  It should only be possible to instrament a class once.
     */
    private void addClassComment() {

        FieldGen fieldGen = null;

        fieldGen = new FieldGen(ACC_STATIC | ACC_FINAL | ACC_PRIVATE, Type.STRING, COMMENT_FIELD_NAME, constantPool);

        fieldGen.setInitValue(COMMENT_FIELD_VALUE + new Date());
        classGen.addField(fieldGen.getField());

    }

    /**
     * Method addSerialVersionUID
     */
    private void addSerialVersionUID() {

        Field    field    = null;
        FieldGen fieldGen = null;

        field = classGen.containsField(SERIAL_VERSION_NAME);

        if (field == null) {

            fieldGen = new FieldGen(ACC_STATIC | ACC_FINAL | ACC_PRIVATE, Type.LONG, SERIAL_VERSION_NAME, constantPool);

            fieldGen.setInitValue(classSerialVersion);
            classGen.addField(fieldGen.getField());

        }

    }

    /**
     * This method takes that InstructionList that getStaticHandlerFieldName has been adding to, and adds it to the static
     * initializer.  If an initilizer is not present, one is added.
     */
    private void addStaticInitCode() {

        Method    method    = null;
        MethodGen methodGen = null;

        method = classGen.containsMethod("<clinit>", "()V");

        if (method == null) {

            // adding new <clinit>
            addedStaticInit.append(new RETURN());

            methodGen = new MethodGen(ACC_STATIC | ACC_FINAL, Type.VOID, NO_ARGS, null, "<clinit>", className, addedStaticInit, constantPool);

            methodGen.setMaxStack();
            methodGen.setMaxLocals();
            classGen.addMethod(methodGen.getMethod());

        } else {

            // adding ops to start of existing <clinit>
            InstructionList ops = null;

            methodGen     = new MethodGen(method, className, constantPool);
            ops           = methodGen.getInstructionList();

            ops.insert(addedStaticInit);
            methodGen.setMaxStack();
            methodGen.setMaxLocals();
            classGen.replaceMethod(method, methodGen.getMethod());

        }

    }

    /**
     * Method renameTargetMethod
     *
     * @param method
     *
     * @return .
     */
    private String renameTargetMethod(Method method) {

        Method    newMethod     = null;
        MethodGen methodGen     = null;
        String    oldMethodName = method.getName();
        String    newMethodName = null;

        if (oldMethodName.equals("<init>")) {

            newMethodName = "CONSTRUCTOR" + POSTFIX;

        } else {

            newMethodName = oldMethodName + POSTFIX;

        }

        methodGen = new MethodGen(method, className, constantPool);

        methodGen.setName(newMethodName);
        methodGen.isFinal(true);
        methodGen.isPrivate(true);
        methodGen.isPublic(false);
        methodGen.isProtected(false);

        newMethod = methodGen.getMethod();

        classGen.replaceMethod(method, newMethod);

        return newMethodName;

    }

    /**
     * At some point it may be desirable to push the Class object for static method - Not sure yet. For now, just pushing a null.
     *
     * @param method
     *
     * @return .
     */
    private InstructionList pushThisOnStack(Method method) {

        InstructionList ops = null;

        ops = new InstructionList();

        if (method.isStatic()) {

            ops.append(new ACONST_NULL());

        } else {

            ops.append(new ALOAD(0));

        }

        return ops;

    }

    /**
     * This method creates a new static field in the class that will hold the MethodHandler for the supplied method.  This has
     * two side effects.  First, the field is created - the name is based on the name of the method  Second, initializer code is
     * added to an InstructionList.  This InstructionList will later be prepended to the classes static initializer
     *
     * @param method
     *
     * @return .
     *
     * @see void addStaticInitCode()
     */
    private String getStaticHandlerFieldName(Method method) {

        // addedStaticInit
        String   fieldName = null;
        Field    field    = null;
        FieldGen fieldGen = null;

        fieldName     = STATIC_HANDLER_FIELD + "_" + (handlerCount++);
        field         = classGen.containsField(fieldName);

        if (field == null) {

            fieldGen = new FieldGen(ACC_STATIC | ACC_FINAL | ACC_PRIVATE, REF_TYPE_METHOD_HANDLER, fieldName, constantPool);

            addedStaticInit.append(new PUSH(constantPool, className)); // PARAM : push class name on the stack
            addedStaticInit.append(new PUSH(constantPool, method.getName())); // PARAM : push "name" onto stack
            addedStaticInit.append(new PUSH(constantPool, method.getSignature())); // PARAM : push "signature" onto stack
            addedStaticInit.append(factory.createInvoke(CLASS_NAME_HANDLER_FACTORY, METHOD_NAME_GET_HANDLER, REF_TYPE_METHOD_HANDLER,
                    ARGS_GET_HANDLER, INVOKESTATIC));
            addedStaticInit.append(factory.createPutStatic(className, fieldName, REF_TYPE_METHOD_HANDLER)); // save the result
            classGen.addField(fieldGen.getField());

        }

        return fieldGen.getName();

    }

    /**
     * Method addProxyMethod
     *
     * @param method
     * @param targetMethodName .
     * @param staticHandlerFieldName
     */
    private void addProxyMethod(Method method, String targetMethodName, String staticHandlerFieldName) {

        Method            newMethod      = null;
        MethodGen         methodGen      = null;
        String            methodName     = null;
        Type[]            params         = null;
        Type              returnType     = null;
        InstructionList   ops            = null;
        InstructionHandle xxx;
        InstructionHandle tri;
        InstructionHandle eA;
        InstructionHandle eB;
        InstructionHandle fin;
        InstructionHandle temp           = null;
        int               resultIndex;
        int               successIndex;
        int               startIndex;
        int               argsIndex;
        int               exceptionIndex;
        int               retIndex;
        int               paramNum       = 0;

        methodGen      = new MethodGen(method, className, constantPool);
        ops            = new InstructionList();
        methodName     = methodGen.getName();
        params         = methodGen.getArgumentTypes();
        returnType     = methodGen.getReturnType();

        methodGen.removeLocalVariables();

        startIndex         = methodGen.addLocalVariable("start", Type.LONG, null, null).getIndex();
        successIndex       = methodGen.addLocalVariable("success", Type.BOOLEAN, null, null).getIndex();
        argsIndex          = methodGen.addLocalVariable("args", REF_TYPE_OBJ_ARRAY, null, null).getIndex();
        exceptionIndex     = methodGen.addLocalVariable("exception", Type.OBJECT, null, null).getIndex();
        retIndex           = methodGen.addLocalVariable("ret", Type.INT, null, null).getIndex();

        methodGen.removeLineNumbers();

        temp     = ops.append(new NOP()); // temp is used as target of JSRs and removed later
        xxx      = ops.append(new PUSH(constantPool, TIME_INIT)); // push ZERO on the stack
        xxx      = ops.append(InstructionFactory.createStore(Type.LONG, startIndex)); // store the ZERO in the start time register
        xxx      = ops.append(new ACONST_NULL()); // push NULL on the stack
        xxx      = ops.append(InstructionFactory.createStore(REF_TYPE_OBJ_ARRAY, argsIndex)); // store the NULL in the params
        xxx      = ops.append(new PUSH(constantPool, false)); // push FALSE on the stack
        xxx      = ops.append(InstructionFactory.createStore(Type.BOOLEAN, successIndex)); // store the FALSE in the success register
        tri      = ops.append(factory.createGetStatic(className, staticHandlerFieldName, REF_TYPE_METHOD_HANDLER));
        xxx      = ops.append(pushThisOnStack(method)); // PARAM : push "this" onto stack

        //
        xxx     = ops.append(InstructionFactory.createLoad(REF_TYPE_OBJ_ARRAY, argsIndex)); // PARAM : push "args" onto stack
        xxx     = ops.append(onBegin);
        xxx     = ops.append(getTime);
        xxx     = ops.append(InstructionFactory.createStore(Type.LONG, startIndex)); // store the time in local

        // set up the call to the REAL method
        if (method.isStatic() == false) {

            xxx = ops.append(new ALOAD(0)); // PARAM : push "this" onto stack

            paramNum++;

        }

        for (int p = 0; p < params.length; p++) {

            ops.append(InstructionFactory.createLoad(params[p], paramNum)); // PARAM : push other args onto stack

            paramNum += params[p].getSize();

        }

        // call the REAL method
        ops.append(factory.createInvoke(className, targetMethodName, returnType, params, method.isStatic() ? INVOKESTATIC : INVOKEVIRTUAL));

        // save (or don't save) the returned value
        if (returnType != Type.VOID) {

            resultIndex     = methodGen.addLocalVariable("result", returnType, null, null).getIndex();
            xxx             = ops.append(InstructionFactory.createStore(returnType, resultIndex)); // store the return value in local
            xxx             = ops.append(new PUSH(constantPool, true)); // set success = true : part 1
            xxx             = ops.append(InstructionFactory.createStore(Type.BOOLEAN, successIndex)); // set success = true : part 2
            xxx             = ops.append(new JSR(temp)); // jump to the finally block
            xxx             = ops.append(InstructionFactory.createLoad(returnType, resultIndex)); // load the return value from local
            xxx             = ops.append(InstructionFactory.createReturn(returnType)); // return the previously returned value

        } else {

            xxx     = ops.append(new PUSH(constantPool, true)); // set success = true : part 1
            xxx     = ops.append(InstructionFactory.createStore(Type.BOOLEAN, successIndex)); // set success = true : part 2
            xxx     = ops.append(new JSR(temp)); // no return to store or load.. just jump to the finally block
            xxx     = ops.append(InstructionFactory.createReturn(returnType)); // return nothing

        }

        eA      = ops.append(new ASTORE(exceptionIndex)); // store the caught exception (to be thrown/used later)
        xxx     = ops.append(factory.createGetStatic(className, staticHandlerFieldName, REF_TYPE_METHOD_HANDLER));
        xxx     = ops.append(pushThisOnStack(method)); // PARAM : push "this" onto stack
        xxx     = ops.append(InstructionFactory.createLoad(REF_TYPE_OBJ_ARRAY, argsIndex)); // PARAM : push "args" onto stack
        xxx     = ops.append(new ALOAD(exceptionIndex)); // PARAM : push caught exception onto stack
        xxx     = ops.append(onCatch);
        xxx     = ops.append(new ALOAD(exceptionIndex)); // load the caught exception so it can be thrown
        xxx     = ops.append(new ATHROW()); // re-throw the exception

        eB      = ops.append(new ASTORE(exceptionIndex)); // store the (probably same) caught exception (to be thrown later)
        xxx     = ops.append(new JSR(temp)); // jump to finally block
        xxx     = ops.append(new ALOAD(exceptionIndex)); // after returning from finally, load the exception so it can be thrown
        xxx     = ops.append(new ATHROW()); // throw the exception

        // finally
        fin     = ops.append(new ASTORE(retIndex)); // store the JSR return address
        xxx     = ops.append(factory.createGetStatic(className, staticHandlerFieldName, REF_TYPE_METHOD_HANDLER));
        xxx     = ops.append(pushThisOnStack(method)); // PARAM : push "this" onto stack
        xxx     = ops.append(InstructionFactory.createLoad(REF_TYPE_OBJ_ARRAY, argsIndex)); // PARAM : push "args" onto stack
        xxx     = ops.append(new ACONST_NULL()); // PARAM : push "return" onto stack
        xxx     = ops.append(getTime);
        xxx     = ops.append(InstructionFactory.createLoad(Type.LONG, startIndex)); // push the start time on the stack
        xxx     = ops.append(new LSUB()); // subtract the start time from the return of currentTimeMillis
        xxx     = ops.append(InstructionFactory.createLoad(Type.BOOLEAN, successIndex));
        xxx     = ops.append(onDone);
        xxx     = ops.append(new RET(retIndex)); // return to the JSR that called this "finally" block

        ops.redirectBranches(temp, fin);

        try {

            ops.delete(temp);

        } catch (Exception e) {

        }

        methodGen.setInstructionList(ops);
        methodGen.isSynchronized(false); // allow blocking to effect method timing
        methodGen.removeExceptionHandlers();
        methodGen.addExceptionHandler(tri, eA.getPrev(), eA, OBJ_TYPE_THROWABLE);
        methodGen.addExceptionHandler(tri, eB.getPrev(), eB, null);
        methodGen.setMaxStack();
        methodGen.setMaxLocals();

        newMethod = methodGen.getMethod();

        classGen.addMethod(newMethod);

    }

    /**
     * Method injectMethod
     *
     * @param method
     * @param criteria .
     *
     * @return .
     */
    private boolean injectMethod(Method method, InjectionCriteria criteria) {

        if (method.isAbstract() //
                 ||method.isNative() //
                 ||BcelUtil.isSynthetic(method) //
                 ||method.getName().startsWith("<")) {

            return false;

        } else if (criteria.isMatch(javaClass, method) == false) {

            LOG.info("method does not match criteria : " + method);

            return false;

        } else {

            String newMethodName = renameTargetMethod(method);

            addProxyMethod(method, newMethodName, getStaticHandlerFieldName(method));

            return true;

        }

    }

    /**
     * Method injectMethods
     *
     * @param criteria
     *
     * @return .
     */
    private int injectMethods(InjectionCriteria criteria) {

        int      count   = 0;
        Method[] methods = classGen.getMethods();

        for (int m = 0; m < methods.length; m++) {

            // if injectMethod did some injecting, then increment the count
            if (injectMethod(methods[m], criteria)) {

                count++;

            }

        }

        return count;

    }

    /**
     * Method injectClass
     *
     * @param javaClass
     * @param criteria
     *
     * @return .
     */
    public static JavaClass injectClass(JavaClass javaClass, InjectionCriteria criteria) {

        JavaClass     outputClass = javaClass;
        ClassInjector injector = null;

        if ((javaClass.isInterface()) || (javaClass.getClassName().indexOf('$') != -1)) {

            return javaClass;

        }

        if (criteria == null) {

            criteria = DEFAULT_INJECTION_CRITERIA;

        }

        if (criteria.isMatch(javaClass) == false) {

            LOG.info("class " + javaClass.getClassName() + " does not match criteria");

            return javaClass;

        }

        injector = new ClassInjector(javaClass);

        if (injector.wasAlreadyInjected()) {

            LOG.warn("class was already injected : " + javaClass.getClassName());

        } else {

            if (injector.injectMethods(criteria) > 0) {

                injector.addSerialVersionUID();
                injector.addClassComment();
                injector.addStaticInitCode();

                outputClass = injector.classGen.getJavaClass();

            }

        }

        return outputClass;

    }

}
