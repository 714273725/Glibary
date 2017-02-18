package fast.glibrary.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TabHost;
import android.widget.TextView;

import fast.glibrary.R;
import fast.glibrary.uiKit.TabIcon;

/**
 * Created by Administrator on 2017/2/17.
 */

public abstract class BaseTabHostActivity<T> extends BaseActivity {
    public FragmentTabHost mHost;
    public FrameLayout mFrameLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabhost);
        mHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mHost.setup(getThis(), getSupportFragmentManager(), android.R.id.tabcontent);
        for (int i = 0; i < getCount(); i++) {
            TextView textView = new TextView(getThis());
            textView.setGravity(Gravity.CENTER);
            textView.setBackgroundResource(android.R.color.holo_red_light);
            textView.setText(""+i);
            // 给每个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = mHost.newTabSpec(getTab(i).getTabTitle())
                    .setIndicator(textView);
            // 将Tab按钮添加进Tab选项卡中
            mHost.addTab(tabSpec, getFragment(i), null);
            /*// 设置Tab按钮的背景
            mHost.getTabWidget().getChildAt(i)
                    .setBackgroundResource(R.drawable.selector_tab_background);*/
        }
        mHost.getTabWidget().setDividerDrawable(getResources().getDrawable(android.R.color.white));
        mHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {

            }
        });
    }

    public abstract TabIcon getTab(int pos);

    public abstract int getCount();

    public abstract Class getFragment(int pos);

}
