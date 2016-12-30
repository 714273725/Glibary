package fast.glibrary.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
}
