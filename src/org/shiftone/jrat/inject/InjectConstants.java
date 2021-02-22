package org.shiftone.jrat.inject;

import org.apache.bcel.Constants;

import org.apache.bcel.generic.ArrayType;
import org.apache.bcel.generic.ObjectType;
import org.apache.bcel.generic.ReferenceType;
import org.apache.bcel.generic.Type;


/**
 * Interface InjectConstants
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.16 $
 */
public interface InjectConstants extends Constants {

    String        POSTFIX                    = "__shiftone_JRat";
    String        COMMENT_FIELD_NAME         = "JRat";
    String        COMMENT_FIELD_VALUE        = "This class was enhanced by the JRat post-processor on ";
    String        STATIC_HANDLER_FIELD       = "HANDLER_FOR_";
    String        SERIAL_VERSION_NAME        = "serialVersionUID";
    String        METHOD_NAME_GET_HANDLER    = "getMethodHandler";
    String        METHOD_NAME_BEGIN          = "onMethodStart";
    String        METHOD_NAME_DONE           = "onMethodFinish";
    String        METHOD_NAME_ERROR          = "onMethodError";
    String        CLASS_NAME_METHOD_HANDLER  = "org.shiftone.jrat.core.spi.MethodHandler";
    String        CLASS_NAME_HANDLER_FACTORY = "org.shiftone.jrat.core.HandlerFactory";
    String        CLASS_NAME_THROWABLE       = Throwable.class.getName();
    String        CLASS_NAME_OBJECT          = Object.class.getName();
    ObjectType    OBJ_TYPE_THROWABLE         = new ObjectType(CLASS_NAME_THROWABLE);
    ReferenceType REF_TYPE_METHOD_HANDLER    = new ObjectType(CLASS_NAME_METHOD_HANDLER);
    ReferenceType REF_TYPE_OBJ_ARRAY         = new ArrayType(CLASS_NAME_OBJECT, 1);
    Type[]        ARGS_GET_HANDLER           = { Type.STRING, Type.STRING, Type.STRING };
    Type[]        ARGS_METHOD_BEGIN          = { Type.OBJECT, REF_TYPE_OBJ_ARRAY };
    Type[]        ARGS_METHOD_DONE           = { Type.OBJECT, REF_TYPE_OBJ_ARRAY, Type.OBJECT, Type.LONG, Type.BOOLEAN };
    Type[]        ARGS_METHOD_ERROR          = { Type.OBJECT, REF_TYPE_OBJ_ARRAY, OBJ_TYPE_THROWABLE };
    Type[]        NO_ARGS                    = Type.NO_ARGS;
    long          TIME_INIT                  = 0;

}
