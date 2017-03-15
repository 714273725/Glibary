package com.administrator.gdemo.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.administrator.gdemo.R;
import com.administrator.gdemo.ui.fragment.FastRefreshFragment;
import com.administrator.gdemo.ui.fragment.GFragment;

import fast.glibrary.base.BaseActivity;
import fast.glibrary.base.BaseNoSlideTabActivity;
import fast.glibrary.base.BaseTabHostActivity;
import fast.glibrary.network.Param;
import fast.glibrary.uiKit.TabIcon;

/**
 * Created by Administrator on 2017/2/17.
 */

public class TabHostActivity extends BaseNoSlideTabActivity {
    private String mTextArray[] = {"首页", "消息", "好友", "搜索"};
    private int[] mIconUnselectIds = {
            R.mipmap.tab_home_unselect,
            R.mipmap.tab_speech_unselect,
            R.mipmap.tab_contact_unselect,
            R.mipmap.tab_more_unselect};
    private int[] mIconSelectIds = {
            R.mipmap.tab_home_select,
            R.mipmap.tab_speech_select,
            R.mipmap.tab_contact_select,
            R.mipmap.tab_more_select};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tab.setSelectedTabIndicatorColor(0xFFFFFF);
    }

    @Override
    public Param getDefaultParams() {
        return null;
    }

    @Override
    public int getCount() {
        return mTextArray.length;
    }

    @NonNull
    @Override
    public TabIcon addTab(int pos) {
        return new TabIcon(mIconSelectIds[pos]
                , null, mIconUnselectIds[pos]);
    }

    @Nullable
    @Override
    public Bundle getBundle(int pos) {
        return null;
    }

    @NonNull
    @Override
    public Class getFragmentClz(int pos) {
        return pos == 0 ? FastRefreshFragment.class : GFragment.class;
    }

    @NonNull
    @Override
    public String[] getTitles() {
        return mTextArray;
    }

    @Override
    public void defaultMethod(BaseActivity activity, Param param) {

    }
}
