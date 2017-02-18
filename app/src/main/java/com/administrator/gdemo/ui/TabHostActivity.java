package com.administrator.gdemo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.administrator.gdemo.R;
import com.administrator.gdemo.ui.fragment.GFragment;

import fast.glibrary.base.BaseTabHostActivity;
import fast.glibrary.uiKit.TabIcon;

/**
 * Created by Administrator on 2017/2/17.
 */

public class TabHostActivity extends BaseTabHostActivity {
    private String mTextArray[] = {"首页", "消息", "好友", "搜索", "更多"};
    private int mIconArray[] = {R.drawable.one, R.drawable.two,
            R.drawable.three, R.drawable.four, R.drawable.five};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public TabIcon getTab(int pos) {
        return new TabIcon(mTextArray[pos]);
    }

    @Override
    public int getCount() {
        return mTextArray.length;
    }

    @Override
    public Class getFragment(int pos) {
        return GFragment.class;
    }

    @Override
    public View getTabView(int pos) {
        ImageView view = new ImageView(getThis());
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        view.setBackgroundResource(mIconArray[pos]);
        LinearLayout layout = new LinearLayout(getThis());
        layout.setGravity(Gravity.CENTER);
        layout.addView(view);
        return layout;
    }

    @Override
    protected String getTabString(int pos) {
        return mTextArray[pos];
    }
}
