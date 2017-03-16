package fast.glibrary.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import fast.glibrary.annotation.helper.StateBinder;
import fast.glibrary.annotation.helper.StateSaver;
import fast.glibrary.network.Param;
import fast.glibrary.utils.Rom;

/**
 * 项目名称：GDemo
 * 类描述：
 * 创建人：gejianye
 * 创建时间：2016/12/21 13:54
 * 修改人：Administrator
 * 修改时间：2016/12/21 13:54
 * 修改备注：
 */
public abstract class BaseActivity<T> extends AppCompatActivity implements BaseActivityDispatcher<T> {
    private static List<Activity> activities = new LinkedList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addActivity(this);
        defaultMethod(this, getDefaultParams());
    }

    public abstract Param getDefaultParams();

    private void addActivity(Activity activity) {
        if (!activities.contains(activity)) {
            activities.add(activity);
        }
        for (Activity a : activities) {
            Log.e(" addActivity ", a.getClass().getName());
        }
    }

    public static void removeActivity(Activity activity) {
        if (activities.contains(activity)) {
            activities.remove(activity);
        }
        for (Activity a : activities) {
            Log.e(" removeActivity ", a.getClass().getName());
        }
    }

    public static void finishAllActivity() {
        for (final Activity activity : activities) {
            if (activity != null) {
                activity.runOnUiThread(() -> activity.finish());
            } else {
                activities.remove(activity);
            }
        }

    }

    public void start(Class<? extends Activity> activityClass) {
        Intent intent = new Intent();
        intent.setClass(this, activityClass);
        this.startActivity(intent);
    }

    public void start(Class<? extends Activity> activityClass, BaseIntent baseIntent) {
        Intent intent = new Intent();
        intent.setClass(this, activityClass);
        baseIntent.setIntent(intent);
        this.startActivity(intent);
    }

    public BaseActivity getThis() {
        return BaseActivity.this;
    }

    public interface BaseIntent {
        void setIntent(Intent intent);
    }

    public static void initToolbar(BaseActivity activity, Toolbar toolbar, TextView title) {
        //用toolbar代替actionBar
        activity.setSupportActionBar(toolbar);
        //设置居中的title文字
        title.setText(activity.getTitle().toString());
        //label 不显示
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        //点击左侧箭头返回
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivity(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        StateSaver.saveStatue(this, outState);
        super.onSaveInstanceState(outState);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        StateBinder.bindState(this, savedInstanceState);
    }

    /**
     * 修改小米系统状态栏文字颜色
     *
     * @param dark 状态栏文字颜色是否为深色
     * @return
     */
    public boolean setMiUiStatusBarDarkMode(boolean dark) {
        Class<? extends Window> clazz = getThis().getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(getThis().getWindow(), dark ? darkModeFlag : 0, darkModeFlag);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 修改魅族系统状态栏文字颜色
     *
     * @param dark 状态栏文字颜色是否为深色
     * @return
     */
    public boolean setMeiZuStatusBarDarkIcon(boolean dark) {
        boolean result = false;
        try {
            WindowManager.LayoutParams lp = getThis().getWindow().getAttributes();
            Field darkFlag = WindowManager.LayoutParams.class
                    .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field meizuFlags = WindowManager.LayoutParams.class
                    .getDeclaredField("meizuFlags");
            darkFlag.setAccessible(true);
            meizuFlags.setAccessible(true);
            int bit = darkFlag.getInt(null);
            int value = meizuFlags.getInt(lp);
            if (dark) {
                value |= bit;
            } else {
                value &= ~bit;
            }
            meizuFlags.setInt(lp, value);
            getThis().getWindow().setAttributes(lp);
            result = true;
        } catch (Exception e) {
        }
        return result;
    }


    public void makeStatueBarDarkMode(boolean flag) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            if (Rom.isMiUi()) {
                setMiUiStatusBarDarkMode(flag);
            } else if (Rom.isMeiZu()) {
                setMeiZuStatusBarDarkIcon(flag);
            }
        } else {
            getThis().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }
}
