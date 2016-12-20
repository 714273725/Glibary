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
    int JSON_OBJECT = 1;
    int NORMAL_OBJECT = 0;

    int value() default NORMAL_OBJECT;
}
