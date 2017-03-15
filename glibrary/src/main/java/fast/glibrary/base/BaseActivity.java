package fast.glibrary.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import fast.glibrary.annotation.helper.StateBinder;
import fast.glibrary.annotation.helper.StateSaver;
import fast.glibrary.network.Param;

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
}
