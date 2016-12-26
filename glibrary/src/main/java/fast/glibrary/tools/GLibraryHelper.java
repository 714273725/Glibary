package fast.glibrary.tools;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * 项目名称：GDemo
 * 类描述：
 * 创建人：gejianye
 * 创建时间：2016/12/21 13:24
 * 修改人：Administrator
 * 修改时间：2016/12/21 13:24
 * 修改备注：
 * <p>
 * </p>
 */
public class GLibraryHelper {
    public static Context getContext() {
        if (mContext == null) {
            throw new IllegalArgumentException("context not initialize yet");
        }
        return mContext;
    }

    private static Context mContext;

    /**
     * context必须是Application
     *
     * @param context
     */
    public static void init(Context context) {
        if (!(context instanceof Application)) {
            throw new IllegalArgumentException("context must be application");
        }
        L.setTag(context.getPackageName());
        mContext = context;
        Fresco.initialize(context);
    }
}
