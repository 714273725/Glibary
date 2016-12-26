package fast.glibrary.tools;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

/**
 * 项目名称：GDemo
 * 类描述：
 * 创建人：gejianye
 * 创建时间：2016/12/21 13:22
 * 修改人：Administrator
 * 修改时间：2016/12/21 13:22
 * 修改备注：
 * <p>在真机中，有时候会发现得到的尺寸不是很准确，需要在AndroidManifest中添加如下配置：
 * <supports-screens android:smallScreens="true"
 * android:normalScreens="true" android:largeScreens="true"
 * android:resizeable="true" android:anyDensity="true" /></p>
 */
public class ScreenTools {
    /**
     * 用于获取状态栏的高度。
     *
     * @return 返回状态栏高度的像素值。
     * {@link GLibraryHelper}
     */
    public static int getStatusBarHeight() {
        int result = 0;
        int resourceId = GLibraryHelper.getContext().getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            result = GLibraryHelper.getContext().getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 获取屏幕宽度px
     *
     * @return
     */
    public static int getScreenWidthPx() {
        return getScreenSize()[0];
    }

    /**
     * 获取屏幕高度px
     *
     * @return
     */
    public static int getScreenHeightPx() {
        return getScreenSize()[1];
    }

    /**
     * 获取标题栏高度
     *
     * @param activity
     * @return
     */
    public static int getTitleBarHeight(Activity activity) {
        int contentTop = activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
        return contentTop - getStatusBarHeight();
    }

    /**
     * 获取屏幕的高度和宽度
     *
     * @return
     */
    public static int[] getScreenSize() {
        WindowManager wm = (WindowManager) GLibraryHelper.getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return new int[]{
                dm.widthPixels, dm.heightPixels
        };

    }

    /**
     * dp to px(pixel)
     */
    public static int dp2px(float dpValue) {
        final float scale = GLibraryHelper.getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px(pixel) to dp
     */
    public static int px2dp(float pxValue) {
        final float scale = GLibraryHelper.getContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
