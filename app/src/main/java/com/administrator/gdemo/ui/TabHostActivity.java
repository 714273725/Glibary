package com.administrator.gdemo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.administrator.gdemo.ui.fragment.GFragment;

import fast.glibrary.base.BaseTabHostActivity;
import fast.glibrary.uiKit.TabIcon;

/**
 * Created by Administrator on 2017/2/17.
 */

public class TabHostActivity extends BaseTabHostActivity {
    private String mTextArray[] = { "首页", "消息", "好友", "搜索", "更多" };

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
}
