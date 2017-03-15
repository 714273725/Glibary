package com.administrator.gdemo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.administrator.gdemo.R;

import java.util.ArrayList;
import java.util.List;

import fast.glibrary.base.BaseActivity;
import fast.glibrary.base.BaseTabActivity;
import fast.glibrary.data.DataFatroty;
import fast.glibrary.network.Param;
import fast.glibrary.uiKit.GViewHolder;
import fast.glibrary.uiKit.TabIcon;

/**
 * 项目名称：GDemo
 * 类描述：
 * 创建人：gejianye
 * 创建时间：2016/12/30 14:44
 * 修改人：Administrator
 * 修改时间：2016/12/30 14:44
 * 修改备注：
 */
public class TabActivity extends BaseTabActivity<String> {
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
    public void bindData(ViewGroup container, GViewHolder holder, String data, int position) {
        holder.setImageUrlString(R.id.siv, DataFatroty.getImage());
    }

    @Override
    public int getLayout(int pos) {
        return R.layout.pager_adapter;
    }

    @Override
    public int getCount() {
        return list_title.size();
    }

    @Override
    public List<String> setData() {
        return list_title;
    }

    @Override
    public TabIcon addTab(int pos) {
        return new TabIcon(mIconSelectIds[pos]
                , list_title.get(pos), mIconUnselectIds[pos]);
    }

    @Override
    public int[] setIndicatorColor() {
        return new int[]{R.color.bgDivider,R.color.colorAccent};
    }

    @Override
    public Param getDefaultParams() {
        return null;
    }

    @Override
    public void defaultMethod(BaseActivity activity, Param param) {

    }
}
