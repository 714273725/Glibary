package fast.glibrary.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

/**
 * 项目名称：GDemo
 * 类描述：
 * 创建人：gejianye
 * 创建时间：2016/12/21 13:54
 * 修改人：Administrator
 * 修改时间：2016/12/21 13:54
 * 修改备注：
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    public BaseActivity getThis(){
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
}
