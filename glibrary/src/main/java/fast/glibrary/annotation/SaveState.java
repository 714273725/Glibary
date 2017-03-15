package fast.glibrary.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 项目名称：GDemo
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2016/12/20 9:41
 * 修改人：Administrator
 * 修改时间：2016/12/20 9:41
 * 修改备注：
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SaveState {
    //在保存状态时，将指定的对象转化为Json字符串
    //这样就指定对象就可以不实现序列化
    int JSON_OBJECT = 1;
    int NORMAL_OBJECT = 0;

    int value() default NORMAL_OBJECT;
}
