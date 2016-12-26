package fast.glibrary.annotation.helper;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.util.SparseArray;

import com.alibaba.fastjson.JSON;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import fast.glibrary.annotation.SaveState;

/**
 * 项目名称：GDemo
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2016/12/20 9:43
 * 修改人：Administrator
 * 修改时间：2016/12/20 9:43
 * 修改备注：
 */
public class StateSaver {
    public static String INSTANCE_STATE = "INSTANCE_STATE";

    public static void saveStatue(Object target, Bundle bundle) {
        if (bundle == null) {
            return;
        }
        if (target == null) {
            return;
        }
        Class targetClass = target.getClass();
        //获取本类的所有成员变量
        Field[] fields = targetClass.getDeclaredFields();
        for (Field field : fields) {
            if (field != null) {
                field.setAccessible(true);
                SaveState statue = field.getAnnotation(SaveState.class);
                //遍历成员变量，判断变量是否被SaveState注解
                if (statue != null) {
                    int type = statue.value();
                    switch (type) {
                        case SaveState.NORMAL_OBJECT:
                            try {
                                bindNormalObject(field, target, bundle);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                            break;
                        case SaveState.JSON_OBJECT:
                            try {
                                bundle.putString(field.getName(), JSON.toJSONString(field.get(target)));
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                            break;
                    }
                }
            }
        }


    }


    private static void bindNormalObject(Field field, Object target, Bundle bundle) throws IllegalAccessException {
        Object data = field.get(target);
        if (data != null) {
            Class fieldClazz = field.getType();
            //假如成员变量是基本数据类型
            if (fieldClazz.isPrimitive()) {
                bindPrimitive(data, field, bundle);
            } else {
                if (data.getClass() == String.class) {
                    bundle.putString(field.getName(), (String) data);
                } else if (!fieldClazz.isArray() && isImplementTarget(fieldClazz, Serializable.class)) {
                    //不是数组，且实现了Serializable接口
                    bundle.putSerializable(field.getName(), (Serializable) data);
                } else if (!fieldClazz.isArray() && isImplementTarget(fieldClazz, Parcelable.class)) {
                    //不是数组，且实现了Parcelable接口
                    bundle.putParcelable(field.getName(), (Parcelable) data);
                } else if (data.getClass() == ArrayList.class) {
                    //绑定list
                    bindList(field, bundle, (ArrayList) data);
                } else if (fieldClazz.isArray()) {
                    //绑定数组
                    bindArray(field, bundle, data, fieldClazz);
                } else if (fieldClazz == SparseArray.class) {
                    //绑定SparseArray
                    bindSparseArray(field, bundle, data);
                } else {
                    Log.e(field.getName(), "'s java type is unsupported, you can only use the @SaveState at a field that its java type is supported by Bundle");
                }
            }
        }
    }

    private static void bindSparseArray(Field field, Bundle bundle, Object data) {
        boolean parcelable = false;
        if (data != null) {
            for (int i = 0; i < ((SparseArray) data).size(); i++) {
                if (((SparseArray) data).get(i) != null) {
                    if (isImplementTarget(((SparseArray) data).get(i).getClass(), Parcelable.class)) {
                        parcelable = true;
                    }
                }
            }
        }
        if (parcelable) {
            bundle.putSparseParcelableArray(field.getName(), (SparseArray<? extends Parcelable>) data);
        }
    }

    private static void bindArray(Field field, Bundle bundle, Object data, Class fieldClazz) {
        Class componentType = fieldClazz.getComponentType();
        if (componentType.isPrimitive()) {
            if (componentType == byte.class) {
                bundle.putByteArray(field.getName(), (byte[]) data);
            }
            if (componentType == short.class) {
                bundle.putShortArray(field.getName(), (short[]) data);
            }
            if (componentType == int.class) {
                bundle.putIntArray(field.getName(), (int[]) data);
            }
            if (componentType == long.class) {
                bundle.putLongArray(field.getName(), (long[]) data);
            }
            if (componentType == float.class) {
                bundle.putFloatArray(field.getName(), (float[]) data);
            }
            if (componentType == double.class) {
                bundle.putDoubleArray(field.getName(), (double[]) data);
            }

            if (componentType == char.class) {
                bundle.putCharArray(field.getName(), (char[]) data);
            }
            if (componentType == boolean.class) {
                bundle.putBooleanArray(field.getName(), (boolean[]) data);
            }
        } else if (isImplementTarget(componentType, Parcelable.class)) {
            bundle.putParcelableArray(field.getName(), (Parcelable[]) data);
        }
    }

    /**
     * 绑定 ArrayList 类型的变量
     *
     * @param field
     * @param bundle
     * @param data
     */
    private static void bindList(Field field, Bundle bundle, ArrayList data) {
        Type fc = field.getGenericType();
        if (fc != null) {
            if (fc instanceof ParameterizedType) {
                ParameterizedType pt = (ParameterizedType) fc;
                Class genericClazz = (Class) pt.getActualTypeArguments()[0];
                if (isImplementTarget(genericClazz, Parcelable.class)) {
                    bundle.putParcelableArrayList(field.getName(), data);
                } else if (genericClazz == String.class) {
                    bundle.putStringArrayList(field.getName(), (ArrayList<String>) data);
                } else {

                }
            }

        }
    }

    /**
     * 保存基本类型 byte,short,int,long,double,float,char,boolean
     *
     * @param data
     * @param field
     * @param bundle
     */
    private static void bindPrimitive(Object data, Field field, Bundle bundle) {
        Class dataClz = data.getClass();
        if (dataClz == byte.class || dataClz == Byte.class) {
            bundle.putByte(field.getName(), (byte) data);
        }
        if (dataClz == short.class || dataClz == Short.class) {
            bundle.putShort(field.getName(), (short) data);
        }
        if (dataClz == int.class || dataClz == Integer.class) {
            bundle.putInt(field.getName(), (int) data);
        }
        if (dataClz == long.class || dataClz == Long.class) {
            bundle.putLong(field.getName(), (long) data);
        }
        if (dataClz == float.class || dataClz == Float.class) {
            bundle.putFloat(field.getName(), (float) data);
        }
        if (dataClz == double.class || dataClz == Double.class) {
            bundle.putDouble(field.getName(), (double) data);
        }

        if (dataClz == char.class || dataClz == Character.class) {
            bundle.putChar(field.getName(), (char) data);
        }
        if (dataClz == boolean.class || dataClz == Boolean.class) {
            bundle.putBoolean(field.getName(), (boolean) data);
        }
    }


    /**
     * 判断clzs实现的接口中是否包含了目标的class
     *
     * @param clzs   要进行判断的clzs
     * @param target 目标的class
     * @return
     */
    public static boolean isImplementTarget(Class clzs, Class target) {
        boolean flag = false;
        Class[] temp = clzs.getInterfaces();
        if (temp != null) {
            for (Class clz : temp) {
                if (clz == target) {
                    flag = true;
                }
            }
        }
        return flag;
    }
}
