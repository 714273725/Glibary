package com.administrator.gdemo.ui;

import android.support.v4.app.Fragment;
import android.view.ViewGroup;

import com.administrator.gdemo.R;
import com.administrator.gdemo.ui.fragment.FastRefreshFragment;
import com.administrator.gdemo.ui.fragment.GFragment;

import java.util.ArrayList;
import java.util.List;

import fast.glibrary.base.BaseActivity;
import fast.glibrary.base.BaseFragmentTabActivity;
import fast.glibrary.network.Param;
import fast.glibrary.uiKit.GViewHolder;
import fast.glibrary.uiKit.TabIcon;

/**
 * 项目名称：GDemo
 * 类描述：
 * 创建人：gejianye
 * 创建时间：2016/12/30 15:38
 * 修改人：Administrator
 * 修改时间：2016/12/30 15:38
 * 修改备注：
 */
public class TabFragmentActivity extends BaseFragmentTabActivity {
    ArrayList<String> list_title;
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

    {
        list_title = new ArrayList<>();
        list_title.add("热门推荐");
        list_title.add("热门收藏");
        list_title.add("本月热榜");
        list_title.add("今日热榜");
    }

    @Override
    public Fragment getFragmentEntity(int pos) {
        if (pos == 0) {
            return new FastRefreshFragment();
        }
        return new GFragment();
    }

    @Override
    public int getFragmentCount() {
        return list_title.size();
    }

    @Override
    public TabIcon addTab(int pos) {
        return new TabIcon(mIconSelectIds[pos]
                , list_title.get(pos), mIconUnselectIds[pos]);
    }

    @Override
    public Param getDefaultParams() {
        return null;
    }

    @Override
    public void defaultMethod(BaseActivity activity, Param param) {

    }
}
