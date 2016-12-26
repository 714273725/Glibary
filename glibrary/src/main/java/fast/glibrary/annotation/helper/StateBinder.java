package fast.glibrary.annotation.helper;

import android.os.Bundle;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.Field;

import fast.glibrary.annotation.SaveState;

/**
 * 项目名称：GDemo
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2016/12/20 9:50
 * 修改人：Administrator
 * 修改时间：2016/12/20 9:50
 * 修改备注：
 */
public class StateBinder {
    public static String INSTANCE_STATE="INSTANCE_STATE";

    public static void bindState(Object target, Bundle source) {
        if (source == null) {
            return;
        }
        if (target == null) {
            return;
        }
        Class targetClass = target.getClass();
        Field[] fields = targetClass.getDeclaredFields();
        if (fields != null) {
            for (Field field : fields) {
                SaveState statue = field.getAnnotation(SaveState.class);
                field.setAccessible(true);
                if (statue != null) {
                    Object data = source.get(field.getName());
                    try {
                        switch (statue.value()) {
                            case SaveState.JSON_OBJECT:
                                field.set(target, JSON.parseObject((String) data, field.getClass()));
                                break;
                            case SaveState.NORMAL_OBJECT:
                                field.set(target, data);
                                break;
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
