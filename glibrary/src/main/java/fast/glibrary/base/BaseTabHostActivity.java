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
            // 给每个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = mHost.newTabSpec(getTab(i).getTabTitle())
                    .setIndicator(getTabView(i));
            // 将Tab按钮添加进Tab选项卡中
            mHost.addTab(tabSpec, getFragment(i), null);
        }
        mHost.getTabWidget().setDividerDrawable(getResources().getDrawable(android.R.color.white));
    }

    /**
     * 根据位置获取tab的属性
     *
     * @param pos
     * @return
     */
    public abstract TabIcon getTab(int pos);

    /**
     * 返回tab的数量
     *
     * @return
     */
    public abstract int getCount();

    /**
     * 根据位置获取相应的Fragment类
     *
     * @param pos
     * @return
     */
    public abstract Class getFragment(int pos);

    /**
     * tab视图
     *
     * @param pos
     * @return
     */
    public View getTabView(int pos) {
        TextView textView = new TextView(getThis());
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundResource(android.R.color.holo_red_light);
        textView.setText(getTabString(pos));
        return textView;
    }

    protected abstract String getTabString(int pos);

}
