package fast.glibrary.tools;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

import fast.glibrary.network.NetWorkDispatcher;

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
    private static void init(Context context) {
        if (!(context instanceof Application)) {
            throw new IllegalArgumentException("context must be application");
        }
        L.setTag(context.getPackageName());
        mContext = context;
        TS.init(context);
        Fresco.initialize(context);
    }

    /**
     * context必须是Application
     *
     * @param context
     */
    public static void init(Context context, NetWorkDispatcher.DispatcherConfig config) {
        init(context);
        NetWorkDispatcher.getInstance().init(context,config);
    }

}
